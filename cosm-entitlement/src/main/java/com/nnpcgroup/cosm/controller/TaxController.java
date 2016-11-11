/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.tax.TaxServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostItem;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * @author 18359
 */
@Named(value = "taxController")
@SessionScoped
public class TaxController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger LOG = Logger.getLogger(TaxController.class.getName());

    @Inject
    private TaxServices taxBean;

    @EJB
    private ProductionCostServices prodCostBean;

    private Integer periodYear;
    private Integer periodMonth;
    private ProductionSharingContract currentPsc;
    private TaxOilDetail taxOilDetail;
    private List<CostItem> nonTanCostItems;
    private List<ProductionCost> prodOpexs;

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

    public TaxOilDetail getTaxOilDetail() {
        return taxOilDetail;
    }

    public void setTaxOilDetail(TaxOilDetail taxOilDetail) {
        this.taxOilDetail = taxOilDetail;
    }

    public void calculationTaxOilDetail(ProductionSharingContract psc, int year, int month) {
        initialize(psc, year, month);

        double royalty = taxBean.computeRoyalty(psc, year, month);
        double grossIncome = taxBean.computeGrossIncome(psc, year, month);
        double totalDeduction = taxBean.computeTotalDeduction(psc, year, month);
        double lossBfw = 0;
        double currentITA = taxBean.computeCurrentYearITA(psc, year, month);
        double currentCapitalAllowance = taxBean.computeCurrentYearCapitalAllowance(psc, year, month);
        double monthlyMinimumTax = taxBean.computeMonthlyMinimumTax(psc, year, month);
        double petroleumProfitTaxRate = psc.getPetroleumProfitTaxRate();

        taxOilDetail = new TaxOilDetail();

        taxOilDetail.setRoyalty(royalty);
        taxOilDetail.setGrossIncome(grossIncome);
        taxOilDetail.setTotalDeduction(totalDeduction);
        taxOilDetail.setLossBfw(lossBfw);
        taxOilDetail.setCurrentITA(currentITA);
        taxOilDetail.setCurrentCapitalAllowance(currentCapitalAllowance);
        taxOilDetail.setMonthlyMinimumTax(monthlyMinimumTax);
        taxOilDetail.setPetroleumProfitTaxRate(petroleumProfitTaxRate);
    }

    public List<ProductionCost> getProdOpexs() {
        //if (prodOpexs == null) {
        prodOpexs = prodCostBean.findOpex(currentPsc, periodYear, periodMonth);
        //  }
        return prodOpexs;
    }

    public void setProdOpexs(List<ProductionCost> prodOpexs) {
        this.prodOpexs = prodOpexs;
    }

    public List<CostItem> getNonTanCostItems() {
        // if (nonTanCostItems == null) {
        nonTanCostItems = new ArrayList<>();
        for (ProductionCost pc : getProdOpexs()) {
            nonTanCostItems.add(pc.getCostItem());
        }
        // }
        return nonTanCostItems;
    }

    public void setNonTanCostItems(List<CostItem> nonTanCostItems) {
        this.nonTanCostItems = nonTanCostItems;
    }

    public Double getOpexCost() {
        Double opexCost = prodOpexs.stream()
                .mapToDouble(p -> p.getAmount())
                .sum();
        return opexCost;

    }

    private void initialize(ProductionSharingContract psc, int year, int month) {
        this.periodYear = year;
        this.periodMonth = month;
        this.currentPsc = psc;
    }

}