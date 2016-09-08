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
    //private static final Logger LOG = LogManager.getRootLogger();

    //@Inject
    @EJB
    private JvForecastServices forecastBean;

//    @Inject
//    private ContractServices contractBean;

  

//    private JvForecast currentProduction;
    private List<JvForecast> productions;
    private Integer periodYear;
    private Integer periodMonth;
//    private FiscalArrangement currentFiscalArrangement;
//    private Contract currentContract;

    /**
     * Creates a new instance of JvController
     */
    public ReportController() {

        LOG.info("ProductionController::constructor activated...");
    }

    public JvForecastServices getForecastBean() {
        return forecastBean;
    }

//    public JvForecast getCurrentProduction() {
//        return currentProduction;
//    }
//
//    public void setCurrentProduction(JvForecast currentProduction) {
//        this.currentProduction = currentProduction;        
//    }

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

//    public FiscalArrangement getCurrentFiscalArrangement() {
//        return currentFiscalArrangement;
//    }
//
//    public void setCurrentFiscalArrangement(FiscalArrangement currentFiscalArrangement) {
//        this.currentFiscalArrangement = currentFiscalArrangement;
//    }

//    public Contract getCurrentContract() {
//        return currentContract;
//    }
//
//    public void setCurrentContract(Contract currentContract) {
//        this.currentContract = contractBean.find(currentContract);
//    }

//    public SelectItem[] getContractSelectOne() {
//        List<Contract> contracts = null;
//
//        if (currentFiscalArrangement != null) {
//            contracts = contractBean.findFiscalArrangementContracts(currentFiscalArrangement);
//        }
//
//        return JsfUtil.getSelectItems(contracts, true);
//
//    }
//
//    public JvForecast getContract(ForecastPK fPK) {
//        return (JvForecast) getForecastBean().find(fPK);
//    }

    public String runEntitlementReport() {
        return "entitlement";
    }

    public void downloadEntitlementReport() throws IOException, ServletException {
//        FacesContext.getCurrentInstance().getExternalContext().dispatch("/EntitlementReport");
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(String.format("EntitlementReport?periodYear=%s&periodMonth=%s", periodYear, periodMonth));
    }
}
