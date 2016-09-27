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
import java.security.Principal;
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
    private JvProductionDetailServices jvProductionBean;

    @EJB
    private CarryProductionDetailServices caProductionBean;

    @EJB
    private ModifiedCarryProductionDetailServices mcaProductionBean;

    @EJB
    private ContractServices contractBean;

    @EJB
    private FiscalArrangementBean fiscalBean;

    @Inject
    Principal principal;

    private JvProductionDetail currentProductionDetail;
    private JvProduction currentProduction;
    private List<JvProduction> productions;
    private List<JvProductionDetail> productionDetails;
    private List<JvProductionDetail> deleteDetails = null;
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

    @Produces
    public JvProductionDetailServices produceProductionDetailBean() {
        return jvProductionBean;
    }

    public JvProductionDetailServices getProductionDetailBean() {
        if (currentContract instanceof CarryContract) {
            return caProductionBean;
        } else if (currentContract instanceof ModifiedCarryContract) {
            return mcaProductionBean;
        } else if (currentContract instanceof JvContract) {
            return jvProductionBean;
        } else {
            return jvProductionBean;
        }
    }

    public JvProductionDetailServices getJvProductionDetailBean() {
        return jvProductionBean;
    }

    public JvProductionServices getProductionBean() {
        return productionBean;
    }

    public JvProductionDetail getCurrentProductionDetail() {
        return currentProductionDetail;
    }

    public void setCurrentProductionDetail(JvProductionDetail currentProductionDetail) {
        this.currentProductionDetail = currentProductionDetail;
    }

    public JvProduction getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(JvProduction currentProduction) {
        this.currentProduction = currentProduction;
    }

    public AlternativeFundingProductionDetail getCurrentAfProduction() {
        if (currentProductionDetail instanceof AlternativeFundingProductionDetail) {
            return (AlternativeFundingProductionDetail) currentProductionDetail;
        }
        return null;
    }

    public void setCurrentAfProduction(AlternativeFundingProductionDetail afProductionDetail) {
        if (afProductionDetail != null) {
            this.currentProductionDetail = afProductionDetail;
        }
    }

    public boolean isFiscalArrangementAfContract() {
        return (getCurrentProductionDetail() instanceof AlternativeFundingProductionDetail);
    }

    public void alternativeFundingCostListener() {
        AlternativeFundingProductionDetailServices afBean = (AlternativeFundingProductionDetailServices) getProductionDetailBean();
        afBean.computeAlternativeFunding(getCurrentAfProduction());
    }

    public void currentContractChanged() {
        if (currentContract instanceof CarryContract) {
            currentProductionDetail = new CarryProductionDetail();
            setNewProduction(true);
        } else if (currentContract instanceof ModifiedCarryContract) {
            currentProductionDetail = new ModifiedCarryProductionDetail();
            setNewProduction(true);
        } else if (currentContract instanceof JvContract) {
            currentProductionDetail = new JvProductionDetail();
            setNewProduction(true);
        } else {
            LOG.log(Level.FINE, "Undefined contract selection...{0}", currentContract);
        }

        if (currentProductionDetail != null) {
            if (periodYear != null && periodMonth != null && currentContract != null) {
                setProductionDetailEmbeddableKeys();
            }
        }
    }

    private void setProductionDetailEmbeddableKeys() {
        ProductionDetailPK pPK = new ProductionDetailPK();

        pPK.setProduction(currentProduction.getProductionPK());
        pPK.setContract(currentContract.getContractPK());

        currentProductionDetail.setProductionDetailPK(pPK);

        currentProductionDetail.setPeriodYear(periodYear);
        currentProductionDetail.setPeriodMonth(periodMonth);
        currentProductionDetail.setContract(currentContract);

        currentProductionDetail.setProduction(currentProduction);

        currentProductionDetail.setCurrentUser(principal.getName());
    }

    private void setProductionEmbeddableKeys() {
        ProductionPK pPK = new ProductionPK();
        pPK.setPeriodYear(periodYear);
        pPK.setPeriodMonth(periodMonth);
        pPK.setFiscalArrangementId(currentFiscalArrangement.getId());

        currentProduction.setProductionPK(pPK);

        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        currentProduction.setFiscalArrangement(currentFiscalArrangement);

        currentProduction.setCurrentUser(principal.getName());
    }

    public void periodMonthChanged() {
        if (isNewProduction()) {
            setProductionDetailEmbeddableKeys();
        }
    }

    public boolean isNewProduction() {
        return newProduction;
    }

    public void setNewProduction(boolean newProduction) {
        this.newProduction = newProduction;
    }

    public List<JvProductionDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<JvProductionDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public List<JvProduction> getProductions() {
        return productions;
    }

    public void setProductions(List<JvProduction> productions) {
        this.productions = productions;
    }

    public void loadProductionDetails(JvProduction jvProd) {
        productionDetails = getJvProductionDetailBean().findByContractPeriod(
                jvProd.getPeriodYear(),
                jvProd.getPeriodMonth(),
                jvProd.getFiscalArrangement());
    }

    public void loadProductionDetails() {
        if (periodYear != null) {
            if (periodMonth != null) {
                if (currentFiscalArrangement != null) {
                    if (currentContract == null) {
                        productionDetails = getProductionDetailBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
                    } else {
                        productionDetails = getProductionDetailBean().findByContractPeriod(periodYear, periodMonth, currentContract);
                    }

                } else {
                    productionDetails = getProductionDetailBean().findByYearAndMonth(periodYear, periodMonth);
                }

            } else if (currentFiscalArrangement != null) {
                if (currentContract == null) {
                    productionDetails = getProductionDetailBean().findAnnualProduction(periodYear, currentFiscalArrangement);
                } else {
                    productionDetails = getProductionDetailBean().findByContractPeriod(periodYear, currentContract);
                }
            }
        }

        if (productionDetails == null) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NoProductionData"));
            LOG.log(Level.INFO, ResourceBundle.getBundle("/Bundle").getString("NoProductionData")
            );
        }
    }

    public void loadFiscalMonthlyProduction() {
        if (periodYear != null && periodMonth != null && currentFiscalArrangement != null) {
            currentProduction = findProduction(periodYear, periodMonth, currentFiscalArrangement);

            if (currentProduction != null) {
                //TODO:fix ORM double linkage
                productionDetails = currentProduction.getProductionDetails();
                //Always  use super class JV ProductionDetailService interface to return all subtypes
//                productionDetails = getJvProductionDetailBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
            } else {
                productionDetails = null;
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NoProductionData"));
                LOG.log(Level.INFO, ResourceBundle.getBundle("/Bundle").getString("NoProductionData")
                );
            }
        }
    }

    private JvProduction findProduction(Integer periodYear, Integer periodMonth, FiscalArrangement currentFiscalArrangement) {
//        ProductionPK pPK = new ProductionPK();
//        pPK.setPeriodYear(periodYear);
//        pPK.setPeriodMonth(periodMonth);
//        pPK.setFiscalArrangementId(currentFiscalArrangement.getId());
//        return getProductionBean().find(pPK);
//Fixed N+1 Problem
        JvProduction jvProd = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
        return jvProd;
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
        LOG.log(Level.INFO,
                "Gross production changed to {0}",
                currentProductionDetail.getGrossProduction());
        getProductionDetailBean().grossProductionChanged(currentProductionDetail);
    }

    public void stockAdjustmentChanged() {
        LOG.log(Level.INFO,
                "Computing stock adjustment/variation",
                currentProductionDetail.getGrossProduction());
        getProductionDetailBean().grossProductionChanged(currentProductionDetail);
    }

    public void operatorDeclaredVolumeListener() {
        getProductionDetailBean().computeOperatorDeclaredEquity(currentProductionDetail);
    }

    public void liftingChanged() {
        LOG.log(Level.INFO, "Exportable volume changed");
        getProductionDetailBean().liftingChanged(currentProductionDetail);

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
        getProductionDetailBean().openingStockChanged(currentProductionDetail);
    }

    public void resetDefaults() {
        LOG.log(Level.INFO, "Resetting to default...");
        getProductionDetailBean().grossProductionChanged(currentProductionDetail);
    }

    private void reset() {
        currentProduction = null;
        productions = null;
        productionDetails = null;
        currentProductionDetail = null;
//        currentContract = null;
    }

    private void reset2() {
        currentProductionDetail = null;
        productionDetails = null;
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

    public void actualize(JvForecastDetail forecastDetail) throws Exception {
        LOG.log(Level.INFO, "Actualizing forecast {0}...", forecastDetail);
        reset();
        ContractPK cPK = forecastDetail.getContract().getContractPK();
        Contract contract = contractBean.find(cPK); //forecast.getContract();

//        setCurrentContract(forecast.getContract());
        setCurrentContract(contract);

        JvProductionDetail productionDetail = null;
        ProductionDetailPK pPK = new ProductionDetailPK(
                forecastDetail.getForecast().makeProductionPK(),
                forecastDetail.getContract().getContractPK()
        );
        productionDetail = (JvProductionDetail) getProductionDetailBean().find(pPK);
        JvProduction prod = null;

        if (productionDetail == null) {
            LOG.log(Level.INFO, "Actualizing: Creating new JV Production detail instance...");

//TODO:find better way to evaluate datatype
            if (forecastDetail instanceof ModifiedCarryForecastDetail) {
                productionDetail = new ModifiedCarryProductionDetail();
            } else if (forecastDetail instanceof CarryForecastDetail) {
                productionDetail = new CarryProductionDetail();
            } else if (forecastDetail instanceof JvForecastDetail) {
                productionDetail = new JvProductionDetail();
            } else {
                //something is wrong
                LOG.log(Level.WARNING, "Something is wrong! JvForecastDetailServices type not determined {0}...", forecastDetail);
                throw new Exception("JvForecastDetailServices type not determined");
            }

            LOG.log(Level.INFO, "Copying forecast details to new actual {0}...", forecastDetail);

            productionDetail.duplicate(forecastDetail);

            prod = getProductionBean().find(forecastDetail.getForecast().makeProductionPK());

            if (prod == null) {
                prod = new JvProduction();
                prod.initialize(forecastDetail.getForecast());
                prod.setCurrentUser(principal.getName());
            } else {
                loadProductionDetails(prod);
            }
            // prod.addProductionDetail(productionDetail);
            addProductionDetail(productionDetail);
            productionDetail.setProduction(prod);

            getProductionDetailBean().enrich(productionDetail);
            productionDetail.setCurrentUser(principal.getName());
        } else {
            prod = productionDetail.getProduction();
        }
        setCurrentProduction(prod);
        setCurrentProductionDetail(productionDetail);
        setPeriodYear(forecastDetail.getPeriodYear());
        setPeriodMonth(forecastDetail.getPeriodMonth());
        setCurrentFiscalArrangement(contract.getFiscalArrangement());
        setDirectActualizing(false);//actualizing through targeted forecast and not directly through the entry actualizing interface
    }

    public void destroy() {
//        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        getProductionDetailBean().delete(currentProduction.getProductionDetails());
        JvProduction production = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
        if (production != null) {
            getProductionBean().delete(currentProduction.getPeriodYear(), currentProduction.getPeriodMonth(), currentProduction.getFiscalArrangement());
        }
        if (!JsfUtil.isValidationFailed()) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
            reset();
        }
    }

    public void destroy(JvProduction prod) {
        setCurrentProduction(prod);
        destroy();
    }

    public void destroyProductionDetail() {
        persistProductionDetail(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            currentProductionDetail = null;
        }
    }

    public void destroyProductionDetail(JvProductionDetail prod) {
//        setCurrentProductionDetail(prod);
//        destroyProductionDetail();

//        if (currentProduction != null) {
//            removeProductionDetail(prod);
//            getProductionBean().edit(currentProduction);
//        }
        try {
            getProductionDetailBean().delete(prod.getPeriodYear(), prod.getPeriodMonth(), prod.getContract());
            reset();
            loadFiscalMonthlyProduction();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            LOG.log(Level.WARNING, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void removeProductionDetail(JvProductionDetail productionDetail) {
        productionDetails.remove(productionDetail);
        if (isEditMode()) {
            addToDeleteDetails(productionDetail);
        }
    }

    private void addToDeleteDetails(JvProductionDetail productionDetail) {
        if (deleteDetails == null) {
            deleteDetails = new ArrayList<>();
        }
        deleteDetails.add(productionDetail);

    }

    public String prepareAddProductionDetail() {
        currentContractChanged();
        return "actual-detail-create";
    }

    public String addProductionDetail() {
        addProductionDetail(currentProductionDetail);
        return "actual-create2";
    }

    public void addProductionDetail(JvProductionDetail jvDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();
        }
        productionDetails.add(jvDetail);
    }

    public String cancelProductionDetail() {
        currentProductionDetail = null;
        return "actual-create2";
    }

    public String prepareCreate() {
        reset();
        setDirectActualizing(true);
        currentContractChanged();
        return "actual-create";
    }

    public String prepareCreateProduction() {
        if (isProductionExists()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DataExists"));
            return null;
        }
        currentProduction = new JvProduction();
        setProductionEmbeddableKeys();
        productionDetails = new ArrayList<>();
        setDirectActualizing(true);
        return "actual-create2";
    }

    public void create() {
        persistProductionDetail(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            if (isDirectActualizing()) {
                reset();
                currentContractChanged();
                loadProductionDetails();
//            setNewProduction(false); 
            }

        }
    }

    public String createProduction() {
        currentProduction.setProductionDetails(productionDetails);
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionCreated"));
        if (!JsfUtil.isValidationFailed() && isDirectActualizing()) {
            reset();
            currentContract = null;
            loadProductionDetails();
//            setNewForecast(false);
            return "actualize2";
        }
        return null;
    }

    public void cancel() {
        reset();
        loadProductionDetails();
        disableEditMode();
        setNewProduction(false);
    }

    public void cancelProduction() {
        reset2();
        loadFiscalMonthlyProduction();

    }

    public String prepareUpdateProduction() {
        loadFiscalMonthlyProduction();
        if (currentProduction == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoProductionData"));
            return null;
        }
        setEditMode(true);
        return "actual-edit2";
    }

    public String updateProduction() {
        if (deleteDetails != null) {
            getProductionDetailBean().delete(deleteDetails);
            deleteDetails = null;
        }

        //currentProduction = getProductionBean().findByContractPeriod(periodYear, periodMonth, currentFiscalArrangement);
        if (currentProduction != null) {
            persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
        }

        if (!JsfUtil.isValidationFailed()) {
            reset();
//            currentContract = null;
            loadFiscalMonthlyProduction();
//            setNewForecast(false);
            disableEditMode();
            return "actualize2";
        }
        return null;
    }

    public void update() {
        persistProductionDetail(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));

        if (isEditMode()) {
            List<ProductionDetail> adjProductions = new ArrayList<>();
            ProductionDetail thisProduction = currentProductionDetail;
            ProductionDetail nextProduction;
            while ((nextProduction = (ProductionDetail) getProductionDetailBean().getNextMonthProduction(thisProduction)) != null) {
                try {
                    getProductionDetailBean().enrich(nextProduction);
                    getProductionDetailBean().edit(nextProduction);
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

    private void persistProductionDetail(JsfUtil.PersistAction persistAction, String successMessage) {
        if (currentProductionDetail != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getProductionDetailBean().edit(currentProductionDetail);
                } else {
                    getProductionDetailBean().remove(currentProductionDetail);
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
                org.apache.log4j.Logger.getLogger(this.getClass().getName()).log(org.apache.log4j.Level.ERROR, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public void validateStockLifting(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
        Double openingStock = currentProductionDetail.getOpeningStock();
        Double entitlement = currentProductionDetail.getOwnShareEntitlement();
        Double lifting = currentProductionDetail.getLifting();

        Double partnerOpeningStock = currentProductionDetail.getPartnerOpeningStock();
        Double partnerEntitlement = currentProductionDetail.getPartnerShareEntitlement();
        Double partnerLifting = currentProductionDetail.getPartnerLifting();

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

    public boolean isEnableControlButton() {
        return periodYear != null && periodMonth != null && currentFiscalArrangement != null;
    }

    public boolean isProductionExists() {
        return findProduction(periodYear, periodMonth, currentFiscalArrangement) != null;
    }
}
