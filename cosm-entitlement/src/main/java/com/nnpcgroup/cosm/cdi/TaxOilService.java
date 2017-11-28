/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.TaxOilAllocation;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface TaxOilService extends Serializable {

    double computeTaxOil(ProductionSharingContract psc, int year, int month);

    double computeEducationTax(ProductionSharingContract psc, int year, int month);

    double computeMinimumTax(ProductionSharingContract psc, int year, int month);

    double computeMonthlyMinimumTax(ProductionSharingContract psc, int year, int month);

    TaxOilDetail computeTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail buildTaxOil(ProductionSharingContract psc, int year, int month);

    TaxOilAllocation computeTaxOilAllocation(ProductionSharingContract psc, Integer year, Integer month);

    Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month);

    double computeMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeReceived(ProductionSharingContract psc, int year, int month);
}
