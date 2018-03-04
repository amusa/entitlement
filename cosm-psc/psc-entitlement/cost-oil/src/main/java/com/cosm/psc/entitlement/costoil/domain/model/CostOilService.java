/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.costoil.domain.model;



import java.io.Serializable;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author 18359
 */
public interface CostOilService extends Serializable {

    double computeCostOil(ProductionSharingContractId pscId, int year, int month);

    CostOilDetail buildCostOilDetail(ProductionSharingContractId pscId, int year, int month);

    CostOilAllocation computeCostOilAllocation(ProductionSharingContractId pscId, int year, int month);

    Allocation computePreviousAllocation(ProductionSharingContractId pscId, int year, int month);

    double computeMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

    double computeCumMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

    double computeReceived(ProductionSharingContractId pscId, int year, int month);
}
