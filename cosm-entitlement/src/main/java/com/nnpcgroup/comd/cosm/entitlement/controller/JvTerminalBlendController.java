/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.controller.util.ProductionDataModel;
import com.nnpcgroup.comd.cosm.entitlement.ejb.JvActualProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.ejb.TerminalBlendServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvActualProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Terminal;
import com.nnpcgroup.comd.cosm.entitlement.entity.TerminalBlend;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author 18359
 */
@Named(value = "jvTermBlendController")
@SessionScoped
public class JvTerminalBlendController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvTerminalBlendController.class.getName());

    @EJB
    private TerminalBlendServices terminalBlendBean;
    
     @EJB
    private JvActualProductionServices productionBean;
     
    private TerminalBlend currentTerminalBlend;
    private List<TerminalBlend>allTerminalBlend;

    private JvActualProduction currentProduction;

    private List<JvActualProduction> productions;
    private ProductionDataModel dataModel;

    private Integer periodYear;
    private Integer periodMonth;
    private Terminal currentTerminal;
    
    /**
     * Creates a new instance of JvController
     */
    public JvTerminalBlendController() {
        
    }

    public ProductionDataModel getDataModel() {
        return dataModel;
    }

    public JvActualProduction getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvActualProduction currentProduction) {        
        this.currentProduction = currentProduction;
    }

    public List<JvActualProduction> getProductions() {       
        loadProductions();
        return productions;
    }

    public void setProductions(List<JvActualProduction> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    public void prepareCreate() {
        log.info("prepareCreate called...");
        currentProduction = new JvActualProduction();//terminalBlendBean.createInstance();
        if (periodYear != null && periodMonth != null) {
            currentProduction.setPeriodYear(periodYear);
            currentProduction.setPeriodMonth(periodMonth);
        }        
    }

    public void destroy() {
        log.log(Level.INFO, "Deleting {0}...", currentProduction);
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            dataModel.removeItem(currentProduction);
            currentProduction = null;
        }
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
        if (allTerminalBlend != null && !allTerminalBlend.isEmpty()) {
            //setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    terminalBlendBean.edit(allTerminalBlend);//TODO:Check!
                } else {
                    terminalBlendBean.remove(currentTerminalBlend);//TODO:Check!
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
        if (periodYear != null && periodMonth != null && currentTerminal != null) {
            productions = productionBean.getTerminalProduction(periodYear, periodMonth, currentTerminal);
            refreshDataModel();
        }
    }

    public void refreshDataModel() {
        log.log(Level.INFO, "Refreshing DataModel...");
        dataModel = new ProductionDataModel(productions);
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
