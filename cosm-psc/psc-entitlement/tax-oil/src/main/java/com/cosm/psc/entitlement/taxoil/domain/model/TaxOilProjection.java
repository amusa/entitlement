/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.domain.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author Ayemi
 */
public class TaxOilProjection implements Serializable {

	private static final long serialVersionUID = 1L;

	private TaxOilProjectionId taxOilProjectionId;
	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
	private double taxOil;
	private double taxOilToDate;
	private Allocation allocation;
	private double royalty;
	private double grossIncome;
	private double lossBfw;
	private double currentCapitalAllowance;
	private double educationTax;
	private double opex;
	private double currentYearCapex;
	private double corporationProceed;
	private double totalDeduction;
	private double adjustedProfit;
	private double assessableProfit;
	private double adjustedAssessableProfit;
	private double currentYearITA;
	private double adjustedProfitLessITA;
	private double totalAnnualAllowance;
	private double section18DeductionLower;
	private double chargeableProfitToDate;
	private double chargeableTaxToDate;
	private double payableTaxToDate;
	private double unrecoupedAnnualAllowance;
	private double monthlyMinimumTax;
	private double minimumTax;

	
	

	public TaxOilProjection(TaxOilProjectionId id, TaxOilCalculator taxCalculator) {
		this.taxOilProjectionId = id;
		this.fiscalPeriod = taxCalculator.getFiscalPeriod();
		this.pscId = taxCalculator.getPscId();
		this.taxOil = taxCalculator.getTaxOil();
		this.taxOilToDate = taxCalculator.getTaxOilToDate();
		this.allocation = taxCalculator.getTaxOilAllocation();
		this.royalty = taxCalculator.getRoyalty();
		this.grossIncome = taxCalculator.getGrossIncome();
		this.lossBfw = taxCalculator.getLossBfw();
		this.currentCapitalAllowance = taxCalculator.getCurrentCapitalAllowance();
		this.educationTax = taxCalculator.getEducationTax();
		this.opex = taxCalculator.getOpex();
		this.currentYearCapex = taxCalculator.getCurrentYearCapex();
		this.corporationProceed = taxCalculator.getCorporationProceed();
		this.totalDeduction = taxCalculator.getTotalDeduction();
		this.adjustedProfit = taxCalculator.getAdjustedProfit();
		this.assessableProfit = taxCalculator.getAssessableProfit();
		this.adjustedAssessableProfit = taxCalculator.getAdjustedAssessableProfit();
		this.currentYearITA = taxCalculator.getCurrentYearITA();
		this.adjustedProfitLessITA = taxCalculator.getAdjustedProfitLessITA();
		this.totalAnnualAllowance = taxCalculator.getTotalAnnualAllowance();
		this.section18DeductionLower = taxCalculator.getSection18DeductionLower();
		this.chargeableProfitToDate = taxCalculator.getChargeableProfitToDate();
		this.chargeableTaxToDate = taxCalculator.getChargeableTaxToDate();
		this.payableTaxToDate = taxCalculator.getPayableTaxToDate();
		this.unrecoupedAnnualAllowance = taxCalculator.getUnrecoupedAnnualAllowance();
		this.monthlyMinimumTax = taxCalculator.getMonthlyMinimumTax();
		this.minimumTax = taxCalculator.getMinimumTax();
	}
	
	public TaxOilProjectionId getTaxOilProjectionId() {
		return taxOilProjectionId;
	}

	public void setTaxOilProjectionId(TaxOilProjectionId taxOilProjectionId) {
		this.taxOilProjectionId = taxOilProjectionId;
	}

	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public void setPscId(ProductionSharingContractId pscId) {
		this.pscId = pscId;
	}

	public double getTaxOil() {
		return taxOil;
	}

	public void setTaxOil(double taxOil) {
		this.taxOil = taxOil;
	}

	public double getTaxOilToDate() {
		return taxOilToDate;
	}

	public void setTaxOilToDate(double taxOilToDate) {
		this.taxOilToDate = taxOilToDate;
	}

	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
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

	public double getLossBfw() {
		return lossBfw;
	}

	public void setLossBfw(double lossBfw) {
		this.lossBfw = lossBfw;
	}

	public double getCurrentCapitalAllowance() {
		return currentCapitalAllowance;
	}

	public void setCurrentCapitalAllowance(double currentCapitalAllowance) {
		this.currentCapitalAllowance = currentCapitalAllowance;
	}

	public double getEducationTax() {
		return educationTax;
	}

	public void setEducationTax(double educationTax) {
		this.educationTax = educationTax;
	}

	public double getOpex() {
		return opex;
	}

	public void setOpex(double opex) {
		this.opex = opex;
	}

	public double getCurrentYearCapex() {
		return currentYearCapex;
	}

	public void setCurrentYearCapex(double currentYearCapex) {
		this.currentYearCapex = currentYearCapex;
	}

	public double getCorporationProceed() {
		return corporationProceed;
	}

	public void setCorporationProceed(double corporationProceed) {
		this.corporationProceed = corporationProceed;
	}

	public double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public double getAdjustedProfit() {
		return adjustedProfit;
	}

	public void setAdjustedProfit(double adjustedProfit) {
		this.adjustedProfit = adjustedProfit;
	}

	public double getAssessableProfit() {
		return assessableProfit;
	}

	public void setAssessableProfit(double assessableProfit) {
		this.assessableProfit = assessableProfit;
	}

	public double getAdjustedAssessableProfit() {
		return adjustedAssessableProfit;
	}

	public void setAdjustedAssessableProfit(double adjustedAssessableProfit) {
		this.adjustedAssessableProfit = adjustedAssessableProfit;
	}

	public double getCurrentYearITA() {
		return currentYearITA;
	}

	public void setCurrentYearITA(double currentYearITA) {
		this.currentYearITA = currentYearITA;
	}

	public double getAdjustedProfitLessITA() {
		return adjustedProfitLessITA;
	}

	public void setAdjustedProfitLessITA(double adjustedProfitLessITA) {
		this.adjustedProfitLessITA = adjustedProfitLessITA;
	}

	public double getTotalAnnualAllowance() {
		return totalAnnualAllowance;
	}

	public void setTotalAnnualAllowance(double totalAnnualAllowance) {
		this.totalAnnualAllowance = totalAnnualAllowance;
	}

	public double getSection18DeductionLower() {
		return section18DeductionLower;
	}

	public void setSection18DeductionLower(double section18DeductionLower) {
		this.section18DeductionLower = section18DeductionLower;
	}

	public double getChargeableProfitToDate() {
		return chargeableProfitToDate;
	}

	public void setChargeableProfitToDate(double chargeableProfitToDate) {
		this.chargeableProfitToDate = chargeableProfitToDate;
	}

	public double getChargeableTaxToDate() {
		return chargeableTaxToDate;
	}

	public void setChargeableTaxToDate(double chargeableTaxToDate) {
		this.chargeableTaxToDate = chargeableTaxToDate;
	}

	public double getPayableTaxToDate() {
		return payableTaxToDate;
	}

	public void setPayableTaxToDate(double payableTaxToDate) {
		this.payableTaxToDate = payableTaxToDate;
	}

	public double getUnrecoupedAnnualAllowance() {
		return unrecoupedAnnualAllowance;
	}

	public void setUnrecoupedAnnualAllowance(double unrecoupedAnnualAllowance) {
		this.unrecoupedAnnualAllowance = unrecoupedAnnualAllowance;
	}

	public double getMonthlyMinimumTax() {
		return monthlyMinimumTax;
	}

	public void setMonthlyMinimumTax(double monthlyMinimumTax) {
		this.monthlyMinimumTax = monthlyMinimumTax;
	}

	public double getMinimumTax() {
		return minimumTax;
	}

	public void setMinimumTax(double minimumTax) {
		this.minimumTax = minimumTax;
	}

}
