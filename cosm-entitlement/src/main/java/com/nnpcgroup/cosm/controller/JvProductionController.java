/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.RegularProduction;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author 18359
 */
@Named(value = "jvActualController")
@SessionScoped
public class JvProductionController implements Serializable {

    private static final Logger LOG = Logger.getLogger(JvProductionController.class.getName());
    private static final long serialVersionUID = -5506490644508725206L;

    @EJB
    private JvProductionServices productionBean;

    private Production currentProduction;
    private List<Production> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
        LOG.info("JvActualProductionController::constructor activated...");
        // LOG.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public Production getCurrentProduction() {
        LOG.info("JvActualProductionController::getProduction called...");
        return currentProduction;
    }

    public void setCurrentProduction(Production currentProduction) {
        LOG.info("JvActualProductionController::setProduction called...");
        this.currentProduction = currentProduction;
        this.currentFiscalArrangement = (currentProduction != null)
                ? currentProduction.getContract().getFiscalArrangement() : null;
    }

    public List<Production> getProductions() {
        LOG.info("JvActualProductionController::getProductions called...");
        return productions;
    }

    public void setProductions(List<Production> productions) {
        LOG.info("JvActualProductionController::setProductions called...");
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
        LOG.log(Level.INFO,
                "Own entmt={0},Partner entmt={1}, Stock Adj={2}...",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement(),
                    currentProduction.getStockAdjustment()});

    }

    public void liftingChanged() {
        LOG.log(Level.INFO, "LIfting changed...");
        productionBean.liftingChanged(currentProduction);

        Double openingStock = currentProduction.getOpeningStock();
        Double entitlement = currentProduction.getOwnShareEntitlement();
        Double lifting = currentProduction.getLifting();

        Double partnerOpeningStock = currentProduction.getPartnerOpeningStock();
        Double partnerEntitlement = currentProduction.getPartnerShareEntitlement();
        Double partnerLifting = currentProduction.getPartnerLifting();

        Double bucket = openingStock + entitlement + partnerOpeningStock + partnerEntitlement;

        if (bucket - lifting - partnerLifting < 0) {
            FacesMessage msg
                    = new FacesMessage("Stock Lifting validation failed!",
                            "Please check your availability and lifting volume");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }

    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed...");
        productionBean.openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
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
        LOG.log(Level.INFO, "************JvActualProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        LOG.log(Level.INFO, "************JvActualProductionController::setPeriodMonth called with value {0}", periodMonth);
        this.periodMonth = periodMonth;
    }

    public FiscalArrangement getCurrentFiscalArrangement() {
        return currentFiscalArrangement;
    }

    public void setCurrentFiscalArrangement(FiscalArrangement currentFiscalArrangement) {
        this.currentFiscalArrangement = currentFiscalArrangement;
    }

    public SelectItem[] getContractSelectOne() {
        if (currentFiscalArrangement == null) {
            return null;
        }
        return JsfUtil.getSelectItems(currentFiscalArrangement.getContracts(), true);
    }

    public void actualize(Production production) {
        LOG.log(Level.INFO, "************JvActualProductionController::actualizing {0}...", production);

        currentProduction = (Production)productionBean.findByContractPeriod(
                production.getPeriodYear(),
                production.getPeriodMonth(),
                production.getContract());
        LOG.log(Level.INFO, "************JvActualProductionController::findByContractStreamPeriod returning {0}...", currentProduction);

        if (currentProduction == null) {
            LOG.log(Level.INFO, "************JvActualProductionController::actualizing returning new JV Production instance...");
            currentProduction = new RegularProduction();//productionBean.createInstance(); TODO:evaluate
            LOG.log(Level.INFO, "************JvActualProductionController::productionBean.createInstance() returning {0}...", currentProduction);
            currentProduction.setPeriodYear(production.getPeriodYear());
            currentProduction.setPeriodMonth(production.getPeriodMonth());
            currentProduction.setContract(production.getContract());

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

    public void destroy(RegularProduction prod) {
        setCurrentProduction(prod);
        destroy();
    }

    public Production prepareCreate() {
        LOG.log(Level.INFO, "Preparing new instance of JvActualProduction for create...");
        currentProduction = new RegularProduction();//productionBean.createInstance(); TODO:evaluate
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

    public void validateStockLifting(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
        Double openingStock = currentProduction.getOpeningStock();
        Double entitlement = currentProduction.getOwnShareEntitlement();
        Double lifting = currentProduction.getLifting();

        Double partnerOpeningStock = currentProduction.getPartnerOpeningStock();
        Double partnerEntitlement = currentProduction.getPartnerShareEntitlement();
        Double partnerLifting = currentProduction.getPartnerLifting();

        Double bucket = openingStock + entitlement + partnerOpeningStock + partnerEntitlement;

        if (bucket - lifting - partnerLifting < 0) {
            FacesMessage msg
                    = new FacesMessage("Stock Lifting validation failed!",
                            "Please check your availability and lifting volume");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }

}
