/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.controller.util.ProductionDataModel;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JvForecastProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvForecastProduction;

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
@Named(value = "jvProdController")
@SessionScoped
public class JvProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvProductionController.class.getName());

    @EJB
    private JvForecastProductionServices productionBean;

    private JvForecastProduction currentProduction;

    private List<JvForecastProduction> productions;
    private ProductionDataModel dataModel;

    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
        log.info("ProductionController::constructor activated...");
    }

    public ProductionDataModel getDataModel() {
        return dataModel;
    }

    public JvForecastProduction getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvForecastProduction currentProduction) {
        log.info("ProductionController::setProduction called...");
        this.currentProduction = currentProduction;
        this.currentFiscalArrangement = (currentProduction != null)
                ? currentProduction.getContract().getFiscalArrangement() : null;
    }

    public List<JvForecastProduction> getProductions() {
        log.info("ProductionController::getProductions called...");
        loadProductions();
        return productions;
    }

    public void setProductions(List<JvForecastProduction> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    public void prepareCreate() {
        log.info("prepareCreate called...");
        currentProduction = productionBean.createInstance();
        if (periodYear != null && periodMonth != null) {
            currentProduction.setPeriodYear(periodYear);
            currentProduction.setPeriodMonth(periodMonth);
        }
        //return currentProduction;
    }

    public void destroy() {
        log.log(Level.INFO, "Deleting {0}...", currentProduction);
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            dataModel.removeItem(currentProduction);
            currentProduction = null;
        }
    }

    public void destroy(JvForecastProduction prod) {
        setCurrentProduction(prod);
        destroy();
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

    public void loadProductions() {
        if (periodYear != null && periodMonth != null) {
            if (currentFiscalArrangement == null) {
                productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
            } else {
                productions = productionBean.findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            }
            refreshDataModel();
        }
    }

    public void refreshDataModel() {
        log.log(Level.INFO, "Refreshing DataModel...");
        dataModel = new ProductionDataModel(productions);
    }

    public void productionVolumeChanged() {
        log.log(Level.INFO, "Production Volume changed...");
        productionBean.enrich(currentProduction);
        log.log(Level.INFO,
                "Production Enriched::Own entmt={0},Partner entmt={1}",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement()
                });

    }

    public void openingStockChanged() {
        log.log(Level.INFO, "Opening Stock changed...");
        productionBean.openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        log.log(Level.INFO, "Resetting to default...");
        productionBean.enrich(currentProduction);
    }

    private void reset() {
        currentProduction = null;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        log.log(Level.INFO, "************JvProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        log.log(Level.INFO, "************JvProductionController::getPeriodMonth called. returning {0}...", periodMonth);

        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        log.log(Level.INFO, "************JvProductionController::setPeriodMonth called with value {0}", periodMonth);
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

    public Double getDailySum() {
        Double dailySum = productions.stream()
                .mapToDouble(p -> p.getProductionVolume())
                .sum();
        return dailySum;
    }

    public Double getGrossSum() {
        Double grossProd = productions.stream()
                .mapToDouble(p -> p.getGrossProduction())
                .sum();
        return grossProd;
    }

    public Double getOwnEntitlementSum() {
        Double ownEntitlement = productions.stream()
                .mapToDouble(p -> p.getOwnShareEntitlement())
                .sum();
        return ownEntitlement;
    }

    public Double getPartnerEntitlementSum() {
        Double partnerEntitlement = productions.stream()
                .mapToDouble(p -> p.getPartnerShareEntitlement())
                .sum();
        return partnerEntitlement;
    }

    public Double getOpeningStockSum() {
        Double openingStockSum = productions.stream()
                .mapToDouble(p -> p.getOpeningStock())
                .sum();
        return openingStockSum;
    }

    public Double getAvailabilitySum() {
        Double availabilitySum = productions.stream()
                .mapToDouble(p -> p.getAvailability())
                .sum();

        return availabilitySum;
    }

    public Double getNomLiftingSum() {
        Double nomLiftingSum = getCargoesSum() * 950000.0;
        return nomLiftingSum;
    }

    public Integer getCargoesSum() {
        Double availabilitySum = getAvailabilitySum();
        Integer cargoesSum = (int) (availabilitySum / 950000.0);
        return cargoesSum;
    }

    public Double getClosingStockSum() {
        Double availabilitySum = getAvailabilitySum();
        return availabilitySum % 950000.0;
    }

}
