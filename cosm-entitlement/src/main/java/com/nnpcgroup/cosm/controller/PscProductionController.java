/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.OilField;
import com.nnpcgroup.cosm.entity.forecast.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetail;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetailPK;

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
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 * @author 18359
 */
@Named(value = "pscProdController")
@SessionScoped
public class PscProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(PscProductionController.class.getName());

    @EJB
    private PscForecastDetailServices productionDetail;

    @Inject
    GeneralController genController;

    @Inject
    Principal principal;

    @Inject
    TaxOilController taxOilController;
    
    @Inject
    CostOilController costOilController;

    private List<PscForecastDetail> productionDetails;

    private PscForecastDetail currentProductionDetail;
    private Integer periodYear;
    private Integer periodMonth;
    private ProductionSharingContract currentPsc;
    private OilField currentOilField;

    public void prepareCreate() {
        if (periodYear != null && periodMonth != null && currentPsc != null) {
            prepareMonthlyCreate();
        } else if (periodYear != null && currentPsc != null && currentOilField != null) {
            prepareFieldCreate();
        }

    }

    private void prepareMonthlyCreate() {

        if (currentPsc == null) {
            return;
        }

        productionDetails = new ArrayList<>();
        List<OilField> oilFields = currentPsc.getOilFields();

        for (OilField of : oilFields) {
            ForecastPK fPK = new ForecastPK(periodYear, periodMonth, currentPsc.getId());
            PscForecastDetailPK pPk = new PscForecastDetailPK(fPK, of.getId());
            currentProductionDetail = new PscForecastDetail();
            currentProductionDetail.setForecastDetailPK(pPk);
            currentProductionDetail.setFiscalArrangement(currentPsc);
            currentProductionDetail.setPeriodYear(periodYear);
            currentProductionDetail.setPeriodMonth(periodMonth);
            currentProductionDetail.setOilField(of);
            productionDetails.add(currentProductionDetail);
        }

    }

    private void prepareFieldCreate() {
        productionDetails = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            ForecastPK fPK = new ForecastPK(periodYear, i, currentPsc.getId());
            PscForecastDetailPK pPk = new PscForecastDetailPK(fPK, currentOilField.getId());
            currentProductionDetail = new PscForecastDetail();
            currentProductionDetail.setForecastDetailPK(pPk);
            currentProductionDetail.setFiscalArrangement(currentPsc);
            currentProductionDetail.setPeriodYear(periodYear);
            currentProductionDetail.setPeriodMonth(i);
            currentProductionDetail.setOilField(currentOilField);
            productionDetails.add(currentProductionDetail);

        }
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            reset();
        }
    }

    public void destroy(PscForecastDetail prod) {
        setCurrentProductionDetail(prod);
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
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionUpdated"));
        if (!JsfUtil.isValidationFailed()) {

        }
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (productionDetails != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getProductionDetailBean().edit(productionDetails);
                } else {
                    getProductionDetailBean().remove(productionDetails);
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

    public void loadProductions() {
        if (periodYear != null && periodMonth != null && currentPsc != null) {
            productionDetails = getProductionDetailBean().find(periodYear, periodMonth, currentPsc);
        } else if (periodYear != null && currentPsc != null && currentOilField != null) {
            productionDetails = getProductionDetailBean().find(periodYear, currentOilField);
        }
    }

//    private void loadProductionsByMonth() {
//        if (periodYear != null && periodMonth != null && currentPsc != null) {
//            productionDetails = getProductionDetailBean().find(periodYear, periodMonth, currentPsc);
//        }
//    }
//
//    private void loadProductionsByField() {
//        if (periodYear != null && currentPsc != null && currentOilField != null) {
//            productionDetails = getProductionDetailBean().find(periodYear, currentOilField);
//        }
//    }
    public void grossProductionChangeListener(PscForecastDetail pscDetail) {
        double grossProd = pscDetail.getGrossProduction();
        int periodYear = pscDetail.getPeriodYear();
        int periodMonth = pscDetail.getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double dailyProd = grossProd / days;
        pscDetail.setDailyProduction(dailyProd);
    }

    private void reset() {
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

    private void setEmbeddableKeys() {
        PscForecastDetailPK fPK = new PscForecastDetailPK();
        fPK.setPeriodYear(periodYear);
        fPK.setPeriodMonth(periodMonth);
        fPK.setFiscalArrangementId(currentPsc.getId());
        fPK.setOilField(currentOilField.getId());

        currentProductionDetail.setForecastDetailPK(fPK);
        currentProductionDetail.setPeriodYear(periodYear);
        currentProductionDetail.setPeriodMonth(periodMonth);

        currentProductionDetail.setFiscalArrangement(currentPsc);
        currentProductionDetail.setOilField(currentOilField);

        currentProductionDetail.setCurrentUser(principal.getName());
    }

    public boolean isEnableControlButton() {
        return periodYear != null && currentPsc != null && currentPsc != null;
    }

    public PscForecastDetail getCurrentProductionDetail() {
        return currentProductionDetail;
    }

    public void setCurrentProductionDetail(PscForecastDetail currentProductionDetail) {
        this.currentProductionDetail = currentProductionDetail;
    }

    public ProductionSharingContract getCurrentPsc() {
        return currentPsc;
    }

    public void setCurrentPsc(ProductionSharingContract currentPsc) {
        this.currentPsc = currentPsc;
    }

    public SelectItem[] getPscOilFieldSelectOptions() {
        if (currentPsc != null) {
            return JsfUtil.getSelectItems(currentPsc.getOilFields(), false);
        }
        return null;
    }

    public OilField getCurrentOilField() {
        return currentOilField;
    }

    public void setCurrentOilField(OilField currentOilField) {
        this.currentOilField = currentOilField;
    }

    public List<PscForecastDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<PscForecastDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public PscForecastDetailServices getProductionDetailBean() {
        return productionDetail;
    }

    public void refreshTaxOilListener() {
        taxOilController.refreshTaxOil();
    }

    public void taxOilCalculationListener() {
        taxOilController.calculateTaxOilDetail(currentPsc, periodYear, periodMonth);
    }
    
    public void costOilCalculationListener() {
        costOilController.computeCostOilDetail(currentPsc, periodYear, periodMonth);
    }
}
