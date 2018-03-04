/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.profitoil.domain.model;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.ProductionSharingContractId;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface ProfitOilService extends Serializable {

    double computeRFactor(ProductionSharingContractId pscId, int year, int month);

    Allocation computeCorporationProfitOilAllocation(ProductionSharingContractId pscId, int year, int month);

    Allocation computeContractorProfitOilAllocation(ProductionSharingContractId pscId, int year, int month);

    double computeProfitOil(ProductionSharingContractId pscId, int year, int month);

    double computeContractorMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

    double computeCumContractorMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

}
