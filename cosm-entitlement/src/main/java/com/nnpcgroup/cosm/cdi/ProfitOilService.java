/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import com.nnpcgroup.cosm.report.schb1.Allocation;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface ProfitOilService extends Serializable {

    double computeRFactor(ProductionSharingContract psc, int year, int month);

    Allocation computeCorporationProfitOilAllocation(ProductionSharingContract psc, int year, int month);

    Allocation computeContractorProfitOilAllocation(ProductionSharingContract psc, int year, int month);

    double computeProfitOil(ProductionSharingContract psc, int year, int month);

    double computeContractorMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeCumContractorMonthlyCharge(ProductionSharingContract psc, int year, int month);

}
