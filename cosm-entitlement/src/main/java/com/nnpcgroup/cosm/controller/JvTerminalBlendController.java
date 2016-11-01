/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastDetailServices;
import com.nnpcgroup.cosm.entity.Terminal;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlement;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

/**
 * @author 18359
 */
@Named(value = "jvTermBlendController")
@SessionScoped
public class JvTerminalBlendController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvTerminalBlendController.class.getName());

    @EJB
    private JvForecastDetailServices productionBean;

    private JvForecast currentProduction;

    private List<JvForecast> productions;
    private List<JvForecastDetail> forecastDetails;
    private List<JvForecastEntitlement> forecastEntitlements;

    private Integer periodYear;
    private Integer periodMonth;
    private Terminal currentTerminal;

    /**
     * Creates a new instance of JvController
     */
    public JvTerminalBlendController() {
        productions = new ArrayList<>();
        forecastDetails = new ArrayList<>();
    }

    public JvForecast getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvForecast currentProduction) {
        log.info("ProductionController::setProduction called...");
        this.currentProduction = currentProduction;
    }

    public List<JvForecast> getProductions() {
        log.info("ProductionController::getProductions called...");
        //loadProductions();
        return productions;
    }

    public void setProductions(List<JvForecast> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    public List<JvForecastDetail> getForecastDetails() {
        loadProductions();
        return forecastDetails;
    }

    public void setForecastDetails(List<JvForecastDetail> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public List<JvForecastEntitlement> getForecastEntitlements() {
        return forecastEntitlements;
    }

    public void setForecastEntitlements(List<JvForecastEntitlement> forecastEntitlements) {
        this.forecastEntitlements = forecastEntitlements;
    }

    //    public void prepareCreate() {
//        log.info("prepareCreate called...");
//        currentProduction = productionBean.createInstance();
//        if (periodYear != null && periodMonth != null) {
//            currentProduction.setPeriodYear(periodYear);
//            currentProduction.setPeriodMonth(periodMonth);
//        }
//        //return currentProduction;
//    }
//    public void destroy() {
//        log.log(Level.INFO, "Deleting {0}...", currentProduction);
//        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            dataModel.removeItem(currentProduction);
//            currentProduction = null;
//        }
//    }
//    public void create() {
//        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
//        if (!JsfUtil.isValidationFailed()) {
//            reset();
//            loadProductions();
//        }
//    }
//    public void cancel() {
//        reset();
//        loadProductions();
//    }
//
//    public void update() {
//        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
//    }
//
//    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
//        if (currentProduction != null) {
//            //setEmbeddableKeys();
//            try {
//                if (persistAction != JsfUtil.PersistAction.DELETE) {
//                    productionBean.edit(currentProduction);
//                } else {
//                    productionBean.remove(currentProduction);
//                }
//                JsfUtil.addSuccessMessage(successMessage);
//            } catch (EJBException ex) {
//                String msg = "";
//                Throwable cause = ex.getCause();
//                if (cause != null) {
//                    msg = cause.getLocalizedMessage();
//                }
//                if (msg.length() > 0) {
//                    JsfUtil.addErrorMessage(msg);
//                } else {
//                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            }
//        }
//    }
    public void loadProductions() {
        if (periodYear != null && periodMonth != null && currentTerminal != null) {
            forecastDetails = productionBean.getTerminalProduction(periodYear, periodMonth, currentTerminal);
        }
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

    public Terminal getCurrentTerminal() {
        return currentTerminal;
    }

    public void setCurrentTerminal(Terminal currentTerminal) {
        this.currentTerminal = currentTerminal;
    }

    public Double getDailySum() {
        log.log(Level.INFO, "Productions: {0}", productions);
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double dailySum = forecastDetails.stream()
                .mapToDouble(p -> p.getDailyProduction())
                .sum();
        return dailySum;
    }

    public Double getGrossSum() {
        log.log(Level.INFO, "productions is not empty {0}", forecastDetails);
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double grossProd = forecastDetails.stream()
                .mapToDouble(p -> p.getGrossProduction())
                .sum();
        return grossProd;
    }

    public Double getOwnEntitlementSum() {
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double ownEntitlement = forecastEntitlements.stream()
                .mapToDouble(p -> p.getOwnShareEntitlement())
                .sum();
        return ownEntitlement;
    }

    public Double getPartnerEntitlementSum() {
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double partnerEntitlement = forecastEntitlements.stream()
                .mapToDouble(p -> p.getPartnerShareEntitlement())
                .sum();
        return partnerEntitlement;
    }

    public Double getOpeningStockSum() {
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double openingStockSum = forecastEntitlements.stream()
                .mapToDouble(p -> p.getOpeningStock())
                .sum();
        return openingStockSum;
    }

    public Double getAvailabilitySum() {
        if (forecastDetails.isEmpty()) {
            return null;
        }
        Double availabilitySum = forecastEntitlements.stream()
                .mapToDouble(p -> p.getAvailability())
                .sum();

        return availabilitySum;
    }

    public Double getNomLiftingSum() {
        Integer cargoesSum = getCargoesSum();
        if (cargoesSum == null) {
            return null;
        }
        Double nomLiftingSum = cargoesSum * 950000.0;
        return nomLiftingSum;
    }

    public Integer getCargoesSum() {
        Double availabilitySum = getAvailabilitySum();
        if (availabilitySum == null) {
            return null;
        }
        Integer cargoesSum = (int) (availabilitySum / 950000.0);
        return cargoesSum;
    }

    public Double getClosingStockSum() {
        Double availabilitySum = getAvailabilitySum();
        if (availabilitySum == null) {
            return null;
        }
        return availabilitySum % 950000.0;
    }

}
