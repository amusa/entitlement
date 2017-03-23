/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;

/**
 *
 * @author 18359
 */
public interface TaxServices {

    public double computeTaxOil(ProductionSharingContract psc, int year, int month);

    public double computeGrossIncome(ProductionSharingContract psc, int year, int month);

    public double computeTotalDeduction(ProductionSharingContract psc, int year, int month);

    public double computeRoyalty(ProductionSharingContract psc, int year, int month);
    
    public double computeRoyaltyCum(ProductionSharingContract psc, int year, int month);

    public double computeAdjustedProfit(ProductionSharingContract psc, int year, int month);

    public double computeAssessableProfit(ProductionSharingContract psc, int year, int month);

    public double computeEducationTax(ProductionSharingContract psc, int year, int month);

    public double computeAdjustedAssessableProfit(ProductionSharingContract psc, int year, int month);

    public double computeCurrentYearITA(ProductionSharingContract psc, int year, int month);

    public double computeUnrecoupedAnnualAllowance(ProductionSharingContract psc, int year, int month);

    public double computeTotalAnnualAllowance(ProductionSharingContract psc, int year, int month);

    public double computePriorYearAnnualAllowance(ProductionSharingContract psc, int year, int month);

    public double computeMinimumTax(ProductionSharingContract psc, int year, int month);

    public double computeMonthlyMinimumTax(ProductionSharingContract psc, int year, int month);

    public double computeAdjustedProfitLessITA(ProductionSharingContract psc, int year, int month);

    public double computeSection18DeductionLower(ProductionSharingContract psc, int year, int month);

    public double computeChargeableTaxToDate(ProductionSharingContract psc, int year, int month);

    public double computeChargeableProfitToDate(ProductionSharingContract psc, int year, int month);

    public double computePayableTaxToDate(ProductionSharingContract psc, int year, int month);

    public double computeCurrentYearCapitalAllowance(ProductionSharingContract psc, int year, int month);

    public TaxOilDetail computeTaxOilDetail(ProductionSharingContract psc, int year, int month);

    public TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContract psc, int year, int month);

    public TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContract psc, int year, int month);

    public TaxOilDetail buildTaxOil(ProductionSharingContract psc, int year, int month);
}
