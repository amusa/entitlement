/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvAlternativeFundingForecastServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvModifiedCarryForecastServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvCarryForecastServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvRegularForecastServices;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.contract.RegularContract;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;

import javax.inject.Named;
import java.io.Serializable;
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
 *
 * @author 18359
 */
@Named(value = "jvProdController")
@SessionScoped
public class JvForecastController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(JvForecastController.class.getName());

    @Inject
    private JvForecastServices forecastBean;

    @EJB
    private JvForecast defaultForecastBean;

    @EJB
    private JvRegularForecastServices regForecastBean;

    @EJB
    private JvCarryForecastServices caForecastBean;

    @EJB
    private JvModifiedCarryForecastServices mcaForecastBean;

    @Inject
    private ContractServices contractBean;

    private Forecast currentProduction;
    private List<Forecast> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;

    /**
     * Creates a new instance of JvController
     */
    public JvForecastController() {
        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastServices getForecastBean() {
        if (currentContract instanceof RegularContract) {
            LOG.log(Level.INFO, "Returning RegularForecast bean...{0}", regForecastBean);
            return regForecastBean;
        } else if (currentContract instanceof CarryContract) {
            LOG.log(Level.INFO, "Returning CarryForecast bean...{0}", caForecastBean);
            return caForecastBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            LOG.log(Level.INFO, "Returning ModifiedCarryForecast bean...{0}", mcaForecastBean);
            return mcaForecastBean;
        } else {
            LOG.log(Level.INFO, "Returning Forecast bean...{0}", forecastBean);
            return forecastBean;
        }
    }

    @Produces
    public JvForecastServices produceForecastBean() {
        return defaultForecastBean;
    }

    public Forecast getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(Forecast currentProduction) {
        LOG.info("ProductionController::setProduction called...");
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

    public List<Forecast> getProductions() {
        LOG.info("ProductionController::getProductions called...");
        loadProductions();
        return productions;
    }

    public void setProductions(List<Forecast> productions) {
        LOG.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    public String prepareCreate() {
        LOG.info("prepareCreate called...");
        reset();
        return "forecast-create";
    }

    public void destroy() {
        LOG.log(Level.INFO, "Deleting {0}...", currentProduction);
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            //dataModel.removeItem(currentProduction);
            currentProduction = null;
        }
    }

    public void destroy(Forecast prod) {
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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

    public void productionVolumeChanged() {
        getForecastBean().enrich(currentProduction);
        LOG.log(Level.INFO,
                "Production Enriched::Own entmt={0},Partner entmt={1}",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement()
                });

    }

    public void alternativeFundingCostListener() {
        JvAlternativeFundingForecastServices afBean = (JvAlternativeFundingForecastServices) getForecastBean();
        afBean.computeAlternativeFunding(getCurrentAfProduction());
    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed...");
        getForecastBean().openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        getForecastBean().enrich(currentProduction);
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
        LOG.log(Level.INFO, "************JvProductionController::setPeriodYear called with value {0}", periodYear);
        this.periodYear = periodYear;
    }

    public Integer getPeriodMonth() {
        LOG.log(Level.INFO, "************JvProductionController::getPeriodMonth called. returning {0}...", periodMonth);

        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        LOG.log(Level.INFO, "************JvProductionController::setPeriodMonth called with value {0}", periodMonth);
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
        if (contractBean.isPersist(currentContract)) {
            LOG.log(Level.INFO, "Yeh!, {0} is persisting...", currentContract);
        } else {
            LOG.log(Level.INFO, "Ooh!, {0} is not persisting...", currentContract);
            contractBean.flush();
        }
    }

    public SelectItem[] getContractSelectOne() {
        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return JsfUtil.getSelectItems(contracts, true);

    }

    public List<Contract> getContractList() {
        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return contracts;
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

    public void currentContractChanged() throws Exception {
        LOG.log(Level.INFO, "Contract Selected...{0}", currentContract);

        if (currentContract instanceof RegularContract) {
            LOG.log(Level.INFO, "Regular Contract Selected...{0}", currentContract);
            currentProduction = new RegularForecast();
        } else if (currentContract instanceof CarryContract) {
            LOG.log(Level.INFO, "Carry Contract Selected...{0}", currentContract);
            currentProduction = new CarryForecast();
        } else if (currentContract instanceof ModifiedCarryContract) {
            LOG.log(Level.INFO, "Modified Carry Contract Selected...{0}", currentContract);
            currentProduction = new ModifiedCarryForecast();
        } else {
            LOG.log(Level.INFO, "Undefined contract selection...{0}", currentContract);
            throw new Exception("Undefined contract type");
            //currentProduction = new RegularForecast();

        }

        if (currentProduction != null) {

            if (periodYear != null && periodMonth != null) {
                setEmbeddableKeys();
            }
        }
    }

    public Forecast getContract(ForecastPK fPK) {
        return (Forecast) getForecastBean().find(fPK);
    }

    private void setEmbeddableKeys() {
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        currentProduction.setContract(currentContract);
        if (contractBean.isPersist(currentContract)) {
            LOG.log(Level.INFO, "Yeh!, {0} is persisting...", currentContract);
        } else {
            LOG.log(Level.INFO, "Ooh!, {0} is not persisting...", currentContract);
        }
    }
}
