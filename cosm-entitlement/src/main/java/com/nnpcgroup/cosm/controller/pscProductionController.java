/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.entity.forecast.ForecastPK;
import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.*;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.forecast.jv.*;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 * @author 18359
 */
@Named(value = "pscProdController")
@SessionScoped
public class pscProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(pscProductionController.class.getName());

    @Inject
    private JvForecastServices jvForecastServices;

    @EJB
    private JvForecastDetailServices forecastBean;

    @EJB
    private JvForecastEntitlementServices entitlementBean;

    @EJB
    private CarryForecastEntitlementServices caEntitlementBean;

    @EJB
    private ModifiedCarryForecastEntitlementServices mcaEntitlementBean;

    @Inject
    private ContractServices contractBean;

    @Inject
    Principal principal;

    private JvForecast currentProduction;
    private List<JvForecast> productions;
    private List<JvForecastDetail> forecastDetails;
    private List<JvForecastDetail> editDetails;
    private JvForecastDetail currentForecastDetail;
    private List<JvForecastEntitlement> forecastEntitlements;
    private JvForecastEntitlement currentEntitlement;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;
    private boolean editMode;
    private boolean newForecast = false;
    private boolean newDetail = false;

    /**
     * Creates a new instance of JvController
     */
    public pscProductionController() {
        this.editMode = false;
    }

    public JvForecastServices getForecastBean() {
        return jvForecastServices;
    }

    public JvForecastDetailServices getForecastDetailBean() {
        return forecastBean;
    }

    public JvForecastEntitlementServices getEntitlementBean() {
        if (currentContract instanceof CarryContract) {
            return caEntitlementBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            return mcaEntitlementBean;
        } else if (currentContract instanceof JvContract) {
            return entitlementBean;
        } else {
            return entitlementBean;
        }
    }

    @Produces
    public JvForecastDetailServices produceForecastBean() {
        return forecastBean;
    }

    public JvForecast getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvForecast currentProduction) {
        this.currentProduction = currentProduction;
    }

    public AlternativeFundingForecastDetail getCurrentAfProduction() {
        if (currentForecastDetail instanceof AlternativeFundingForecastDetail) {
            return (AlternativeFundingForecastDetail) currentForecastDetail;
        }
        return null;
    }

    public AlternativeFundingForecastEntitlement getCurrentAfEntitlement() {
        if (currentEntitlement instanceof AlternativeFundingForecastEntitlement) {
            return (AlternativeFundingForecastEntitlement) currentEntitlement;
        }
        return null;
    }

    public void setCurrentAfProduction(AlternativeFundingForecastDetail afProduction) {
        if (afProduction != null) {
            this.currentForecastDetail = afProduction;
        }
    }

    public List<JvForecast> getProductions() {
        //loadProductions();
        return productions;
    }

    public void setProductions(List<JvForecast> productions) {
        this.productions = productions;
    }

    public JvForecastDetail getCurrentForecastDetail() {
        return currentForecastDetail;
    }

    public void setCurrentForecastDetail(JvForecastDetail currentForecastDetail) {
        this.currentForecastDetail = currentForecastDetail;
    }

    public List<JvForecastEntitlement> getForecastEntitlements() {
        return forecastEntitlements;
    }

    public void setForecastEntitlements(List<JvForecastEntitlement> forecastEntitlements) {
        this.forecastEntitlements = forecastEntitlements;
    }

    public JvForecastEntitlement getCurrentEntitlement() {
        return currentEntitlement;
    }

    public void setCurrentEntitlement(JvForecastEntitlement currentEntitlement) {
        this.currentEntitlement = currentEntitlement;
    }

    public void prepareEditForecastDetail(JvForecastDetail jvDetail) {
        setCurrentForecastDetail(jvDetail);
        if (currentForecastDetail != null) {
            currentContract = currentForecastDetail.getContract();
        }
        setEditMode(true);
    }

    public List<JvForecastDetail> getForecastDetails() {
        //loadForecastDetails();
        return forecastDetails;
    }

    public void setForecastDetails(List<JvForecastDetail> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public String prepareCreate() {
        reset();
        currentProduction = new JvForecast();
        currentForecastDetail = new JvForecastDetail();
        currentContractChanged();
        setForecastEmbeddableKeys();
        return "forecast-create";
    }

    public String prepareCreateForecast() {
        if (isForecastExists()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DataExists"));
            return null;
        }
        currentProduction = new JvForecast();
        setForecastEmbeddableKeys();
        forecastDetails = new ArrayList<>();
        return "forecast-create2";
    }

    public String prepareAddForecastDetail() {
        currentContractChanged();
        return "forecast-detail-create";
    }

    public String prepareEditDetail() {
        currentContractChanged();
        setNewDetail(true);
        return "forecast-detail-edit";
    }

    private void setNewDetail(boolean isNew) {
        newDetail = isNew;
    }

    public boolean isNewDetail() {
        return newDetail;
    }

    public String prepareUpdateForecast() {
        loadFiscalMonthlyProduction();
        if (currentProduction == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoForecastData"));
            return null;
        }
        setEditMode(true);
        return "forecast-edit2";
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
        }

    }

    public void destroyForecastDetail() {
        persistForecastDetail(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
//            loadForecastDetails();
            loadFiscalMonthlyProduction();

        }
    }

    public void destroyForecastDetail(JvForecastDetail prod) {
//        setCurrentForecastDetail(prod);
//        destroyForecastDetail();
        if (currentProduction != null) {
            removeForecastDetail(prod);

            removeEntitlement(prod);
            try {
                getForecastBean().edit(currentProduction);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                LOG.log(Level.WARNING, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }

//        try {
//            getForecastDetailBean().delete(prod.getPeriodYear(), prod.getPeriodMonth(), prod.getContract());
//            reset();
//            loadFiscalMonthlyProduction();
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
//        } catch (Exception ex) {
//            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            LOG.log(Level.WARNING, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//        }
    }

    public void destroy(JvForecast prod) {
        setCurrentProduction(prod);
        destroy();
    }

    public void removeForecastDetail(JvForecastDetail forecastDetail) {
        List<JvForecastDetail> fDetails;
        if (currentProduction != null) {
            fDetails = currentProduction.getForecastDetails();
            if (fDetails != null) {
                fDetails.remove(forecastDetail);
            }
        }
    }

    public void removeEntitlement(JvForecastDetail detail) {
        List<JvForecastEntitlement> entitlements;
        if (currentProduction != null) {
            entitlements = currentProduction.getEntitlements();

            for (JvForecastEntitlement ent : entitlements) {
                if (isEqualKeys(detail, ent)) {
                    entitlements.remove(ent);
                }
            }

        }
    }

    private boolean isEqualKeys(JvForecastDetail detail, JvForecastEntitlement ent) {
        JvForecastDetailPK detailPK = detail.getForecastDetailPK();
        JvForecastEntitlementPK entitlementPK = ent.getEntitlementPK();

        if (!detailPK.getForecastPK().equals(entitlementPK.getForecastPK())) {
            return false;
        }

        return detailPK.getContractPK().equals(entitlementPK.getContractPK());
    }

    public void create() {
        persistForecastDetail(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            currentContractChanged();
            //loadProductions();
//            setNewForecast(false);
        }
    }

    public String addForecastDetail() {
//        if (forecastDetails == null) {
//            forecastDetails = new ArrayList<>();
//        }
//        forecastDetails.add(currentForecastDetail);
        addForecastDetail(currentForecastDetail);
        addEntitlement(currentEntitlement);

        return "forecast-create2";
    }

    public void addForecastDetail(JvForecastDetail jvDetail) {
        if (currentProduction != null) {
            currentProduction.addForecastDetail(jvDetail);
        }

    }

    public void addEntitlement(JvForecastEntitlement entitlement) {
        if (currentProduction != null) {
            currentProduction.addEntitlement(entitlement);
        }

    }

    public String editForecastDetail() {
        if (!isNewDetail()) {
            if (editDetails == null) {
                editDetails = new ArrayList<>();
            }
            editDetails.add(currentForecastDetail);
        } else {
            addForecastDetail(currentForecastDetail);
            setNewDetail(false);
        }

        return "forecast-edit2";
    }

    public String createForecast() {
        //currentProduction.setForecastDetails(forecastDetails);
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            currentContract = null;
            loadFiscalMonthlyProduction();
//            setNewForecast(false);
            return "forecast2";
        }
        return null;
    }

    public void cancel() {
        reset();
        currentContractChanged();
        // loadProductions();
        disableEditMode();
        setNewForecast(false);
    }

    public void cancelForecast() {
        reset2();
        disableEditMode();
        loadForecastDetails();

    }

    public String cancelEditDetail() {
        currentForecastDetail = null;
        return "forecast-edit2";
    }

    public String cancelForecastDetail() {
        currentForecastDetail = null;
        return "forecast-create2";
    }

    public void update() {
        persistForecastDetail(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));

        if (!JsfUtil.isValidationFailed()) {

        }

    }

    public void performAutomaticStockAdjustment(JvForecastEntitlement entitlementToAdjust) {
        LOG.log(Level.INFO, "Performing automatic stock adjustment...");
        JvForecastEntitlement thisEntitlement = entitlementToAdjust;
        JvForecastEntitlement nextEntitlement;
        while ((nextEntitlement = (JvForecastEntitlement) getEntitlementBean().getNextMonthProduction(thisEntitlement)) != null) {
            try {
                LOG.log(Level.INFO, "Adjusting {0} {1}/{2}...",
                        new Object[]{nextEntitlement.getContract(),
                            nextEntitlement.getPeriodYear(),
                            nextEntitlement.getPeriodMonth()});

                getEntitlementBean().enrich(nextEntitlement);
                getEntitlementBean().edit(nextEntitlement);
                // adjforecasts.add(nextForecast);
                thisEntitlement = nextEntitlement;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
            }
        }

        // getForecastDetailBean().edit(adjforecasts);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StockAdjustmentSuccess"));
    }

    public void performAutomaticStockAdjustment(JvForecastEntitlement entitlementToAdjust, Double grossProd) {
        LOG.log(Level.INFO, "Performing automatic stock adjustment...");
        JvForecastEntitlement thisEntitlement = entitlementToAdjust;
        JvForecastEntitlement nextEntitlement;
        while ((nextEntitlement = (JvForecastEntitlement) getEntitlementBean().getNextMonthProduction(thisEntitlement)) != null) {
            try {
                LOG.log(Level.INFO, "Adjusting {0} {1}/{2}...",
                        new Object[]{nextEntitlement.getContract(),
                            nextEntitlement.getPeriodYear(),
                            nextEntitlement.getPeriodMonth()});

                getEntitlementBean().enrich(nextEntitlement, grossProd);
                getEntitlementBean().edit(nextEntitlement);
                // adjforecasts.add(nextForecast);
                thisEntitlement = nextEntitlement;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
            }
        }

        // getForecastDetailBean().edit(adjforecasts);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StockAdjustmentSuccess"));
    }

    public String updateForecast() {
        if (currentProduction != null) {
//            persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
            getForecastBean().edit(currentProduction);
            if (editDetails != null) {
                performAutomaticStockAdjustment(editDetails);
                editDetails = null;
            }
        }

        if (!JsfUtil.isValidationFailed()) {
            reset();
//            currentContract = null;
            loadFiscalMonthlyProduction();
//            setNewForecast(false);
            disableEditMode();
            return "forecast2";
        }
        return null;
    }

    public void performAutomaticStockAdjustment(JvForecastDetail jvDetail) {
        JvForecastEntitlement entitlement = getForecastEntitlement(jvDetail);
        performAutomaticStockAdjustment(entitlement, jvDetail.getGrossProduction());
    }

    public void performAutomaticStockAdjustment(List<JvForecastDetail> detailsToAdjust) {
        for (JvForecastDetail jvDetail : detailsToAdjust) {
            performAutomaticStockAdjustment(jvDetail);
        }
    }

    public JvForecastEntitlement getForecastEntitlement(JvForecastDetail jvDetail) {
        return new JvForecastEntitlement();//TODO:Implement
    }

    public void performAutomaticEntitlementAdjustment(List<JvForecastEntitlement> entitlementsToAdjust) {
        for (JvForecastEntitlement jvEntitlement : entitlementsToAdjust) {
            performAutomaticStockAdjustment(jvEntitlement);
        }
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentProduction != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getForecastBean().edit(currentProduction);
                } else {
                    getForecastBean().remove(currentProduction);
                }
                JsfUtil.addSuccessMessage(successMessage);
                LOG.log(Level.INFO, "{0} {1}/{2}/{3}",
                        new Object[]{successMessage,
                            currentProduction.getPeriodYear(),
                            currentProduction.getPeriodMonth(),
                            currentProduction.getFiscalArrangement().getTitle()});
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                    LOG.log(Level.WARNING, msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                    LOG.log(Level.WARNING, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                LOG.log(Level.WARNING, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    private void persistForecastDetail(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentForecastDetail != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getForecastDetailBean().edit(currentForecastDetail);
                    if (isEditMode()) {
                        performAutomaticStockAdjustment(currentForecastDetail);
                        disableEditMode();
                    }

                } else {
                    getForecastDetailBean().remove(currentForecastDetail);
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

    @Deprecated
    public void loadProductions() {
        if (periodYear != null) {
            if (periodMonth != null) {
                handleMonthlyProduction();
            } else {
                handleAnnualProduction();
            }
        }
    }

    public void loadForecastDetails() {
        if (periodYear != null) {
            if (periodMonth != null) {
                handleMonthlyProduction();
            } else {
                handleAnnualProduction();
            }
        }
        if (forecastDetails == null) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NoForecastData"));
            LOG.log(Level.INFO, ResourceBundle.getBundle("/Bundle").getString("NoForecastData")
            );
        }
    }

    private void handleAnnualProduction() {
        if (currentFiscalArrangement != null) {
            if (currentContract == null) {
                forecastDetails = getForecastDetailBean().findAnnualProduction(periodYear, currentFiscalArrangement);
                forecastEntitlements = getEntitlementBean().findAnnualProduction(periodYear, currentFiscalArrangement);
            } else {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, currentContract);
                forecastEntitlements = getEntitlementBean().findByContractPeriod(periodYear, currentContract);
            }
        }
        //return forecastDetails;
    }

    private List<JvForecastDetail> handleMonthlyProduction() {
        if (currentFiscalArrangement != null) {
            if (currentContract == null) {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
                forecastEntitlements = getEntitlementBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            } else {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, periodMonth, currentContract);
                forecastEntitlements = getEntitlementBean().findByContractPeriod(periodYear, periodMonth, currentContract);
            }

        } else {
            forecastDetails = getForecastDetailBean().findByYearAndMonth(periodYear, periodMonth);
            forecastEntitlements = getEntitlementBean().findByYearAndMonth(periodYear, periodMonth);
        }

        return forecastDetails;
    }

    public void loadFiscalMonthlyProduction() {
        if (periodYear != null && periodMonth != null && currentFiscalArrangement != null) {
            currentProduction = findForecast(periodYear, periodMonth, currentFiscalArrangement);

            if (currentProduction == null) {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NoForecastData"));
                LOG.log(Level.INFO, ResourceBundle.getBundle("/Bundle").getString("NoForecastData")
                );
            }
        }
    }

    private JvForecast findForecast(Integer periodYear, Integer periodMonth, FiscalArrangement currentFiscalArrangement) {
//        ForecastPK forecastPK = new ForecastPK();
//        forecastPK.setPeriodYear(periodYear);
//        forecastPK.setPeriodMonth(periodMonth);
//        forecastPK.setFiscalArrangementId(currentFiscalArrangement.getId());
//        return getForecastBean().find(forecastPK);
//Fixed N+1 Problem
        JvForecast jvForecast = getForecastBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
        return jvForecast;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void enableEditMode() {
        setEditMode(true);
        dailyProductionChanged();
    }

    public void disableEditMode() {
        setEditMode(false);
    }

    private void checkNull() throws Exception {
        if (periodYear == null || periodMonth == null || currentContract == null) {
            throw new Exception("Null value encountered!");
        }
    }

    public void dailyProductionChanged() {
        LOG.log(Level.INFO,
                "Daily production volume changed {0}",
                new Object[]{currentForecastDetail.getDailyProduction()});
        try {
            checkNull();
            getForecastDetailBean().enrich(currentForecastDetail);
            getEntitlementBean().enrich(currentEntitlement, currentForecastDetail.getGrossProduction());
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("NullEncountered"));

        }
    }

    public void alternativeFundingCostListener() {
        AlternativeFundingForecastEntitlementServices afBean = (AlternativeFundingForecastEntitlementServices) getEntitlementBean();
        try {
            afBean.computeAlternativeFunding(getCurrentAfEntitlement());
        } catch (Exception rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed {0}...", currentEntitlement);
        getEntitlementBean().openingStockChanged(currentEntitlement);
    }

    public void liftingChanged() {
        LOG.log(Level.INFO, "Exportable volume changed: NNPC {0}, Partner {1}",
                new Object[]{currentEntitlement.getLifting(), currentEntitlement.getPartnerLifting()});
        getEntitlementBean().computeClosingStock(currentEntitlement);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        try {
            getForecastDetailBean().enrich(currentForecastDetail);
        } catch (Exception rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    private void reset() {
        currentProduction = null;
        currentForecastDetail = null;
        currentEntitlement = null;
        productions = null;
        forecastDetails = null;
        forecastEntitlements = null;
//        currentContract = null;
    }

    private void reset2() {
        currentProduction = null;
        currentForecastDetail = null;
        productions = null;
//        currentContract = null;
        forecastDetails = null;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    public FiscalArrangement getCurrentFiscalArrangement() {
        return currentFiscalArrangement;
    }

    public void setCurrentFiscalArrangement(FiscalArrangement currentFiscalArrangement) {
        this.currentFiscalArrangement = currentFiscalArrangement;
    }

    public Contract getCurrentContract() {
        return currentContract;
    }

    public void setCurrentContract(Contract currentContract) {
        this.currentContract = currentContract;
    }

    public SelectItem[] getContractSelectOne() {
        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return JsfUtil.getSelectItems(contracts, false);

    }

    public void fiscalArrangementChanged() {
        currentContract = null;
    }

    public boolean isNewForecast() {
        return newForecast;
    }

    public void setNewForecast(boolean newForecast) {
        this.newForecast = newForecast;
    }

    public Double getDailySum() {
        Double dailySum = forecastDetails.stream()
                .mapToDouble(p -> p.getDailyProduction())
                .sum();
        return dailySum;
    }

    public Double getGrossSum() {
        Double grossProd = forecastDetails.stream()
                .mapToDouble(p -> p.getGrossProduction())
                .sum();
        return grossProd;
    }

    public Double getOwnEntitlementSum() {
        Double ownEntitlement = forecastEntitlements.stream()
                .mapToDouble(p -> p.getOwnShareEntitlement())
                .sum();
        return ownEntitlement;
    }

    public Double getPartnerEntitlementSum() {
        Double partnerEntitlement = forecastEntitlements.stream()
                .mapToDouble(p -> p.getPartnerShareEntitlement())
                .sum();
        return partnerEntitlement;
    }

    public Double getOpeningStockSum() {
        Double openingStockSum = forecastEntitlements.stream()
                .mapToDouble(p -> p.getOpeningStock())
                .sum();
        return openingStockSum;
    }

    public Double getAvailabilitySum() {
        Double availabilitySum = forecastEntitlements.stream()
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

    public boolean isFiscalArrangementAfContract() {
        return (getCurrentEntitlement() instanceof AlternativeFundingForecastEntitlement);
    }

    public void currentContractChanged() {// throws Exception {
        if (currentContract instanceof CarryContract) {
            currentForecastDetail = new CarryForecastDetail();
            currentEntitlement = new CarryForecastEntitlement();
            setNewForecast(true);
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentForecastDetail = new ModifiedCarryForecastDetail();
            currentEntitlement = new ModifiedCarryForecastEntitlement();
            setNewForecast(true);
        } else if (currentContract instanceof JvContract) {
            currentForecastDetail = new JvForecastDetail();
            currentEntitlement = new JvForecastEntitlement();
            setNewForecast(true);
        } else {
            LOG.fine("Undefined contract selection...");
            currentForecastDetail = null;
            currentEntitlement = null;
        }

        if (currentForecastDetail != null && currentEntitlement != null) {

            if (periodYear != null && periodMonth != null && currentContract != null) {
                setForecastDetailEmbeddableKeys();
                setEntitlementEmbeddableKeys();
            }
        }
    }

    public JvForecast getContract(ForecastPK fPK) {
        return (JvForecast) getForecastDetailBean().find(fPK);
    }

    private void setEntitlementEmbeddableKeys() {
        JvForecastEntitlementPK fPK = new JvForecastEntitlementPK(currentProduction.getForecastPK(), currentContract.getContractPK());
        currentEntitlement.setEntitlementPK(fPK);

        currentEntitlement.setPeriodYear(periodYear);
        currentEntitlement.setPeriodMonth(periodMonth);
        currentEntitlement.setFiscalArrangement(currentFiscalArrangement);
        currentEntitlement.setContract(currentContract);

        currentEntitlement.setForecast(currentProduction);

        currentEntitlement.setCurrentUser(principal.getName());
    }

    private void setForecastDetailEmbeddableKeys() {
        JvForecastDetailPK fPK = new JvForecastDetailPK(currentProduction.getForecastPK(), currentContract.getContractPK());
        currentForecastDetail.setForecastDetailPK(fPK);

        currentForecastDetail.setPeriodYear(periodYear);
        currentForecastDetail.setPeriodMonth(periodMonth);
        currentForecastDetail.setFiscalArrangement(currentFiscalArrangement);
        currentForecastDetail.setContract(currentContract);

        currentForecastDetail.setForecast(currentProduction);

        currentForecastDetail.setCurrentUser(principal.getName());
    }

    private void setForecastEmbeddableKeys() {
        ForecastPK fPK = new ForecastPK();
        fPK.setPeriodYear(periodYear);
        fPK.setPeriodMonth(periodMonth);
        fPK.setFiscalArrangementId(currentFiscalArrangement.getId());

        currentProduction.setForecastPK(fPK);
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);

        currentProduction.setFiscalArrangement(currentFiscalArrangement);

        currentProduction.setCurrentUser(principal.getName());
    }

    public void periodMonthChanged() {
        if (isNewForecast()) {
            setForecastDetailEmbeddableKeys();
        }
    }

    public boolean isEnableControlButton() {
        return periodYear != null && periodMonth != null && currentFiscalArrangement != null;
    }

    public boolean isForecastExists() {
        return findForecast(periodYear, periodMonth, currentFiscalArrangement) != null;
    }

}
