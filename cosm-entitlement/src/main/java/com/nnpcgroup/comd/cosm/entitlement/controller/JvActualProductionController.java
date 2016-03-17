/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JvActualProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvActualProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author 18359
 */
@Named(value = "jvActualController")
@SessionScoped
public class JvActualProductionController implements Serializable {

    private static final Logger log = Logger.getLogger(JvActualProductionController.class.getName());
    private static final long serialVersionUID = -5506490644508725206L;

    @EJB
    private JvActualProductionServices productionBean;

    private JvActualProduction currentProduction;
    private List<JvActualProduction> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;

    /**
     * Creates a new instance of JvController
     */
    public JvActualProductionController() {
        log.info("JvActualProductionController::constructor activated...");
        // log.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public JvActualProduction getCurrentProduction() {
        log.info("JvActualProductionController::getProduction called...");
        return currentProduction;
    }

    public void setCurrentProduction(JvActualProduction currentProduction) {
        log.info("JvActualProductionController::setProduction called...");
        this.currentProduction = currentProduction;
        this.currentFiscalArrangement = (currentProduction != null)
                ? currentProduction.getContractStream().getFiscalArrangement() : null;
    }

    public List<JvActualProduction> getProductions() {
        log.info("JvActualProductionController::getProductions called...");
        return productions;
    }

    public void setProductions(List<JvActualProduction> productions) {
        log.info("JvActualProductionController::setProductions called...");
        this.productions = productions;
    }

    public void loadProductions() {
        if (periodYear != null && periodMonth != null) {
            if (currentFiscalArrangement == null) {
                productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
            } else {
                productions = productionBean.findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            }

        }
    }

    public void grossProductionChanged() {
        productionBean.grossProductionChanged(currentProduction);
        log.log(Level.INFO,
                "Own entmt={0},Partner entmt={1}, Stock Adj={2}...",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement(),
                    currentProduction.getStockAdjustment()});

    }

    public void liftingChanged() {
        log.log(Level.INFO, "LIfting changed...");
        productionBean.liftingChanged(currentProduction);

    }

    public void openingStockChanged() {
        log.log(Level.INFO, "Opening Stock changed...");
        productionBean.openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        log.log(Level.INFO, "Resetting to default...");
        productionBean.grossProductionChanged(currentProduction);
    }

    private void reset() {
        currentProduction = null;
        productions = null;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        log.log(Level.INFO, "************JvActualProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        log.log(Level.INFO, "************JvActualProductionController::setPeriodMonth called with value {0}", periodMonth);
        this.periodMonth = periodMonth;
    }

    public FiscalArrangement getCurrentFiscalArrangement() {
        return currentFiscalArrangement;
    }

    public void setCurrentFiscalArrangement(FiscalArrangement currentFiscalArrangement) {
        this.currentFiscalArrangement = currentFiscalArrangement;
    }

    public SelectItem[] getContractStreamSelectOne() {
        if (currentFiscalArrangement == null) {
            return null;
        }
        return JsfUtil.getSelectItems(currentFiscalArrangement.getContractStreams(), true);
    }

    public void actualize(Production production) {
        log.log(Level.INFO, "************JvActualProductionController::actualizing {0}...", production);

        currentProduction = productionBean.findByContractPeriod(
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

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadProductions();
        }
    }

    public JvActualProduction prepareCreate() {
        log.log(Level.INFO, "Preparing new instance of JvActualProduction for create...");
        currentProduction = productionBean.createInstance();
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        return currentProduction;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadProductions();
        }
    }

    public void cancel() {
        reset();
        loadProductions();
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
