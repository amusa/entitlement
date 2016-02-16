/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JvProductionBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvProduction;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;

import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author 18359
 */
@Named(value = "jvProdController")
@SessionScoped
public class JvProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvProductionController.class.getName());

    //@Inject
    //@JV
    @EJB
    private JvProductionBean productionBean;

    private JvProduction currentProduction;

    private List<JvProduction> productions;

    private boolean manualEntry = false;
    private Integer periodYear;
    private Integer periodMonth;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
        log.info("ProductionController::constructor activated...");
        // log.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public JvProduction getCurrentProduction() {
        log.log(Level.INFO, "ProductionController::getCurrentProduction returning...",
                currentProduction);
        return currentProduction;
    }

    public void setCurrentProduction(JvProduction currentProduction) {
        log.info("ProductionController::setProduction called...");
        this.currentProduction = currentProduction;
    }

    public List<JvProduction> getProductions() {
        log.info("ProductionController::getProductions called...");
        if (productions == null && periodYear != null && periodMonth != null) {
            productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
        }
        return productions;
    }

    public void setProductions(List<JvProduction> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    @PostConstruct
    public void postConstruct() {
        log.log(Level.INFO, "JvProductionController::postConstructor initializing");
    }

    public void processManualEntry() {
        log.info("JvProductionController::processManualEntry called...");
        log.info("*******setting manualEntry to true********");
        // currentProduction = new JvProduction();
        if (periodYear == null || periodMonth == null) {
            return;
        }
        currentProduction = productionBean.createInstance();
        log.log(Level.INFO, "ProductionBean instance = {0}", productionBean);
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
        setManualEntry(true);

    }

    public JvProduction prepareCreate() {
        currentProduction = productionBean.createInstance();
        if (periodYear != null && periodMonth != null) {
            currentProduction.setPeriodYear(periodYear);
            currentProduction.setPeriodMonth(periodMonth);
        }
        return currentProduction;
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            currentProduction = null; // Remove selection
            productions = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            productions = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentProduction != null) {
            //setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    productionBean.edit(currentProduction);
                } else {
                    productionBean.remove(currentProduction);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public void loadProductions() {
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
    }

    public void addProductionItem() {
        log.info("JvProductionController::addProductionItem called...");
        log.log(Level.INFO, "year={0} month={1} contractStream={2} prodVolume={3}",
                new Object[]{currentProduction.getPeriodYear(), currentProduction.getPeriodMonth(), currentProduction.getContractStream(), currentProduction.getProductionVolume()});

        if (productions == null) {
            productions = new ArrayList<>(); //TODO:use factory method
        }
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);

        productionBean.enrich(currentProduction);

        productions.add(currentProduction);
        //currentProduction = new JvProduction();
        currentProduction = productionBean.createInstance();

    }

    public void productionVolumeChange() {
        log.log(Level.INFO, "Production Volume changed...");
        productionBean.enrich(currentProduction);
        log.log(Level.INFO,
                "Production Enriched::Own entmt={0},Partner entmt={1}",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement()
                });

    }

    public boolean isManualEntry() {
        log.log(Level.INFO, "*******isManualEntry returning {0} ***********", manualEntry);
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

    public void fiscalListChanged(AjaxBehaviorEvent event) {
        log.info("jvProductionController::fiscalListChanged called...");

    }

    public void saveProductions() {
        log.log(Level.INFO, "************jvProductionController::saveProduction called...{0} records to be saved!",
                productions.size());
        productionBean.edit(productions);
        reset();

        JsfUtil.addSuccessMessage("JV Production Saved Successfully!");
    }

    private void reset() {
        productions = null;
        currentProduction = productionBean.createInstance();
        //setManualEntry(false);
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        log.log(Level.INFO, "************JvProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        log.log(Level.INFO, "************JvProductionController::setPeriodMonth called with value {0}", periodMonth);
        this.periodMonth = periodMonth;
    }

}
