/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.forecast.jv.*;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import java.io.IOException;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
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
    private JvForecastDetailServices forecastBean;

    private List<JvForecast> productions;
    private Integer periodYear;
    private Integer periodMonth;

    public ReportController() {

        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastDetailServices getForecastBean() {
        return forecastBean;
    }

    public List<JvForecast> getProductions() {
        return productions;
    }

    public void setProductions(List<JvForecast> productions) {
        this.productions = productions;
    }

    public void loadProductions() {
        if (periodYear != null && periodMonth != null) {
            productions = getForecastBean().findByYearAndMonth(periodYear, periodMonth);
        }
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
