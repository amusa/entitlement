/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.report.schb1.TaxOilAllocation;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface TaxOilService extends Serializable {

    double computeTaxOil(ProductionSharingContract psc, int year, int month);

    double computeGrossIncome(ProductionSharingContract psc, int year, int month);

    double computeTotalDeduction(ProductionSharingContract psc, int year, int month);

    double computeAdjustedProfit(ProductionSharingContract psc, int year, int month);

    double computeAssessableProfit(ProductionSharingContract psc, int year, int month);

    double computeEducationTax(ProductionSharingContract psc, int year, int month);

    double computeAdjustedAssessableProfit(ProductionSharingContract psc, int year, int month);

    double computeCurrentYearITA(ProductionSharingContract psc, int year, int month);

    double computeUnrecoupedAnnualAllowance(ProductionSharingContract psc, int year, int month);

    double computeTotalAnnualAllowance(ProductionSharingContract psc, int year, int month);

    double computePriorYearAnnualAllowance(ProductionSharingContract psc, int year, int month);

    double computeMinimumTax(ProductionSharingContract psc, int year, int month);

    double computeMonthlyMinimumTax(ProductionSharingContract psc, int year, int month);

    double computeAdjustedProfitLessITA(ProductionSharingContract psc, int year, int month);

    double computeSection18DeductionLower(ProductionSharingContract psc, int year, int month);

    double computeChargeableTaxToDate(ProductionSharingContract psc, int year, int month);

    double computeChargeableProfitToDate(ProductionSharingContract psc, int year, int month);

    double computePayableTaxToDate(ProductionSharingContract psc, int year, int month);

    double computeCurrentYearCapitalAllowance(ProductionSharingContract psc, int year, int month);

    TaxOilDetail computeTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContract psc, int year, int month);

    TaxOilDetail buildTaxOil(ProductionSharingContract psc, int year, int month);

    TaxOilAllocation computeTaxOilAllocation(ProductionSharingContract psc, Integer year, Integer month);
}
