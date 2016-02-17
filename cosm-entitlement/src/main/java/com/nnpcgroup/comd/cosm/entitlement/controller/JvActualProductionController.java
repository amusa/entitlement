/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JvActualProductionBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
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
@Named(value = "jvActualController")
@SessionScoped
public class JvActualProductionController implements Serializable {

    private static final Logger log = Logger.getLogger(JvActualProductionController.class.getName());
    private static final long serialVersionUID = -5506490644508725206L;

//    @Inject
//    @JVACTUAL
    @EJB
    private JvActualProductionBean productionBean;

    private ActualJvProduction currentProduction;
    //private ActualJvProduction currentActualProduction;

    private List<ActualJvProduction> productions;

    private boolean manualEntry = false;
    private FiscalArrangement currentFiscal;
    private int periodYear;
    private int periodMonth;

    /**
     * Creates a new instance of JvController
     */
    public JvActualProductionController() {
        log.info("JvActualProductionController::constructor activated...");
        // log.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public Production getCurrentProduction() {
        log.info("JvActualProductionController::getProduction called...");
        return currentProduction;
    }

    public void setCurrentProduction(ActualJvProduction currentProduction) {
        log.info("JvActualProductionController::setProduction called...");
        this.currentProduction = currentProduction;
    }

    public ActualJvProduction getCurrentActualProduction() {
        log.info("JvActualProductionController::getActualCurrentProduction called...");
        return (ActualJvProduction) currentProduction;
    }

    public void setCurrentActualProduction(ActualJvProduction currentActualProduction) {
        this.currentProduction = currentActualProduction;
    }

    public List<ActualJvProduction> getProductions() {
        log.info("JvActualProductionController::getProductions called...");
        return productions;
    }

    public void setProductions(List<ActualJvProduction> productions) {
        log.info("JvActualProductionController::setProductions called...");
        this.productions = productions;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("JvActualProductionController::postConstructor initializing production...");
    }

    public void processManualEntry() {
        log.info("JvActualProductionController::processManualEntry called...");
        log.info("*******setting manualEntry to true********");
        // currentProduction = new JvProduction();
        currentProduction = productionBean.createInstance();
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
        setManualEntry(true);

    }

    public void loadProductions() {
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
    }

    public void addProductionItem() {
        log.info("JvActualProductionController::addProductionItem called...");
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
        currentFiscal = null;

    }

    public boolean isManualEntry() {
        log.log(Level.INFO, "*******isManualEntry returning {0} ***********", manualEntry);
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

    public FiscalArrangement getCurrentFiscal() {
        return currentFiscal;
    }

    public void setCurrentFiscal(FiscalArrangement currentFiscal) {
        this.currentFiscal = currentFiscal;
    }

    public List<ContractStream> getContractStreamList() {
        log.log(Level.INFO, "getting currentFiscal = {0}...", currentFiscal);
        if (null != currentFiscal) {
            return currentFiscal.getContractStreams();
        }
        return null;
    }

    public void fiscalListChanged(AjaxBehaviorEvent event) {
        log.info("JvActualProductionController::fiscalListChanged called...");

    }

    public void saveProductions() {
        log.log(Level.INFO, "************JvActualProductionController::saveProduction called...{0} records to be saved!",
                productions.size());
        productionBean.create(productions);
        reset();

        JsfUtil.addSuccessMessage("JV Production Saved Successfully!");
    }

    private void reset() {
        productions = null;
        setManualEntry(false);
    }

    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        log.log(Level.INFO, "************JvActualProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        log.log(Level.INFO, "************JvActualProductionController::setPeriodMonth called with value {0}", periodMonth);
        this.periodMonth = periodMonth;
    }

    public void actualize(Production production) {
        log.log(Level.INFO, "************JvActualProductionController::actualizing {0}...", production);

        currentProduction = productionBean.findByContractStreamPeriod(
                production.getPeriodYear(),
                production.getPeriodMonth(),
                production.getContractStream());
        log.log(Level.INFO, "************JvActualProductionController::findByContractStreamPeriod returning {0}...", currentProduction);

        if (currentProduction == null) {
            log.log(Level.INFO, "************JvActualProductionController::actualizing returning new JV Production instance...");
            currentProduction = productionBean.createInstance();
            log.log(Level.INFO, "************JvActualProductionController::productionBean.createInstance() returning {0}...", currentProduction);
            currentProduction.setPeriodYear(production.getPeriodYear());
            currentProduction.setPeriodMonth(production.getPeriodMonth());
            currentProduction.setContractStream(production.getContractStream());

            // productionBean.enrich(currentProduction);
        }
    }

    public void productionVolumeChange() {
        productionBean.enrich(currentProduction);
        log.log(Level.INFO,
                "Production Enriched::Own entmt={0},Partner entmt={1}, Stock Adj={2}...",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement(),
                    currentProduction.getStockAdjustment()});

    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            currentProduction = null; // Remove selection
            productions = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public ActualJvProduction prepareCreate() {
        log.log(Level.INFO, "Preparing new instance of ActualJvProduction for create...");
        currentProduction = productionBean.createInstance();
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        return currentProduction;
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

}
