/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.model;

import java.io.Serializable;

/**
 * @author Ayemi
 */
public class TaxOilDetail implements Serializable {

    private double royalty;
    private double grossIncome;
    private double lossBfw;
    private double currentITA;
    private double currentCapitalAllowance;
    private double monthlyMinimumTax;
    private double petroleumProfitTaxRate;
    private double educationTax;
    private double priorYearAnnualAllowance;
    private double opex;


    public double getAdjustedProfit() {
        return Math.max(0, grossIncome - getTotalDeduction());
    }

    public double getAssessableProfit() {
        return getAdjustedProfit() - lossBfw;
    }

    public double getEducationTax() {
        return educationTax;
    }

    public void setEducationTax(double eduTax) {
        this.educationTax = eduTax;
    }

    public double getAdjustedAssessableProfit() {
        return getAssessableProfit() - getEducationTax();
    }

    public double getAdjustedProfitLessITA() {
        return (0.85 * getAdjustedAssessableProfit() - (1.7 * currentITA));
    }

    public double getTotalAnnualAllowance() {
        return currentITA + currentCapitalAllowance + priorYearAnnualAllowance;
    }

    public double getSection18DeductionLower() {
        return Math.max(0, Math.min(getAdjustedProfitLessITA(), getTotalAnnualAllowance()));
    }

    public double getChargeableProfitToDate() {
        return Math.max(0, getAdjustedAssessableProfit() - getSection18DeductionLower());
    }

    public double getChargeableTaxToDate() {
        return getChargeableProfitToDate() * (petroleumProfitTaxRate / 100);
    }

    public double getPayableTaxToDate() {
        return Math.max(monthlyMinimumTax, (monthlyMinimumTax < 0 && getChargeableTaxToDate() < 0) ? 0 : getChargeableTaxToDate());
    }

    public double getUnrecoupedAnnualAllowance() {
        return Math.min(0, getAdjustedAssessableProfit() - getTotalAnnualAllowance());
    }

    public double getPriorYearAnnualAllowance() {
        return priorYearAnnualAllowance;
    }

    public void setPriorYearAnnualAllowance(double priorYrAnnualAllw) {
        this.priorYearAnnualAllowance = priorYrAnnualAllw;
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
        return opex + royalty;
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

    public double getMinimumTax() {
        return getAdjustedAssessableProfit() * 0.15;
    }

    public double getOpex() {
        return opex;
    }

    public void setOpex(double opex) {
        this.opex = opex;
    }
}
