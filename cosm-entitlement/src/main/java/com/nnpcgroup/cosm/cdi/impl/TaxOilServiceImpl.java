/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.RoyaltyService;
import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.cdi.FiscalPeriodService;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.crude.CrudePriceBean;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.cdi.TaxOilService;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.RoyaltyAllocation;
import com.nnpcgroup.cosm.report.schb1.TaxOilAllocation;
import com.nnpcgroup.cosm.util.CacheKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;

@Dependent
public class TaxOilServiceImpl implements TaxOilService {

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private PscLiftingServices liftingBean;

    @EJB
    private CrudePriceBean priceBean;

    @EJB
    private PscForecastDetailServices productionBean;

    @Inject
    private RoyaltyService royaltyService;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    GeneralController genController;

    @Inject
    private CacheUtil cache;

    @Override
    public double computeTaxOil(ProductionSharingContract psc, int year, int month) {
        return computeTaxOilDetail(psc, year, month).getTaxOil();
    }

    private double computeGrossIncome(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);
        Double grossIncome;

        if (cache.getIncomeCache().containsKey(cacheKey)) {
            return cache.getIncomeCache().get(cacheKey);
        }

        grossIncome = liftingBean.getGrossIncome(psc, year, month);
        cache.getIncomeCache().put(cacheKey, grossIncome);

        return grossIncome;
    }

    private double computeTotalDeduction(ProductionSharingContract psc, int year, int month) {
        double royalty, opex;
        opex = computeCurrentYearOpex(psc, year, month);
        royalty = royaltyService.computeRoyaltyCum(psc, year, month);

        return royalty + opex;
    }

    private double computeAdjustedProfit(ProductionSharingContract psc, int year, int month) {
        double totalDeduction, grossIncome, adjustedProfit;
        totalDeduction = computeTotalDeduction(psc, year, month);
        grossIncome = computeGrossIncome(psc, year, month);
        adjustedProfit = Math.max(0, grossIncome - totalDeduction);

        return adjustedProfit;
    }


    private double computeAssessableProfit(ProductionSharingContract psc, int year, int month) {
        double adjProfit, lossBf;

        adjProfit = computeAdjustedProfit(psc, year, month);
        lossBf = computeLossBf();

        return adjProfit + lossBf;

    }

    @Override
    public double computeEducationTax(ProductionSharingContract psc, int year, int month) {
//if education tax exists in cost lineitem use it, else compute
        Double eduTax = prodCostBean.getEducationTax(psc, year, month);

        if (eduTax != null) {
            return 0; //avoid double edu tax
        }

        double assessableProfit;

        assessableProfit = computeAssessableProfit(psc, year, month);

//        return Math.max(0, (2 / 102) * assessableProfit); //TODO: verify if rate should come from psc object
        double eduTaxRate = psc.getTaxAndAllowance().getEducationTax();
        return Math.max(0, (eduTaxRate / 102) * assessableProfit);
    }

    private double computeAdjustedAssessableProfit(ProductionSharingContract psc, int year, int month) {
        double assessableProfit, educationTax;

        assessableProfit = computeAssessableProfit(psc, year, month);
        educationTax = computeEducationTax(psc, year, month);

        return assessableProfit - educationTax;
    }

    private double computeCurrentYearITA(ProductionSharingContract psc, int year, int month) {
        double itaRate = psc.getInvestmentTaxAllowanceCredit(); //TODO:verify application of ITA rate

        return computeCurrentYearCapex(psc, year, month) * (itaRate / 100.0);
    }


    private double computeUnrecoupedAnnualAllowance(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);
        Double UAA;

        if (cache.getUAACache().containsKey(cacheKey)) {
            return cache.getUAACache().get(cacheKey);
        }

        double adjAssProfit, totalAnnualAllw;

        adjAssProfit = computeAdjustedAssessableProfit(psc, year, month);
        totalAnnualAllw = computeTotalAnnualAllowance(psc, year, month);

        UAA = Math.min(0, adjAssProfit - totalAnnualAllw);

        cache.getUAACache().put(cacheKey, UAA);

        return UAA;
    }

    private double computePriorYearAnnualAllowance(ProductionSharingContract psc, int year, int month) {

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year);

        if (!prodCostBean.fiscalPeriodExists(psc, prevFp)) {
            return 0.0;
        }

        double UAA;

        UAA = computeUnrecoupedAnnualAllowance(psc, prevFp.getYear(), prevFp.getMonth());

        return -1 * UAA;
    }

    private double computeCurrentYearCapitalAllowance(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);
        Double capitalAllowance;

        if (cache.getAmortizationCache().containsKey(cacheKey)) {
            return cache.getAmortizationCache().get(cacheKey);
        }

        capitalAllowance = computeCurrentYearCapitalAllowance(psc, new FiscalPeriod(year, month));

        return capitalAllowance;
    }

    @Override
    public TaxOilDetail computeTaxOilDetail(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);
        TaxOilDetail taxOilDetail = null;

        if (cache.getTaxOilCache().containsKey(cacheKey)) {
            return cache.getTaxOilCache().get(cacheKey);
        }

        taxOilDetail = new TaxOilDetail();

        double royalty = royaltyService.computeRoyaltyCum(psc, year, month);
        taxOilDetail.setRoyalty(royalty);

        double grossIncome = computeGrossIncome(psc, year, month);
        taxOilDetail.setGrossIncome(grossIncome);

        double opex = computeCurrentYearOpex(psc, year, month);
        taxOilDetail.setOpex(opex);

        double lossBfw = 0;
        taxOilDetail.setLossBfw(lossBfw);

        double currentITA = computeCurrentYearITA(psc, year, month);
        taxOilDetail.setCurrentITA(currentITA);

        double currentCapitalAllowance = computeCurrentYearCapitalAllowance(psc, year, month);
        taxOilDetail.setCurrentCapitalAllowance(currentCapitalAllowance);

        double petroleumProfitTaxRate = psc.getPetroleumProfitTaxRate(makeDate(year, month));
        taxOilDetail.setPetroleumProfitTaxRate(petroleumProfitTaxRate);

        double educationTax = computeEducationTax(psc, year, month);
        taxOilDetail.setEducationTax(educationTax);

        double monthlyMinimumTax = computeMonthlyMinimumTax(psc, year, month);
        taxOilDetail.setMonthlyMinimumTax(monthlyMinimumTax);

        double priorYrAnnualAllw = computePriorYearAnnualAllowance(psc, year, month);
        taxOilDetail.setPriorYearAnnualAllowance(priorYrAnnualAllw);

        cache.getTaxOilCache().put(cacheKey, taxOilDetail);

        return taxOilDetail;
    }

    @Override
    public TaxOilDetail getPriorYearTaxOilDetail(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year);

        if (!prodCostBean.fiscalPeriodExists(psc, prevFp)) {
            return new TaxOilDetail();
        }

        return computeTaxOilDetail(psc, prevFp.getYear(), prevFp.getMonth());

    }

    @Override
    public TaxOilDetail getPriorMonthTaxOilDetail(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        if (!prodCostBean.fiscalPeriodExists(psc, prevFp)) {
            return new TaxOilDetail();
        }

        return computeTaxOilDetail(psc, prevFp.getYear(), prevFp.getMonth());

    }


    @Override
    public TaxOilDetail buildTaxOil(ProductionSharingContract psc, int year, int month) {
        cache.clearAll();
        return computeTaxOilDetail(psc, year, month);
    }


    private double computeCurrentYearCapitalAllowance(ProductionSharingContract psc, FiscalPeriod fp) {
        if (!fp.isCurrentYear()) {
            return 0;
        }

        if (!prodCostBean.fiscalPeriodExists(psc, fp)) {
            return 0;
        }

        CacheKey cacheKey = new CacheKey(psc, fp.getYear(), fp.getMonth());

        double currMonthCapitalAllw = prodCostBean.getCapitalAllowanceRecovery(psc, fp.getYear(), fp.getMonth());// Armotization
        double currentYearCapitalAllw = currMonthCapitalAllw + computeCurrentYearCapitalAllowance(psc, fp.getPreviousFiscalPeriod());//cummulative

        cache.getAmortizationCache().put(cacheKey, currentYearCapitalAllw);

        return currentYearCapitalAllw;
    }

    private double computeTotalAnnualAllowance(ProductionSharingContract psc, int year, int month) {
        double currYrITA, priorYrAnnualAllw, currYrCapAllw;

        currYrITA = computeCurrentYearITA(psc, year, month);
        priorYrAnnualAllw = computePriorYearAnnualAllowance(psc, year, month);
        currYrCapAllw = computeCurrentYearCapitalAllowance(psc, year, month);

        return currYrITA + priorYrAnnualAllw + currYrCapAllw;
    }

    @Override
    public double computeMinimumTax(ProductionSharingContract psc, int year, int month) {
        double AAP;
        AAP = computeAdjustedAssessableProfit(psc, year, month);

        return AAP * 0.15;
    }

    @Override
    public double computeMonthlyMinimumTax(ProductionSharingContract psc, int year, int month) {
        double minTaxCurrent, minTaxPrev;

        minTaxCurrent = computeMinimumTax(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        minTaxPrev = computeMinimumTax(psc, prevFp.getYear(), prevFp.getMonth());

        return minTaxCurrent - minTaxPrev;
    }

    private double computeLossBf() {
        return 0;
    }

    private double computeCurrentYearOpex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getCurrentYearOpex(psc, year, month);
    }

    private double computeCurrentYearCapex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getCurrentYearCapex(psc, year, month);
    }

    private Date makeDate(int year, int month) {
        //Return date af the last day of a given year and month

        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return new GregorianCalendar(year, month - 1, daysInMonth).getTime();
    }

    @Override
    public TaxOilAllocation computeTaxOilAllocation(ProductionSharingContract psc, Integer year, Integer month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getTaxOilAllocationCache().containsKey(cacheKey)) {
            return cache.getTaxOilAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new TaxOilAllocation();
        }

        TaxOilAllocation allocation = new TaxOilAllocation();

        double taxOil = computeTaxOil(psc, year, month);
        double corpLiftProceed = liftingBean.getCorporationProceed(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeTaxOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
        RoyaltyAllocation royAlloc = (RoyaltyAllocation) royaltyService.computeRoyaltyAllocation(psc, year, month);//(this.psc, this.periodYear, this.periodMonth)

        allocation.setMonthlyCharge(taxOil);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setRoyalty(royAlloc.getRoyaltyReceived());
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        cache.getTaxOilAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        return computeTaxOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
    }

    @Override
    public double computeMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeTaxOilAllocation(psc, year, month).getMonthlyCharge();
    }

    @Override
    public double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeTaxOilAllocation(psc, year, month).getCumMonthlyCharge();
    }

    @Override
    public double computeReceived(ProductionSharingContract psc, int year, int month) {
        return computeTaxOilAllocation(psc, year, month).getReceived();
    }

    public ProductionCostServices getProdCostBean() {
        return prodCostBean;
    }

    public PscLiftingServices getLiftingBean() {
        return liftingBean;
    }

    public CrudePriceBean getPriceBean() {
        return priceBean;
    }

    public PscForecastDetailServices getProductionBean() {
        return productionBean;
    }

    public RoyaltyService getRoyaltyService() {
        return royaltyService;
    }

    public FiscalPeriodService getFiscalService() {
        return fiscalService;
    }

    public GeneralController getGenController() {
        return genController;
    }

    public CacheUtil getCache() {
        return cache;
    }
}
