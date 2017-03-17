/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.FiscalPeriodService;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.tax.TaxServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * @author 18359
 */
@Named(value = "costOilController")
@SessionScoped
public class CostOilController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(CostOilController.class.getName());

    @Inject
    private TaxServices taxBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @EJB
    private ProductionCostServices prodCostBean;

    private Integer periodYear;
    private Integer periodMonth;
    private ProductionSharingContract currentPsc;
    private CostOilDetail costOilDetail;

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

    public ProductionSharingContract getCurrentPsc() {
        return currentPsc;
    }

    public void setCurrentPsc(ProductionSharingContract currentPsc) {
        this.currentPsc = currentPsc;
    }

    public CostOilDetail getCostOilDetail() {
        return costOilDetail;
    }

    public void setCostOilDetail(CostOilDetail costOilDetail) {
        this.costOilDetail = costOilDetail;
    }

    public void computeCostOilDetail(ProductionSharingContract psc, Integer year, Integer month) {
        initialize(psc, year, month);
        Double armotizedCapex = prodCostBean.getCapitalAllowanceRecovery(psc, year, month);
        Double opex = prodCostBean.getOpex(psc, year, month);
        Double eduTax = taxBean.computeEducationTax(psc, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        Double costOilBfw = computeCostOilBfw(psc, prevFp.getYear(), prevFp.getMonth());

        costOilDetail = new CostOilDetail();
        costOilDetail.setArmotizedCapex(armotizedCapex);
        costOilDetail.setOpex(opex);
        costOilDetail.setEducationTax(eduTax);
        costOilDetail.setCostOilBfw(costOilBfw);

    }

    public Double computeCostOilCum(ProductionSharingContract psc, Integer year, Integer month) {
        Double costOil, costOilBfw;
        costOil = computeCostOil(psc, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        costOilBfw = computeCostOilBfw(psc, prevFp.getYear(), prevFp.getMonth());

        return costOil + costOilBfw;
    }

    public Double computeCostOil(ProductionSharingContract psc, Integer year, Integer month) {
        Double armotizedCapex, opex, eduTax;

        armotizedCapex = prodCostBean.getCapitalAllowanceRecovery(psc, year, month);
        opex = prodCostBean.getOpex(psc, year, month);
        eduTax = taxBean.computeEducationTax(psc, year, month);

        return armotizedCapex + opex + eduTax;

    }

    public Double computeCostOilBfw(ProductionSharingContract psc, Integer year, Integer month) {
        Double costOilCum, monthlyCurrentCharge;
        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return 0.0;
        }
        costOilCum = computeCostOilCum(psc, year, month);
        monthlyCurrentCharge = computeMontlyCurrentCharge(psc, year, month, costOilCum);

        return Math.max(0, costOilCum - monthlyCurrentCharge);
    }

    public Double computeMontlyCurrentCharge(ProductionSharingContract psc, Integer year, Integer month, Double costOilCum) {
        Double proceed = taxBean.computeGrossIncome(psc, year, month);
        Double costUplift = psc.getCostUplift();

        return Math.max(0, Math.min(proceed * costUplift, costOilCum));
    }

    private void initialize(ProductionSharingContract psc, int year, int month) {
        this.periodYear = year;
        this.periodMonth = month;
        this.currentPsc = psc;
    }

    

}
