/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.application.internal;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.util.CacheKey;
import com.cosm.common.util.CacheUtil;
import com.cosm.psc.entitlement.taxoil.application.TaxOilService;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilAllocation;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilDetail;

import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;

@Dependent
public class DefaultTaxOilService implements TaxOilService {

	@EJB
	private ProductionCostServices prodCostBean;

	@EJB
	private PscLiftingServices liftingBean;

	@Inject
	private RoyaltyService royaltyService;

	@Inject
	private FiscalPeriodService fiscalService;

	private CacheUtil<CacheKey, Double> incomeCache = new CacheUtil<>("income", 100);
	private CacheUtil<CacheKey, Double> uAACache = new CacheUtil<>("UAA", 100);
	private CacheUtil<CacheKey, Double> amortizationCache = new CacheUtil<>("amortization", 100);
	private CacheUtil<CacheKey, TaxOilDetail> taxOilCache = new CacheUtil<>("taxOil", 100);
	private CacheUtil<CacheKey, TaxOilAllocation> taxOilAllocationCache = new CacheUtil<>("taxOilAllocation", 100);

	@Override
	public double computeTaxOil(ProductionSharingContractId pscId, int year, int month) {
		return computeTaxOilDetail(pscId, year, month).getTaxOil();
	}

	private double computeGrossIncome(ProductionSharingContractId pscId, int year, int month) {
		CacheKey cacheKey = new CacheKey(pscId, year, month);
		Double grossIncome;

		if (incomeCache.cache().containsKey(cacheKey)) {
			return incomeCache.cache().get(cacheKey);
		}

		grossIncome = liftingBean.getGrossIncome(pscId, year, month);
		incomeCache.cache().put(cacheKey, grossIncome);

		return grossIncome;
	}

	private double computeTotalDeduction(ProductionSharingContractId pscId, int year, int month) {
		double royalty, opex;
		opex = computeCurrentYearOpex(pscId, year, month);
		royalty = royaltyService.computeRoyaltyCum(pscId, year, month);

		return royalty + opex;
	}

	private double computeAdjustedProfit(ProductionSharingContractId pscId, int year, int month) {
		double totalDeduction, grossIncome, adjustedProfit;
		totalDeduction = computeTotalDeduction(pscId, year, month);
		grossIncome = computeGrossIncome(pscId, year, month);
		adjustedProfit = Math.max(0, grossIncome - totalDeduction);

		return adjustedProfit;
	}

	private double computeAssessableProfit(ProductionSharingContractId pscId, int year, int month) {
		double adjProfit, lossBf;

		adjProfit = computeAdjustedProfit(pscId, year, month);
		lossBf = computeLossBf();

		return adjProfit + lossBf;

	}

	@Override
	public double computeEducationTax(ProductionSharingContractId pscId, int year, int month) {
		// if education tax exists in cost lineitem use it, else compute
		Double eduTax = prodCostBean.getEducationTax(pscId, year, month);

		if (eduTax != null) {
			return 0; // avoid double edu tax
		}

		double assessableProfit;

		assessableProfit = computeAssessableProfit(pscId, year, month);

		// return Math.max(0, (2 / 102) * assessableProfit); //TODO: verify if rate
		// should come from psc object
		// TODO: Take care of orphan psc object
		double eduTaxRate = psc.getTaxAndAllowance().getEducationTax();
		return Math.max(0, (eduTaxRate / 102) * assessableProfit);
	}

	private double computeAdjustedAssessableProfit(ProductionSharingContractId pscId, int year, int month) {
		double assessableProfit, educationTax;

		assessableProfit = computeAssessableProfit(pscId, year, month);
		educationTax = computeEducationTax(pscId, year, month);

		return assessableProfit - educationTax;
	}

	private double computeCurrentYearITA(ProductionSharingContractId pscId, int year, int month) {
		// TODO: Take care of orphan psc object again
		double itaRate = psc.getInvestmentTaxAllowanceCredit(); // TODO:verify application of ITA rate

		return computeCurrentYearCapex(pscId, year, month) * (itaRate / 100.0);
	}

	
	private double computeTotalAnnualAllowance(ProductionSharingContractId pscId, int year, int month) {
		double currYrITA, priorYrAnnualAllw, currYrCapAllw;

		currYrITA = computeCurrentYearITA(pscId, year, month);
		priorYrAnnualAllw = computePriorYearAnnualAllowance(pscId, year, month);
		currYrCapAllw = computeCurrentYearCapitalAllowance(pscId, year, month);

		return currYrITA + priorYrAnnualAllw + currYrCapAllw;
	}
	
	private double computePriorYearAnnualAllowance(ProductionSharingContractId pscId, int year, int month) {

		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year);

		if (!prodCostBean.fiscalPeriodExists(pscId, prevFp)) {
			return 0.0;
		}

		double UAA;

		UAA = computeUnrecoupedAnnualAllowance(pscId, prevFp.getYear(), prevFp.getMonth());

		return -1 * UAA;
	}

	private double computeCurrentYearCapitalAllowance(ProductionSharingContractId pscId, int year, int month) {
		CacheKey cacheKey = new CacheKey(pscId, year, month);
		Double capitalAllowance;

		if (amortizationCache.cache().containsKey(cacheKey)) {
			return amortizationCache.cache().get(cacheKey);
		}

		capitalAllowance = computeCurrentYearCapitalAllowance(pscId, new FiscalPeriod(year, month));

		return capitalAllowance;
	}
	
	private double computeCurrentYearCapitalAllowance(ProductionSharingContractId pscId, FiscalPeriod fp) {
		if (!fp.isCurrentYear()) {
			return 0;
		}

		if (!prodCostBean.fiscalPeriodExists(pscId, fp)) {
			return 0;
		}

		CacheKey cacheKey = new CacheKey(pscId, fp.getYear(), fp.getMonth());

		double currMonthCapitalAllw = prodCostBean.getCapitalAllowanceRecovery(pscId, fp.getYear(), fp.getMonth());// Armotization
		double currentYearCapitalAllw = currMonthCapitalAllw
				+ computeCurrentYearCapitalAllowance(pscId, fp.getPreviousFiscalPeriod());// cummulative

		amortizationCache.cache().put(cacheKey, currentYearCapitalAllw);

		return currentYearCapitalAllw;
	}
	
	private double computeUnrecoupedAnnualAllowance(ProductionSharingContractId pscId, int year, int month) {
		CacheKey cacheKey = new CacheKey(pscId, year, month);
		Double UAA;

		if (uAACache.cache().containsKey(cacheKey)) {
			return uAACache.cache().get(cacheKey);
		}

		double adjAssProfit, totalAnnualAllw;

		adjAssProfit = computeAdjustedAssessableProfit(pscId, year, month);
		totalAnnualAllw = computeTotalAnnualAllowance(pscId, year, month);

		UAA = Math.min(0, adjAssProfit - totalAnnualAllw);

		uAACache.cache().put(cacheKey, UAA);

		return UAA;
	}

	@Override
	public TaxOilDetail computeTaxOilDetail(ProductionSharingContractId pscId, int year, int month) {
		CacheKey cacheKey = new CacheKey(pscId, year, month);
		TaxOilDetail taxOilDetail = null;

		if (taxOilCache.cache().containsKey(cacheKey)) {
			return taxOilCache.cache().get(cacheKey);
		}

		taxOilDetail = new TaxOilDetail();

		double royalty = royaltyService.computeRoyaltyCum(pscId, year, month);
		taxOilDetail.setRoyalty(royalty);

		double grossIncome = computeGrossIncome(pscId, year, month);
		taxOilDetail.setGrossIncome(grossIncome);

		double opex = computeCurrentYearOpex(pscId, year, month);
		taxOilDetail.setOpex(opex);

		double lossBfw = 0;
		taxOilDetail.setLossBfw(lossBfw);

		double currentITA = computeCurrentYearITA(pscId, year, month);
		taxOilDetail.setCurrentITA(currentITA);

		double currentCapitalAllowance = computeCurrentYearCapitalAllowance(pscId, year, month);
		taxOilDetail.setCurrentCapitalAllowance(currentCapitalAllowance);

		// TODO: FIX ORPHAN PSC OBJECT - REFACTORING NEEDED
		double petroleumProfitTaxRate = psc.getPetroleumProfitTaxRate(makeDate(year, month));
		taxOilDetail.setPetroleumProfitTaxRate(petroleumProfitTaxRate);

		double educationTax = computeEducationTax(pscId, year, month);
		taxOilDetail.setEducationTax(educationTax);

		double monthlyMinimumTax = computeMonthlyMinimumTax(pscId, year, month);
		taxOilDetail.setMonthlyMinimumTax(monthlyMinimumTax);

		double priorYrAnnualAllw = computePriorYearAnnualAllowance(pscId, year, month);
		taxOilDetail.setPriorYearAnnualAllowance(priorYrAnnualAllw);

		taxOilCache.cache().put(cacheKey, taxOilDetail);

		return taxOilDetail;
	}

	@Override
	public TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContractId pscId, int year, int month) {
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year);

		if (!prodCostBean.fiscalPeriodExists(pscId, prevFp)) {
			return new TaxOilDetail();
		}

		return computeTaxOilDetail(pscId, prevFp.getYear(), prevFp.getMonth());

	}

	@Override
	public TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContractId pscId, int year, int month) {
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

		if (!prodCostBean.fiscalPeriodExists(pscId, prevFp)) {
			return new TaxOilDetail();
		}

		return computeTaxOilDetail(pscId, prevFp.getYear(), prevFp.getMonth());

	}

	@Override
	public TaxOilDetail buildTaxOil(ProductionSharingContractId pscId, int year, int month) {
		clearAllCache();
		return computeTaxOilDetail(pscId, year, month);
	}

	private void clearAllCache() {
		incomeCache.clear();
		uAACache.clear();
		amortizationCache.clear();
		taxOilCache.clear();
		taxOilAllocationCache.clear();
	}

	

	

	@Override
	public double computeMinimumTax(ProductionSharingContractId pscId, int year, int month) {
		double AAP;
		AAP = computeAdjustedAssessableProfit(pscId, year, month);

		return AAP * 0.15;
	}

	@Override
	public double computeMonthlyMinimumTax(ProductionSharingContractId pscId, int year, int month) {
		double minTaxCurrent, minTaxPrev;

		minTaxCurrent = computeMinimumTax(pscId, year, month);
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
		minTaxPrev = computeMinimumTax(pscId, prevFp.getYear(), prevFp.getMonth());

		return minTaxCurrent - minTaxPrev;
	}

	private double computeLossBf() {
		return 0;
	}

	private double computeCurrentYearOpex(ProductionSharingContractId pscId, int year, int month) {
		return prodCostBean.getCurrentYearOpex(pscId, year, month);
	}

	private double computeCurrentYearCapex(ProductionSharingContractId pscId, int year, int month) {
		return prodCostBean.getCurrentYearCapex(pscId, year, month);
	}

	private Date makeDate(int year, int month) {
		// Return date af the last day of a given year and month

		YearMonth yearMonthObject = YearMonth.of(year, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		return new GregorianCalendar(year, month - 1, daysInMonth).getTime();
	}

	@Override
	public TaxOilAllocation computeTaxOilAllocation(ProductionSharingContractId pscId, Integer year, Integer month) {
		CacheKey cacheKey = new CacheKey(pscId, year, month);

		if (taxOilAllocationCache.cache().containsKey(cacheKey)) {
			return taxOilAllocationCache.cache().get(cacheKey);
		}

		if (!prodCostBean.fiscalPeriodExists(pscId, year, month)) {
			return new TaxOilAllocation();
		}

		TaxOilAllocation allocation = new TaxOilAllocation();

		double taxOil = computeTaxOil(pscId, year, month);
		double corpLiftProceed = liftingBean.getCorporationProceed(pscId, year, month);
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
		Allocation prevAlloc = computeTaxOilAllocation(pscId, prevFp.getYear(), prevFp.getMonth());
		// TODO: FIX RoyaltyAllocation
		RoyaltyAllocation royAlloc = (RoyaltyAllocation) royaltyService.computeRoyaltyAllocation(pscId, year, month);// (this.psc,
																														// this.periodYear,
																														// this.periodMonth)

		allocation.setMonthlyCharge(taxOil);
		allocation.setLiftingProceed(corpLiftProceed);
		allocation.setRoyalty(royAlloc.getRoyaltyReceived());
		allocation.setChargeBfw(prevAlloc.getChargeCfw());
		allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

		taxOilAllocationCache.cache().put(cacheKey, allocation);

		return allocation;
	}

	@Override
	public Allocation computePreviousAllocation(ProductionSharingContractId pscId, int year, int month) {
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
		return computeTaxOilAllocation(pscId, prevFp.getYear(), prevFp.getMonth());
	}

	@Override
	public double computeMonthlyCharge(ProductionSharingContractId pscId, int year, int month) {
		return computeTaxOilAllocation(pscId, year, month).getMonthlyCharge();
	}

	@Override
	public double computeCumMonthlyCharge(ProductionSharingContractId pscId, int year, int month) {
		return computeTaxOilAllocation(pscId, year, month).getCumMonthlyCharge();
	}

	@Override
	public double computeReceived(ProductionSharingContractId pscId, int year, int month) {
		return computeTaxOilAllocation(pscId, year, month).getReceived();
	}

}
