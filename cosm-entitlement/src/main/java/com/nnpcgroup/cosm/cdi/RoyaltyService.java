/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;
import com.nnpcgroup.cosm.report.schb1.CostOilAllocation;
import com.nnpcgroup.cosm.report.schb1.RoyaltyAllocation;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface RoyaltyService extends Serializable {

    double computeRoyalty(ProductionSharingContract psc, int year, int month);

    double computeRoyaltyCum(ProductionSharingContract psc, int year, int month);

    RoyaltyAllocation computeRoyaltyAllocation(ProductionSharingContract psc, int year, int month);

}
