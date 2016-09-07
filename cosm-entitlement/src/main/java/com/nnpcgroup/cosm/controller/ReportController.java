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
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import java.io.IOException;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author 18359
 */
@Named(value = "reportController")
@SessionScoped
public class ReportController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    //private static final Logger LOG = Logger.getLogger(JvForecastController.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();

    //@Inject
    @EJB
    private JvForecastServices forecastBean;

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

    /**
     * Creates a new instance of JvController
     */
    public ReportController() {

        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastServices getForecastBean() {
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

    public List<JvForecast> getProductions() {
        loadProductions();
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

    public JvForecast getContract(ForecastPK fPK) {
        return (JvForecast) getForecastBean().find(fPK);
    }

    public String runEntitlementReport() {
        return "entitlement";
    }

    public void downloadEntitlementReport() throws IOException, ServletException {
//        FacesContext.getCurrentInstance().getExternalContext().dispatch("/EntitlementReport");
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       // request.setAttribute("periodYear", periodYear);
//        request.setAttribute("periodMonth", periodMonth);

        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(String.format("EntitlementReport?periodYear=%s&periodMonth=%s", periodYear, periodMonth));
//        FacesContext.getCurrentInstance().getExternalContext().redirect("EntitlementReport");
    }
}
