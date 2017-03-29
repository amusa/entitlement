/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.cdi.CostOilService;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;

import javax.inject.Named;
import java.io.Serializable;

import java.util.logging.Logger;
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
    private CostOilService costOilService;

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
        costOilDetail = costOilService.buildCostOilDetail(psc, year, month);
    }

    private void initialize(ProductionSharingContract psc, int year, int month) {
        this.periodYear = year;
        this.periodMonth = month;
        this.currentPsc = psc;
    }
}
