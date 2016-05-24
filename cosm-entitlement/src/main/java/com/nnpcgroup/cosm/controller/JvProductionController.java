/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionServices;
import com.nnpcgroup.cosm.ejb.production.jv.CarryProductionServices;
import com.nnpcgroup.cosm.ejb.production.jv.JvProduction;
import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionServices;
import com.nnpcgroup.cosm.ejb.production.jv.RegularProductionServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.contract.RegularContract;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProduction;
import com.nnpcgroup.cosm.entity.production.jv.CarryProduction;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProduction;
import com.nnpcgroup.cosm.entity.production.jv.RegularProduction;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 *
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

    private Production currentProduction;
    private List<Production> productions;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement currentFiscalArrangement;
    private Contract currentContract;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
        LOG.info("JvActualProductionController::constructor activated...");
        // LOG.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public JvProductionServices getProductionBean() {
        if (currentContract instanceof RegularContract) {
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
        this.currentFiscalArrangement = (currentProduction != null)
                ? currentProduction.getContract().getFiscalArrangement() : null;
        this.currentContract = (currentProduction != null)
                ? currentProduction.getContract() : null;
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
        LOG.log(Level.INFO, "Contract Selected...{0}", currentContract);
        if (currentContract instanceof RegularContract) {
            currentProduction = new RegularProduction();
        } else if (currentContract instanceof CarryContract) {
            currentProduction = new CarryProduction();
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentProduction = new ModifiedCarryProduction();
        } else {
            LOG.log(Level.INFO, "Undefined contract selection...{0}", currentContract);
            //throw new Exception("Undefined contract type");
        }

        if (currentProduction != null) {
            currentProduction.setContract(currentContract);

            if (periodYear != null && periodMonth != null) {
                setEmbeddableKeys();

            }
        }
    }

    private void setEmbeddableKeys() {
        //ProductionPK fPK = new ProductionPK(periodYear, periodMonth, currentContract);
        //currentProduction.setProductionPK(fPK);
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        currentProduction.setContract(currentContract);
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

    public void actualize(Forecast forecast) {
        LOG.log(Level.INFO, "************actualizing {0}...", forecast);
        reset();
        setCurrentContract(forecast.getContract());
        Production production = null;
        ProductionPK pPK = new ProductionPK(
                forecast.getPeriodYear(),
                forecast.getPeriodMonth(),
                forecast.getContract()
        );
//        production = (Production) getProductionBean().findByContractPeriod(
//                forecast.getForecastPK().getPeriodYear(),
//                forecast.getForecastPK().getPeriodMonth(),
//                forecast.getContract());
        production = (Production) getProductionBean().find(pPK);
        LOG.log(Level.INFO, "************findByContractStreamPeriod returning {0}...", currentProduction);

        if (production == null) {
            LOG.log(Level.INFO, "************actualizing returning new JV Production instance...");

//TODO:find better way to evaluate datatype
            if (forecast instanceof ModifiedCarryForecast) {
                production = new ModifiedCarryProduction();
            } else if (forecast instanceof CarryForecast) {
                production = new CarryProduction();
            } else if (forecast instanceof RegularForecast) {
                production = new RegularProduction();
            } else {
                //something is wrong
                LOG.log(Level.INFO, "Something is wrong! Forecast type not determined {0}...", forecast);
            }

            LOG.log(Level.INFO, "************getProductionBean().createInstance() returning {0}...", currentProduction);
            //production.setProductionPK(pPK);
            production.setContract(forecast.getContract());

            // getProductionBean().enrich(currentProduction);
        }
        setCurrentProduction(production);
        setPeriodYear(forecast.getPeriodYear());
        setPeriodMonth(forecast.getPeriodMonth());
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

}
