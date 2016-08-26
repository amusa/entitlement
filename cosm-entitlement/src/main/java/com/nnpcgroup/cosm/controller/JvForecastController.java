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
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
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

    //@Inject
    @EJB
    private JvForecastServices forecastBean;

    @EJB
    private CarryForecastServices caForecastBean;

    @EJB
    private ModifiedCarryForecastServices mcaForecastBean;

    @Inject
    private ContractServices contractBean;

    @EJB
    private FiscalArrangementBean fiscalBean;

    private JvForecast currentProduction;
    private List<JvForecast> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;
    private boolean editMode;

    /**
     * Creates a new instance of JvController
     */
    public JvForecastController() {
        this.editMode = false;
        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastServices getForecastBean() {
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
    public JvForecastServices produceForecastBean() {
        return forecastBean;
    }

    public JvForecast getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvForecast currentProduction) {
        this.currentProduction = currentProduction;
        this.currentFiscalArrangement = (currentProduction != null)
                ? currentProduction.getContract().getFiscalArrangement() : null;
        this.currentContract = (currentProduction != null)
                ? currentProduction.getContract() : null;
    }

    public AlternativeFundingForecast getCurrentAfProduction() {
        if (currentProduction instanceof AlternativeFundingForecast) {
            return (AlternativeFundingForecast) currentProduction;
        }
        return null;
    }

    public void setCurrentAfProduction(AlternativeFundingForecast afProduction) {
        if (afProduction != null) {
            this.currentProduction = afProduction;
        }
    }

    public List<JvForecast> getProductions() {
        loadProductions();
        return productions;
    }

    public void setProductions(List<JvForecast> productions) {
        this.productions = productions;
    }

    public String prepareCreate() {
        reset();
        return "forecast-create";
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
        disableEditMode();
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
        if (isEditMode()) {
            List<Forecast> adjforecasts = new ArrayList<>();
            JvForecast thisForecast = currentProduction;
            JvForecast nextForecast;
            while ((nextForecast = (JvForecast) getForecastBean().getNextMonthProduction(thisForecast)) != null) {
                try {
                    getForecastBean().enrich(nextForecast);
                    getForecastBean().edit(nextForecast);
                    // adjforecasts.add(nextForecast);
                    thisForecast = nextForecast;
                } catch (NoRealizablePriceException rpe) {
                    Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
                    JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
                }
            }

            // getForecastBean().edit(adjforecasts);
            disableEditMode();
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
        reset();
        if (periodYear != null && periodMonth != null) {
            if (currentFiscalArrangement == null) {
                //  productions = getForecastBean().findByYearAndMonth(periodYear, periodMonth);
            } else {
                productions = getForecastBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
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
            getForecastBean().enrich(currentProduction);
            LOG.log(Level.INFO,
                    String.format("Production Enriched::Own entmt=%f, Partner entmt=%f",
                            new Object[]{currentProduction.getOwnShareEntitlement(),
                                currentProduction.getPartnerShareEntitlement()
                            }));
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    public void alternativeFundingCostListener() {
        AlternativeFundingForecastServices afBean = (AlternativeFundingForecastServices) getForecastBean();
        try {
            afBean.computeAlternativeFunding(getCurrentAfProduction());
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed...");
        getForecastBean().openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        try {
            getForecastBean().enrich(currentProduction);
        } catch (NoRealizablePriceException rpe) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR, null, rpe);
            JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
        }
    }

    private void reset() {
        currentProduction = null;
        productions = null;
        currentContract = null;
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
        this.currentContract = contractBean.find(currentContract);
    }

    public SelectItem[] getContractSelectOne() {
        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return JsfUtil.getSelectItems(contracts, true);

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

    public boolean isFiscalArrangementAfContract() {
        return (getCurrentProduction() instanceof AlternativeFundingForecast);
    }

    public void currentContractChanged() {// throws Exception {
        if (currentContract instanceof CarryContract) {
            currentProduction = new CarryForecast();
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentProduction = new ModifiedCarryForecast();
        } else if (currentContract instanceof JvContract) {
            currentProduction = new JvForecast();
        } else {
            //throw new Exception("Undefined contract type");
            //currentProduction = new JvForecastServices();

        }

        if (currentProduction != null) {

            if (periodYear != null && periodMonth != null) {
                setEmbeddableKeys();
            }
        }
    }

    public JvForecast getContract(ForecastPK fPK) {
        return (JvForecast) getForecastBean().find(fPK);
    }

    private void setEmbeddableKeys() {
        ForecastPK fPK = new ForecastPK();
        fPK.setPeriodYear(periodYear);
        fPK.setPeriodMonth(periodMonth);
        fPK.setContract(currentContract.getContractPK());
        currentProduction.setForecastPK(fPK);

        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        currentProduction.setContract(currentContract);
//        currentContract.addForecast(currentProduction);
//        currentProduction.setContract(currentContract);
    }
}
