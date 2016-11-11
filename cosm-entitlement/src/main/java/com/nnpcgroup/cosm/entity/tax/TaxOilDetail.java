/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.tax;

import java.io.Serializable;

/**
 *
 * @author Ayemi
 */
public class TaxOilDetail implements Serializable {

    private double royalty;
    private double grossIncome;
    private double totalDeduction;
    private double lossBfw;
    private double currentITA;
    private double currentCapitalAllowance;
    private double monthlyMinimumTax;
    private double petroleumProfitTaxRate;

    public double getAdjustedProfit() {
        return Math.max(0, grossIncome - totalDeduction);
    }

    public double getAssessableProfit() {
        return getAdjustedProfit() - lossBfw;
    }

    public double getEducationTax() {
        return Math.max(0, (2 / 102) * getAssessableProfit());
    }

    public double getAdjustedAssessableProfit() {
        return getAssessableProfit() - getEducationTax();
    }

    public double getAdjustedProfitLessITA() {
        return (0.85 * getAdjustedAssessableProfit() - (1.7 * currentITA));
    }

    public double getTotalAnnualAllowance() {
        return currentITA + currentCapitalAllowance;//TODO: +priorYearAnnualAllowance
    }

    public double getSection18DeductionLower() {
        return Math.max(0, Math.min(getAdjustedProfitLessITA(), getTotalAnnualAllowance()));
    }

    public double getChargeableProfitToDate() {
        return Math.max(0, getAdjustedAssessableProfit() - getSection18DeductionLower());
    }

    public double getChargeableTaxToDate() {
        return petroleumProfitTaxRate * getChargeableProfitToDate();
    }

    public double getPayableTaxToDate() {
        return Math.max(monthlyMinimumTax, (monthlyMinimumTax < 0 && getChargeableTaxToDate() < 0) ? 0 : getChargeableTaxToDate());
    }

    public double getUnrecoupedAnnualAllowance() {
        return Math.min(0, getAdjustedAssessableProfit() - getTotalAnnualAllowance());
    }

    public double getPriorYearAnnualAllowance() {
        return -1 * getUnrecoupedAnnualAllowance();
    }

    public double getTaxOil() {
        return Math.max(0, getPayableTaxToDate());
    }

    public double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(double royalty) {
        this.royalty = royalty;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public double getLossBfw() {
        return lossBfw;
    }

    public void setLossBfw(double lossBfw) {
        this.lossBfw = lossBfw;
    }

    public double getCurrentITA() {
        return currentITA;
    }

    public void setCurrentITA(double currentITA) {
        this.currentITA = currentITA;
    }

    public double getCurrentCapitalAllowance() {
        return currentCapitalAllowance;
    }

    public void setCurrentCapitalAllowance(double currentCapitalAllowance) {
        this.currentCapitalAllowance = currentCapitalAllowance;
    }

    public double getMonthlyMinimumTax() {
        return monthlyMinimumTax;
    }

    public void setMonthlyMinimumTax(double monthlyMinimumTax) {
        this.monthlyMinimumTax = monthlyMinimumTax;
    }

    public double getPetroleumProfitTaxRate() {
        return petroleumProfitTaxRate;
    }

    public void setPetroleumProfitTaxRate(double petroleumProfitTaxRate) {
        this.petroleumProfitTaxRate = petroleumProfitTaxRate;
    }

}
