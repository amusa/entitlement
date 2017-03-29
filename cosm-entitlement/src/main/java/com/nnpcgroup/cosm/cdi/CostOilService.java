/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;
import com.nnpcgroup.cosm.report.schb1.CostOilAllocation;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface CostOilService extends Serializable {

    double computeCostOil(ProductionSharingContract psc, int year, int month);

    CostOilDetail buildCostOilDetail(ProductionSharingContract psc, int year, int month);

    CostOilAllocation computeCostOilAllocation(ProductionSharingContract psc, int year, int month);
}
