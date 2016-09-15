/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.production.jv.*;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.*;
import com.nnpcgroup.cosm.entity.forecast.jv.*;
import com.nnpcgroup.cosm.entity.production.jv.*;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 18359
 */
@Named(value = "jvActualController")
@SessionScoped
public class JvProductionController implements Serializable {

    private static final Logger LOG = Logger.getLogger(JvProductionController.class.getName());
    private static final long serialVersionUID = -5506490644508725206L;

    @Inject
    private JvProductionServices productionBean;

    @EJB
    private JvProduction defaultProductionBean;

    @EJB
    private RegularProductionServices regProductionBean;

    @EJB
    private CarryProductionServices caProductionBean;

    @EJB
    private ModifiedCarryProductionServices mcaProductionBean;

    @EJB
    private ContractServices contractBean;

    @EJB
    private FiscalArrangementBean fiscalBean;

    private Production currentProduction;
    private List<Production> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;
    private boolean directActualizing = false;
    private boolean editMode;
    private boolean newProduction = false;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
    }

    public JvProductionServices getProductionBean() {
        if (currentContract instanceof JvContract) {
            return regProductionBean;
        } else if (currentContract instanceof CarryContract) {
            return caProductionBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            return mcaProductionBean;
        } else {
            return productionBean;
        }
    }

    @Produces
    public JvProductionServices produceProductionBean() {
        return defaultProductionBean;
    }

    public Production getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(Production currentProduction) {
        this.currentProduction = currentProduction;

        ContractPK contractPK;// = new ContractPK();
//        contractPK.setFiscalArrangementId(currentProduction.getFiscalArrangementId());
//        contractPK.setCrudeTypeCode(currentProduction.getCrudeTypeCode());
        contractPK = currentProduction.getContract().getContractPK();
        currentContract = contractBean.find(contractPK);
        if (currentContract != null) {
            // currentFiscalArrangement = currentContract.getFiscalArrangement();
        }

    }

    public AlternativeFundingProduction getCurrentAfProduction() {
        if (currentProduction instanceof AlternativeFundingProduction) {
            return (AlternativeFundingProduction) currentProduction;
        }
        return null;
    }

    public void setCurrentAfProduction(AlternativeFundingProduction afProduction) {
        if (afProduction != null) {
            this.currentProduction = afProduction;
        }
    }

    public boolean isFiscalArrangementAfContract() {
        return (getCurrentProduction() instanceof AlternativeFundingProduction);
    }

    public void alternativeFundingCostListener() {
        AlternativeFundingProductionServices afBean = (AlternativeFundingProductionServices) getProductionBean();
        afBean.computeAlternativeFunding(getCurrentAfProduction());
    }

    public void currentContractChanged() {
        if (currentContract instanceof JvContract) {
            currentProduction = new RegularProduction();
            setNewProduction(true);
        } else if (currentContract instanceof CarryContract) {
            currentProduction = new CarryProduction();
            setNewProduction(true);
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentProduction = new ModifiedCarryProduction();
            setNewProduction(true);
        } else {
            LOG.log(Level.INFO, "Undefined contract selection...{0}", currentContract);
        }

        if (currentProduction != null) {
            if (periodYear != null && periodMonth != null) {
                setEmbeddableKeys();
            }
        }
    }

    private void setEmbeddableKeys() {
        ProductionPK pPK = new ProductionPK();
        pPK.setPeriodYear(periodYear);
        pPK.setPeriodMonth(periodMonth);
        pPK.setContract(currentContract.getContractPK());
        currentProduction.setProductionPK(pPK);

        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        currentProduction.setContract(currentContract);
//        currentContract.addForecast(currentProduction);
//        currentProduction.setContract(currentContract);
    }
    
    public void periodMonthChanged(){
        if(isNewProduction()){
            setEmbeddableKeys();
        }
    }

    public boolean isNewProduction() {
        return newProduction;
    }

    public void setNewProduction(boolean newProduction) {
        this.newProduction = newProduction;
    }

    
    public List<Production> getProductions() {
        return productions;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }

    public void loadProductions() {
        if (periodYear != null) {
            if (periodMonth != null) {
                if (currentFiscalArrangement != null) {
                    if (currentContract == null) {
                        productions = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
                    } else {
                        productions = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentContract);
                    }

                } else {
                    productions = getProductionBean().findByYearAndMonth(periodYear, periodMonth);
                }

            } else if (currentFiscalArrangement != null) {
                if (currentContract == null) {
                    productions = getProductionBean().findAnnualProduction(periodYear, currentFiscalArrangement);
                } else {
                    productions = getProductionBean().findByContractPeriod(periodYear, currentContract);
                }
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
        grossProductionChanged();
    }

    public void disableEditMode() {
        setEditMode(false);
    }

    public void stockAdjustmentChangedEditMode() {
        setEditMode(true);
        stockAdjustmentChanged();
    }

    public void grossProductionChanged() {
        getProductionBean().grossProductionChanged(currentProduction);
        LOG.log(Level.INFO,
                "Own entmt={0},Partner entmt={1}, Stock Adj={2}...",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement(),
                    currentProduction.getStockAdjustment()});

    }

    public void stockAdjustmentChanged() {
        getProductionBean().grossProductionChanged(currentProduction);
        LOG.log(Level.INFO,
                "Own entmt={0},Partner entmt={1}, Stock Adj={2}...",
                new Object[]{currentProduction.getOwnShareEntitlement(),
                    currentProduction.getPartnerShareEntitlement(),
                    currentProduction.getStockAdjustment()});

    }

    public void operatorDeclaredVolumeListener() {
        getProductionBean().computeOperatorDeclaredEquity(currentProduction);
    }

    public void liftingChanged() {
        LOG.log(Level.INFO, "Lifting changed...");
        getProductionBean().liftingChanged(currentProduction);

//        Double openingStock = currentProduction.getOpeningStock();
//        Double entitlement = currentProduction.getOwnShareEntitlement();
//        Double lifting = currentProduction.getLifting();
//
//        Double partnerOpeningStock = currentProduction.getPartnerOpeningStock();
//        Double partnerEntitlement = currentProduction.getPartnerShareEntitlement();
//        Double partnerLifting = currentProduction.getPartnerLifting();
//
//        Double bucket = openingStock + entitlement + partnerOpeningStock + partnerEntitlement;
//
//        if (bucket - lifting - partnerLifting < 0) {
//            FacesMessage msg
//                    = new FacesMessage("Stock Lifting validation failed!",
//                            "Please check your availability and lifting volume");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//
//            throw new ValidatorException(msg);
//        }
    }

    public void openingStockChanged() {
        LOG.log(Level.INFO, "Opening Stock changed...");
        getProductionBean().openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        getProductionBean().grossProductionChanged(currentProduction);
    }

    private void reset() {
        currentProduction = null;
        productions = null;
//        currentContract = null;
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

    public SelectItem[] getContractSelectOne() {
        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return JsfUtil.getSelectItems(contracts, false);
    }

    public Contract getCurrentContract() {
        return currentContract;
    }

    public void setCurrentContract(Contract currentContract) {
        this.currentContract = currentContract;
    }

    public void fiscalArrangementChanged() {
        currentContract = null;
    }

    public void actualize(ForecastDetail forecastDetail) throws Exception {
        LOG.log(Level.INFO, "Actualizing {0}...", forecastDetail);
        reset();
        ContractPK cPK = forecastDetail.getContract().getContractPK();
        Contract contract = contractBean.find(cPK); //forecast.getContract();

//        setCurrentContract(forecast.getContract());
        setCurrentContract(contract);

        Production production = null;
        ProductionPK pPK = new ProductionPK(
                forecastDetail.getPeriodYear(),
                forecastDetail.getPeriodMonth(),
                forecastDetail.getContract().getContractPK()
        );
        production = (Production) getProductionBean().find(pPK);

        if (production == null) {
            LOG.log(Level.INFO, "Actualizing: Creating new JV Production instance...");

//TODO:find better way to evaluate datatype
            if (forecastDetail instanceof ModifiedCarryForecastDetail) {
                production = new ModifiedCarryProduction();
            } else if (forecastDetail instanceof CarryForecastDetail) {
                production = new CarryProduction();
            } else if (forecastDetail instanceof JvForecastDetail) {
                production = new RegularProduction();
            } else {
                //something is wrong
                LOG.log(Level.INFO, "Something is wrong! JvForecastDetailServices type not determined {0}...", forecastDetail);
                throw new Exception("JvForecastDetailServices type not determined");
            }

            LOG.log(Level.INFO, "************getProductionBean().createInstance() returning {0}...", currentProduction);
            //production.setProductionPK(pPK);
            production.setContract(forecastDetail.getContract());
            production.setPeriodYear(forecastDetail.getPeriodYear());
            production.setPeriodMonth(forecastDetail.getPeriodMonth());
//            production.setFiscalArrangementId(forecast.getContract().getContractPK().getFiscalArrangementId());
//            production.setCrudeTypeCode(forecast.getContract().getContractPK().getCrudeTypeCode());
            production.setProductionPK(pPK);

            // getProductionBean().enrich(currentProduction);
        }
        setCurrentProduction(production);
        setPeriodYear(forecastDetail.getPeriodYear());
        setPeriodMonth(forecastDetail.getPeriodMonth());
        setCurrentFiscalArrangement(contract.getFiscalArrangement());
        setDirectActualizing(false);//actualizing through targeted forecast and not directly through the entry actualizing interface
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            loadProductions();
        }
    }

    public void destroy(Production prod) {
        setCurrentProduction(prod);
        destroy();
    }

    public String prepareCreate() {
        reset();
        setDirectActualizing(true);
        currentContractChanged();
        return "actual-create";
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
            currentContractChanged();
            loadProductions();
//            setNewProduction(false);
        }
    }

    public void cancel() {
        reset();
        loadProductions();
        disableEditMode();
        setNewProduction(false);
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));

        if (isEditMode()) {
            List<Production> adjProductions = new ArrayList<>();
            Production thisProduction = currentProduction;
            Production nextProduction;
            while ((nextProduction = (Production) getProductionBean().getNextMonthProduction(thisProduction)) != null) {
                try {
                    getProductionBean().enrich(nextProduction);
                    getProductionBean().edit(nextProduction);
                    // adjProductions.add(nextProduction);
                    thisProduction = nextProduction;
                } catch (NoRealizablePriceException rpe) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, rpe);
                    JsfUtil.addErrorMessage(rpe, ResourceBundle.getBundle("/Bundle").getString("RealizablePriceErrorOccured"));
                }
            }

            // getForecastBean().edit(adjforecasts);
            disableEditMode();
        }
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentProduction != null) {
            //setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getProductionBean().edit(currentProduction);
                } else {
                    getProductionBean().remove(currentProduction);
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

    public boolean isDirectActualizing() {
        return directActualizing;
    }

    public void setDirectActualizing(boolean directActualizing) {
        this.directActualizing = directActualizing;
    }
}
