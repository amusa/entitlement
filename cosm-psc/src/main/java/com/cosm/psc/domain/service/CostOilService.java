/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.service;

import com.cosm.psc.domain.model.Allocation;
import com.cosm.psc.domain.model.CostOilAllocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.cost.CostOilDetail;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface CostOilService extends Serializable {

    double computeCostOil(ProductionSharingContract psc, int year, int month);

    CostOilDetail buildCostOilDetail(ProductionSharingContract psc, int year, int month);

    CostOilAllocation computeCostOilAllocation(ProductionSharingContract psc, int year, int month);

    Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month);

    double computeMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeReceived(ProductionSharingContract psc, int year, int month);
}
