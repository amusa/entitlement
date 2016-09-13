/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author 18359
 */
@Named(value = "jvProdController")
@SessionScoped
public class JvForecastController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    //private static final Logger LOG = Logger.getLogger(JvForecastController.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();

    @Inject
    private JvForecastServices jvForecastServices;

    @EJB
    private JvForecastDetailServices forecastBean;

    @EJB
    private CarryForecastDetailServices caForecastBean;

    @EJB
    private ModifiedCarryForecastDetailServices mcaForecastBean;

    @Inject
    private ContractServices contractBean;

    @EJB
    private FiscalArrangementBean fiscalBean;

    private JvForecast currentProduction;
    private List<JvForecast> productions;
    private List<JvForecastDetail> forecastDetails;
    private JvForecastDetail currentForecastDetail;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;
    private boolean editMode;
    private boolean newForecast = false;

    /**
     * Creates a new instance of JvController
     */
    public JvForecastController() {
        this.editMode = false;
        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastServices getForecastBean() {
        return jvForecastServices;
    }

    public JvForecastDetailServices getForecastDetailBean() {
        if (currentContract instanceof CarryContract) {
            return caForecastBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            return mcaForecastBean;
        } else if (currentContract instanceof JvContract) {
            return forecastBean;
        } else {
            return forecastBean;
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
//        this.currentFiscalArrangement = (currentProduction != null)
//                ? currentProduction.getContract().getFiscalArrangement() : null;
//        this.currentContract = (currentProduction != null)
//                ? currentProduction.getContract() : null;
    }

    public AlternativeFundingForecastDetail getCurrentAfProduction() {
        if (currentForecastDetail instanceof AlternativeFundingForecastDetail) {
            return (AlternativeFundingForecastDetail) currentForecastDetail;
        }
        return null;
    }

    public void setCurrentAfProduction(AlternativeFundingForecastDetail afProduction) {
        if (afProduction != null) {
            this.currentForecastDetail = afProduction;
        }
    }

    public List<JvForecast> getProductions() {
        loadProductions();
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

    public List<JvForecastDetail> getForecastDetails() {
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
        currentProduction = new JvForecast();
        setForecastEmbeddableKeys();
        forecastDetails = new ArrayList<>();
        return "forecast-create2";
    }

    public String prepareAddForecastDetail() {
        currentContractChanged();
        return "forecast-detail-create";
    }

    public String prepareUpdateForecast() {
        loadFiscalMonthlyProduction();
        return "forecast-edit2";
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            currentProduction = null;
        }
    }

    public void destroy(JvForecast prod) {
        setCurrentProduction(prod);
        destroy();
    }

    public void removeForecastDetail(JvForecastDetail forecastDetail) {
        forecastDetails.remove(forecastDetail);
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            currentContractChanged();
            loadProductions();
//            setNewForecast(false);
        }
    }

    public String addForecastDetail() {
        if (forecastDetails == null) {
            forecastDetails = new ArrayList<>();
        }
        forecastDetails.add(currentForecastDetail);
        return "forecast-create2";
    }

    public String createForecast() {
        currentProduction.setForecastDetails(forecastDetails);
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            currentContract = null;
            loadProductions();
//            setNewForecast(false);
            return "forecast2";
        }
        return null;
    }

    public void cancel() {
        reset();
        currentContractChanged();
        loadProductions();
        disableEditMode();
        setNewForecast(false);
    }

    public void cancelForecast() {
        reset2();
        loadForecastDetails();

    }

    public String cancelForecastDetail() {
        currentForecastDetail = null;
        return "forecast-create2";
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
        if (isEditMode()) {
            List<Forecast> adjforecasts = new ArrayList<>();
            JvForecast thisForecast = currentProduction;
            JvForecast nextForecast;
            while ((nextForecast = (JvForecast) getForecastDetailBean().getNextMonthProduction(thisForecast)) != null) {
                try {
                    getForecastDetailBean().enrich(nextForecast);
                    getForecastDetailBean().edit(nextForecast);
                    // adjforecasts.add(nextForecast);
                    thisForecast = nextForecast;
                } catch (NoRealizablePriceException rpe) {
                    Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
                    JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
                }
            }

            // getForecastDetailBean().edit(adjforecasts);
            disableEditMode();
        }
    }

    public void updateForecast() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
    }


    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentProduction != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getForecastDetailBean().edit(currentProduction);
                } else {
                    getForecastDetailBean().remove(currentProduction);
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
                Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

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
    }

    private List<JvForecastDetail> handleAnnualProduction() {
        if (currentFiscalArrangement != null) {
            if (currentContract == null) {
                forecastDetails = getForecastDetailBean().findAnnualProduction(periodYear, currentFiscalArrangement);
            } else {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, currentContract);
            }
        }
        return forecastDetails;
    }

    private List<JvForecastDetail> handleMonthlyProduction() {
        if (currentFiscalArrangement != null) {
            if (currentContract == null) {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            } else {
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, periodMonth, currentContract);
            }

        } else {
            forecastDetails = getForecastDetailBean().findByYearAndMonth(periodYear, periodMonth);
        }

        return forecastDetails;
    }

    private void loadFiscalMonthlyProduction() {
        if (periodYear != null && periodMonth != null && currentFiscalArrangement != null) {
            ForecastPK forecastPK = new ForecastPK();
            forecastPK.setPeriodYear(periodYear);
            forecastPK.setPeriodMonth(periodMonth);
            forecastPK.setFiscalArrangementId(currentFiscalArrangement.getId());
            currentProduction = getForecastBean().find(forecastPK);

            if (currentProduction != null) {
                //forecastDetails = currentProduction.getForecastDetails();
                forecastDetails = getForecastDetailBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            }
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void enableEditMode() {
        setEditMode(true);
        productionVolumeChanged();
    }

    public void disableEditMode() {
        setEditMode(false);
    }

    public void productionVolumeChanged() {
        try {
            getForecastDetailBean().enrich(currentForecastDetail);
            LOG.log(Level.INFO,
                    String.format("Production Enriched::Own entmt=%f, Partner entmt=%f",
                            new Object[]{currentForecastDetail.getOwnShareEntitlement(),
                                    currentForecastDetail.getPartnerShareEntitlement()
                            }));
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    public void alternativeFundingCostListener() {
        AlternativeFundingForecastDetailServices afBean = (AlternativeFundingForecastDetailServices) getForecastDetailBean();
        try {
            afBean.computeAlternativeFunding(getCurrentAfProduction());
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed...");
        getForecastDetailBean().openingStockChanged(currentProduction);
    }

    public void liftingChanged() {
        getForecastDetailBean().computeClosingStock(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        try {
            getForecastDetailBean().enrich(currentProduction);
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    private void reset() {
        //       currentProduction = null;
        currentForecastDetail = null;
        productions = null;
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
//        if (currentContract == null) {
//            return;
//        }
        this.currentContract = currentContract;//contractBean.find(currentContract);
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
                .mapToDouble(p -> p.getProductionVolume())
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
        Double ownEntitlement = forecastDetails.stream()
                .mapToDouble(p -> p.getOwnShareEntitlement())
                .sum();
        return ownEntitlement;
    }

    public Double getPartnerEntitlementSum() {
        Double partnerEntitlement = forecastDetails.stream()
                .mapToDouble(p -> p.getPartnerShareEntitlement())
                .sum();
        return partnerEntitlement;
    }

    public Double getOpeningStockSum() {
        Double openingStockSum = forecastDetails.stream()
                .mapToDouble(p -> p.getOpeningStock())
                .sum();
        return openingStockSum;
    }

    public Double getAvailabilitySum() {
        Double availabilitySum = forecastDetails.stream()
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
        return (getCurrentForecastDetail() instanceof AlternativeFundingForecastDetail);
    }

    public void currentContractChanged() {// throws Exception {
        if (currentContract instanceof CarryContract) {
            currentForecastDetail = new CarryForecastDetail();
            setNewForecast(true);
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentForecastDetail = new ModifiedCarryForecastDetail();
            setNewForecast(true);
        } else if (currentContract instanceof JvContract) {
            currentForecastDetail = new JvForecastDetail();
            setNewForecast(true);
        } else {
            LOG.info("Undefined contract selection...");
        }

        if (currentForecastDetail != null) {

            if (periodYear != null && periodMonth != null) {
                setForecastDetailEmbeddableKeys();
            }
        }
    }

    public JvForecast getContract(ForecastPK fPK) {
        return (JvForecast) getForecastDetailBean().find(fPK);
    }

    private void setForecastDetailEmbeddableKeys() {
        ForecastDetailPK fPK = new ForecastDetailPK();
        fPK.setForecast(currentProduction.getForecastPK());
        fPK.setContract(currentContract.getContractPK());

        currentForecastDetail.setForecastDetailPK(fPK);

        currentForecastDetail.setPeriodYear(periodYear);
        currentForecastDetail.setPeriodMonth(periodMonth);
        currentForecastDetail.setContract(currentContract);

        currentForecastDetail.setForecast(currentProduction);

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
        currentProduction.setForecastDetails(forecastDetails);

    }

    public void periodMonthChanged() {
        if (isNewForecast()) {
            setForecastDetailEmbeddableKeys();
        }
    }
}
