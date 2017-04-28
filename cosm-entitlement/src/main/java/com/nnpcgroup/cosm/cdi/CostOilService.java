/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.CostOilAllocation;

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
