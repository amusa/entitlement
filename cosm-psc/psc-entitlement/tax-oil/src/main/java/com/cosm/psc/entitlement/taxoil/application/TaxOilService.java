/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.application;

import java.io.Serializable;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.event.TaxOilReady;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilAllocation;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilDetail;

/**
 * @author 18359
 */
public interface TaxOilService extends Serializable {

    double computeTaxOil(ProductionSharingContractId pscId, int year, int month);

    double computeEducationTax(ProductionSharingContractId pscId, int year, int month);

    double computeMinimumTax(ProductionSharingContractId pscId, int year, int month);

    double computeMonthlyMinimumTax(ProductionSharingContractId pscId, int year, int month);

    TaxOilDetail computeTaxOilDetail(ProductionSharingContractId pscId, int year, int month);

    TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContractId pscId, int year, int month);

    TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContractId pscId, int year, int month);

    TaxOilDetail buildTaxOil(ProductionSharingContractId pscId, int year, int month);

    TaxOilAllocation computeTaxOilAllocation(ProductionSharingContractId pscId, Integer year, Integer month);

    Allocation computePreviousAllocation(ProductionSharingContractId pscId, int year, int month);

    double computeMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

    double computeCumMonthlyCharge(ProductionSharingContractId pscId, int year, int month);

    double computeReceived(ProductionSharingContractId pscId, int year, int month);
    
    void when(TaxOilReady event);
}
