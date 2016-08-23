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
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
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

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
        LOG.info("JvActualProductionController::constructor activated...");
    }

    public JvProductionServices getProductionBean() {
        if (currentContract instanceof JvContract) {
            LOG.log(Level.INFO, "Returning RegularProduction bean...{0}", regProductionBean);
            return regProductionBean;
        } else if (currentContract instanceof CarryContract) {
            LOG.log(Level.INFO, "Returning CarryProduction bean...{0}", caProductionBean);
            return caProductionBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            LOG.log(Level.INFO, "Returning ModifiedCarryProduction bean...{0}", mcaProductionBean);
            return mcaProductionBean;
        } else {
            LOG.log(Level.INFO, "Returning Defualt Production bean...{0}", productionBean);
            return productionBean;
        }
    }

    @Produces
    public JvProductionServices produceProductionBean() {
        return defaultProductionBean;
    }

    public Production getCurrentProduction() {
        LOG.info("JvActualProductionController::getProduction called...");
        return currentProduction;
    }

    public void setCurrentProduction(Production currentProduction) {
        LOG.info("JvActualProductionController::setProduction called...");
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

    public void currentContractChanged() throws Exception {
        LOG.log(Level.INFO, "Contract Selected...{0}", currentContract);
        if (currentContract instanceof JvContract) {
            currentProduction = new RegularProduction();
        } else if (currentContract instanceof CarryContract) {
            currentProduction = new CarryProduction();
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentProduction = new ModifiedCarryProduction();
        } else {
            LOG.log(Level.INFO, "Undefined contract selection...{0}", currentContract);
            throw new Exception("Undefined contract type");
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

    public List<Production> getProductions() {
        LOG.info("JvActualProductionController::getProductions called...");
        return productions;
    }

    public void setProductions(List<Production> productions) {
        LOG.info("JvActualProductionController::setProductions called...");
        this.productions = productions;
    }

    public void loadProductions() {
        reset();
        if (periodYear != null && periodMonth != null) {
            if (currentFiscalArrangement == null) {
                // productions = getProductionBean().findByYearAndMonth(periodYear, periodMonth);
            } else {
                productions = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
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

    public void liftingChanged() {
        LOG.log(Level.INFO, "LIfting changed...");
        getProductionBean().liftingChanged(currentProduction);

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
        getProductionBean().openingStockChanged(currentProduction);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        getProductionBean().grossProductionChanged(currentProduction);
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

        List<Contract> contracts = null;

        if (currentFiscalArrangement != null) {
            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
        }

        return JsfUtil.getSelectItems(contracts, true);
    }

    public Contract getCurrentContract() {
        return currentContract;
    }

    public void setCurrentContract(Contract currentContract) {
        this.currentContract = currentContract;
    }

    public void actualize(Forecast forecast) throws Exception {
        LOG.log(Level.INFO, "************actualizing {0}...", forecast);
        reset();
        ContractPK cPK = forecast.getContract().getContractPK();
        Contract contract = contractBean.find(cPK); //forecast.getContract();

//        setCurrentContract(forecast.getContract());
        setCurrentContract(contract);

        Production production = null;
        ProductionPK pPK = new ProductionPK(
                forecast.getPeriodYear(),
                forecast.getPeriodMonth(),
                forecast.getContract().getContractPK()
        );
        production = (Production) getProductionBean().find(pPK);
        LOG.log(Level.INFO, "************findByContractStreamPeriod returning {0}...", currentProduction);

        if (production == null) {
            LOG.log(Level.INFO, "************actualizing returning new JV Production instance...");

//TODO:find better way to evaluate datatype
            if (forecast instanceof ModifiedCarryForecast) {
                production = new ModifiedCarryProduction();
            } else if (forecast instanceof CarryForecast) {
                production = new CarryProduction();
            } else if (forecast instanceof JvForecast) {
                production = new RegularProduction();
            } else {
                //something is wrong
                LOG.log(Level.INFO, "Something is wrong! JvForecastServices type not determined {0}...", forecast);
                throw new Exception("JvForecastServices type not determined");
            }

            LOG.log(Level.INFO, "************getProductionBean().createInstance() returning {0}...", currentProduction);
            //production.setProductionPK(pPK);
            production.setContract(forecast.getContract());
            production.setPeriodYear(forecast.getPeriodYear());
            production.setPeriodMonth(forecast.getPeriodMonth());
//            production.setFiscalArrangementId(forecast.getContract().getContractPK().getFiscalArrangementId());
//            production.setCrudeTypeCode(forecast.getContract().getContractPK().getCrudeTypeCode());
            production.setProductionPK(pPK);

            // getProductionBean().enrich(currentProduction);
        }
        setCurrentProduction(production);
        setPeriodYear(forecast.getPeriodYear());
        setPeriodMonth(forecast.getPeriodMonth());
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

    public void prepareCreate() {
        LOG.log(Level.INFO, "Preparing new instance of JvActualProduction for create...");
        // currentProduction = new RegularProduction();//getProductionBean().createInstance(); TODO:evaluate
//        currentProduction.setPeriodYear(periodYear);
//        currentProduction.setPeriodMonth(periodMonth);
        // setEmbeddableKeys();
        //return currentProduction;
        reset();
        setDirectActualizing(true);
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
