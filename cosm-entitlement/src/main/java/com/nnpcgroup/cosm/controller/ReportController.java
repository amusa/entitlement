/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.forecast.jv.*;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetailPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlement;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlementPK;
import com.nnpcgroup.cosm.report.EntitlementDetail;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * @author 18359
 */
@Named(value = "reportController")
@SessionScoped
public class ReportController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(JvForecastController.class.getName());

    @EJB
    private JvForecastDetailServices forecastDetailBean;

    @Inject
    private JvForecastServices forecastBean;

    private List<JvForecast> productions;
    private List<JvForecastDetail> forecastDetails;
    private Integer periodYear;
    private Integer periodMonth;
    private List<EntitlementDetail> entitlementDetails;

    public ReportController() {

        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastDetailServices getForecastDetailBean() {
        return forecastDetailBean;
    }

    public JvForecastServices getForecastBean() {
        return forecastBean;
    }

    public List<JvForecast> getProductions() {
        return productions;
    }

    public void setProductions(List<JvForecast> productions) {
        this.productions = productions;
    }

    public List<JvForecastDetail> getForecastDetails() {
        return forecastDetails;
    }

    public void setForecastDetails(List<JvForecastDetail> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public List<EntitlementDetail> getEntitlementDetails() {
        return entitlementDetails;
    }

    public void setEntitlementDetails(List<EntitlementDetail> entitlementDetails) {
        this.entitlementDetails = entitlementDetails;
    }

    public void loadProductions() {
        if (periodYear != null && periodMonth != null) {
//            forecastDetails = getForecastDetailBean().findByYearAndMonth(periodYear, periodMonth);
            productions = getForecastBean().findByYearAndMonth(periodYear, periodMonth);
            processEntitlementDetails(productions);

        }
    }

    public void processEntitlementDetails(List<JvForecast> productions) {
        if (productions != null) {
            entitlementDetails = new ArrayList<>();
            for (JvForecast forecast : productions) {
                java.util.List<JvForecastDetail> forecastDetails = forecast.getForecastDetails();
                java.util.List<JvForecastEntitlement> entitlements = forecast.getEntitlements();

                for (JvForecastDetail fDetail : forecastDetails) {
                    JvForecastEntitlement entitlement = getEntitlement(fDetail, entitlements);
                    EntitlementDetail eDetail = new EntitlementDetail(forecast.getFiscalArrangement().getOperator().getName(),
                            fDetail.getContract().getTitle(),
                            entitlement.getLifting(),
                            entitlement.getPartnerLifting(),
                            forecast.getRemark());
                    entitlementDetails.add(eDetail);
                }

            }

        }
    }

    public JvForecastEntitlement getEntitlement(JvForecastDetail detail, java.util.List<JvForecastEntitlement> entitlements) {
        if (detail == null) {
            return null;
        }

        for (JvForecastEntitlement ent : entitlements) {
            if (isEqualKeys(detail, ent)) {
                return ent;
            }
        }
        return null;
    }

    private boolean isEqualKeys(JvForecastDetail detail, JvForecastEntitlement ent) {
        JvForecastDetailPK detailPK = detail.getForecastDetailPK();
        JvForecastEntitlementPK entitlementPK = ent.getEntitlementPK();

        if (!detailPK.getForecastPK().equals(entitlementPK.getForecastPK())) {
            return false;
        }

        return detailPK.getContractPK().equals(entitlementPK.getContractPK());
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

    public String runEntitlementReport() {
        return "entitlement";
    }

    public void downloadEntitlementReport() throws IOException, ServletException {
//        FacesContext.getCurrentInstance().getExternalContext().dispatch("/EntitlementReport");
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(String.format("EntitlementReport?periodYear=%s&periodMonth=%s", periodYear, periodMonth));
    }
}
