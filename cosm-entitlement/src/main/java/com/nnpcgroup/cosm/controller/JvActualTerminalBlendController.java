/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.Terminal;
import com.nnpcgroup.cosm.entity.production.jv.Production;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author 18359
 */
@Named(value = "jvActualTermBlendController")
@SessionScoped
public class JvActualTerminalBlendController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvActualTerminalBlendController.class.getName());

    @Inject
    private JvProductionServices productionBean;

    private Production currentProduction;

    private List<Production> productions;

    private Integer periodYear;
    private Integer periodMonth;
    private Terminal currentTerminal;

    /**
     * Creates a new instance of JvController
     */
    public JvActualTerminalBlendController() {
        productions = new ArrayList<>();
    }

    public Production getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(Production currentProduction) {
        log.info("ProductionController::setProduction called...");
        this.currentProduction = currentProduction;
    }

    public List<Production> getProductions() {
        log.info("ProductionController::getProductions called...");
        loadProductions();
        return productions;
    }

    public void setProductions(List<Production> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
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
            productions = productionBean.getTerminalProduction(periodYear, periodMonth, currentTerminal);
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

    public Double getGrossSum() {
        log.log(Level.INFO, "productions is not empty {0}", productions);
        if (productions.isEmpty()) {
            return null;
        }
        Double grossProd = productions.stream()
                .mapToDouble(p -> p.getGrossProduction())
                .sum();
        return grossProd;
    }

    public Double getOwnEntitlementSum() {
        if (productions.isEmpty()) {
            return null;
        }
        Double ownEntitlement = productions.stream()
                .mapToDouble(p -> p.getOwnShareEntitlement())
                .sum();
        return ownEntitlement;
    }

    public Double getPartnerEntitlementSum() {
        if (productions.isEmpty()) {
            return null;
        }
        Double partnerEntitlement = productions.stream()
                .mapToDouble(p -> p.getPartnerShareEntitlement())
                .sum();
        return partnerEntitlement;
    }

    public Double getOpeningStockSum() {
        if (productions.isEmpty()) {
            return null;
        }
        Double openingStockSum = productions.stream()
                .mapToDouble(p -> p.getOpeningStock())
                .sum();
        return openingStockSum;
    }

    public Double getAvailabilitySum() {
        if (productions.isEmpty()) {
            return null;
        }
        Double availabilitySum = productions.stream()
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