/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.tax.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.FiscalPeriodService;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.crude.CrudePriceBean;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.ejb.tax.TaxServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;
import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.entity.tax.TaxOilKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

@Dependent
public class TaxServicesImpl implements TaxServices, Serializable {

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private PscLiftingServices liftingBean;

    @EJB
    private CrudePriceBean priceBean;

    @EJB
    private PscForecastDetailServices productionBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    GeneralController genController;

    @Inject
    private CacheUtil cache;

    @Override
    public double computeTaxOil(ProductionSharingContract psc, int year, int month) {
        return Math.max(0, computePayableTaxToDate(psc, year, month));
    }

    @Override
    public double computeGrossIncome(ProductionSharingContract psc, int year, int month) {
        TaxOilKey taxOilKey = new TaxOilKey(psc, year, month);
        Double grossIncome;

        if (cache.getIncomeCache().containsKey(taxOilKey)) {
            return cache.getIncomeCache().get(taxOilKey);
        }

        grossIncome = computeGrossIncome(psc, new FiscalPeriod(year, month));

        return grossIncome;
    }

    private double computeGrossIncome(ProductionSharingContract psc, FiscalPeriod fp) {
        if (!fp.isCurrentYear()) {
            return 0;
        }

        if (!prodCostBean.fiscalPeriodExists(psc, fp)) {
            return 0.0;
        }
        double currentMonthIncome = 0;
        List<PscLifting> pscLiftings = liftingBean.find(psc, fp.getYear(), fp.getMonth());
        double price;


        for (PscLifting lifting : pscLiftings) {
//            CrudePricePK pricePK = new CrudePricePK();
//            pricePK.setCrudeTypeCode(psc.getCrudeType().getCode());
//            pricePK.setPriceDate(lifting.getLiftingDate());
//            CrudePrice crudePrice = priceBean.find(pricePK);
//
//            if (crudePrice != null) {
//                price = crudePrice.getOsPrice();
//            } else {
//                price = 0;
//            }
//
//            grossIncome += (lifting.getTotalLifting() * price);
            currentMonthIncome += lifting.getRevenue();

        }

        TaxOilKey taxOilKey = new TaxOilKey(psc, fp.getYear(), fp.getMonth());
        double grossIncome =
                currentMonthIncome + computeGrossIncome(psc, fp.getPreviousFiscalPeriod());//recursively compute cummulative gross income;

        cache.getIncomeCache().put(taxOilKey, grossIncome);

        return grossIncome;
    }

    @Override
    public double computeTotalDeduction(ProductionSharingContract psc, int year, int month) {
        double royalty, opex;
        opex = computeCurrentYearOpex(psc, year, month);
        royalty = computeRoyaltyCum(psc, year, month);

        return royalty + opex;
    }

    @Override
    public double computeRoyalty(ProductionSharingContract psc, int year, int month) {
        double royalty, royRate, grossProd;

        //royRate = getRoyaltyRate(psc);
        grossProd = productionBean.getGrossProduction(psc, year, month);
        int days = genController.daysOfMonth(year, month);
        Double dailyProd = grossProd / days;
        royRate = computeRoyaltyRate(dailyProd);
        double weightedAvePrice = liftingBean.computeWeightedAvePrice(psc, year, month);

//        grossProdCum = productionBean.getGrossProductionToDate(psc, year, month);//cummulative production

        double consessionRental = 0;

        if (productionBean.isFirstProductionOfYear(psc, year, month)) {
            consessionRental = psc.getOmlAnnualConcessionRental();
            if (getYearOfDate(psc.getFirstOilDate()) == year) {
                consessionRental += psc.getOplTotalConcessionRental();
            }
        }

        royalty = (grossProd * (royRate / 100) * weightedAvePrice) + consessionRental;

        return royalty;
    }

    private int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    @Override
    public double computeRoyaltyCum(ProductionSharingContract psc, int year, int month) {
        TaxOilKey taxOilKey = new TaxOilKey(psc, year, month);
        Double royalty;

        if (cache.getRoyaltyCache().containsKey(taxOilKey)) {
            return cache.getRoyaltyCache().get(taxOilKey);
        }

        royalty = computeRoyaltyCum(psc, new FiscalPeriod(year, month));

        return royalty;
    }

    private double computeRoyaltyCum(ProductionSharingContract psc, FiscalPeriod fp) {

        if (!fp.isCurrentYear()) {
            return 0;
        }

        if (!prodCostBean.fiscalPeriodExists(psc, fp)) {
            return 0;
        }

        TaxOilKey taxOilKey = new TaxOilKey(psc, fp.getYear(), fp.getMonth());
        double currentMonthRoyalty = computeRoyalty(psc, fp.getYear(), fp.getMonth());
        double previousMonthRoyalty = computeRoyaltyCum(psc, fp.getPreviousFiscalPeriod());
        double royalty = currentMonthRoyalty + previousMonthRoyalty;

        cache.getRoyaltyCache().put(taxOilKey, royalty);

        return royalty;

    }

    public double computeRoyaltyRate(double dailyProd) {
        if (dailyProd < 2000.0) {
            return 5.0;
        } else if (dailyProd >= 2000.0 && dailyProd < 5000.0) {
            return 7.5;
        } else if (dailyProd >= 5000.0 && dailyProd < 10000.0) {
            return 15.0;
        }
        return 20.0;

    }

    @Override
    public double computeAdjustedProfit(ProductionSharingContract psc, int year, int month) {
        double totalDeduction, grossIncome, adjustedProfit;
        totalDeduction = computeTotalDeduction(psc, year, month);
        grossIncome = computeGrossIncome(psc, year, month);
        adjustedProfit = Math.max(0, grossIncome - totalDeduction);

        return adjustedProfit;
    }

    @Override
    public double computeAssessableProfit(ProductionSharingContract psc, int year, int month) {
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

    @Override
    public double computeAdjustedAssessableProfit(ProductionSharingContract psc, int year, int month) {
        double assessableProfit, educationTax;

        assessableProfit = computeAssessableProfit(psc, year, month);
        educationTax = computeEducationTax(psc, year, month);

        return assessableProfit - educationTax;
    }

    @Override
    public double computeCurrentYearITA(ProductionSharingContract psc, int year, int month) {
        double itaRate = psc.getInvestmentTaxAllowanceCredit(); //TODO:verify application of ITA rate

        return computeCurrentYearCapex(psc, year, month) * (itaRate / 100.0);
    }


    @Override
    public double computeUnrecoupedAnnualAllowance(ProductionSharingContract psc, int year, int month) {
        TaxOilKey taxOilKey = new TaxOilKey(psc, year, month);
        Double UAA;

        if (cache.getUAACache().containsKey(taxOilKey)) {
            return cache.getUAACache().get(taxOilKey);
        }

        double adjAssProfit, totalAnnualAllw;

        adjAssProfit = computeAdjustedAssessableProfit(psc, year, month);
        totalAnnualAllw = computeTotalAnnualAllowance(psc, year, month);

        UAA = Math.min(0, adjAssProfit - totalAnnualAllw);

        cache.getUAACache().put(taxOilKey, UAA);

        return UAA;
    }

    @Override
    public double computePriorYearAnnualAllowance(ProductionSharingContract psc, int year, int month) {

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year);

        if (!prodCostBean.fiscalPeriodExists(psc, prevFp)) {
            return 0.0;
        }

        double UAA;

        UAA = computeUnrecoupedAnnualAllowance(psc, prevFp.getYear(), prevFp.getMonth());

        return -1 * UAA;
    }

    @Override
    public double computeCurrentYearCapitalAllowance(ProductionSharingContract psc, int year, int month) {
        TaxOilKey taxOilKey = new TaxOilKey(psc, year, month);
        Double capitalAllowance;

        if (cache.getAmortizationCache().containsKey(taxOilKey)) {
            return cache.getAmortizationCache().get(taxOilKey);
        }

        capitalAllowance = computeCurrentYearCapitalAllowance(psc, new FiscalPeriod(year, month));

        return capitalAllowance;
    }

    @Override
    public TaxOilDetail computeTaxOilDetail(ProductionSharingContract psc, int year, int month) {
        TaxOilKey taxOilKey = new TaxOilKey(psc, year, month);
        TaxOilDetail taxOilDetail = null;

        if (cache.getTaxOilCache().containsKey(taxOilKey)) {
            return cache.getTaxOilCache().get(taxOilKey);
        }

        taxOilDetail = new TaxOilDetail();

        double royalty = computeRoyaltyCum(psc, year, month);
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

        cache.getTaxOilCache().put(taxOilKey, taxOilDetail);

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

        TaxOilKey taxOilKey = new TaxOilKey(psc, fp.getYear(), fp.getMonth());

        double currMonthCapitalAllw = prodCostBean.getCapitalAllowanceRecovery(psc, fp.getYear(), fp.getMonth());// Armotization
        double currentYearCapitalAllw = currMonthCapitalAllw + computeCurrentYearCapitalAllowance(psc, fp.getPreviousFiscalPeriod());//cummulative

        cache.getAmortizationCache().put(taxOilKey, currentYearCapitalAllw);

        return currentYearCapitalAllw;
    }

    @Override
    public double computeTotalAnnualAllowance(ProductionSharingContract psc, int year, int month) {
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

    @Override
    public double computeAdjustedProfitLessITA(ProductionSharingContract psc, int year, int month) {
        double AAP, currYrITA;

        currYrITA = computeCurrentYearITA(psc, year, month);
        AAP = computeAdjustedAssessableProfit(psc, year, month);

        return (AAP * 0.85) - (currYrITA * 1.7);
    }

    @Override
    public double computeSection18DeductionLower(ProductionSharingContract psc, int year, int month) {
        double totalAnnualAllw, adjProfitLessITA;

        totalAnnualAllw = computeTotalAnnualAllowance(psc, year, month);
        adjProfitLessITA = computeAdjustedProfitLessITA(psc, year, month);

        return Math.max(0, Math.min(adjProfitLessITA, totalAnnualAllw));
    }

    @Override
    public double computeChargeableTaxToDate(ProductionSharingContract psc, int year, int month) {
        double chargeProfit2Date, petProfTaxRate;
        petProfTaxRate = psc.getPetroleumProfitTaxRate();
        chargeProfit2Date = computeChargeableProfitToDate(psc, year, month);

        return chargeProfit2Date * petProfTaxRate;

    }

    @Override
    public double computeChargeableProfitToDate(ProductionSharingContract psc, int year, int month) {
        double AAP, sect18DeductLower;

        AAP = computeAdjustedAssessableProfit(psc, year, month);
        sect18DeductLower = computeSection18DeductionLower(psc, year, month);

        return Math.max(0, AAP - sect18DeductLower);
    }

    @Override
    public double computePayableTaxToDate(ProductionSharingContract psc, int year, int month) {
        double taxMin, chargeableTaxToDate;

        taxMin = computeMonthlyMinimumTax(psc, year, month);
        chargeableTaxToDate = computeChargeableTaxToDate(psc, year, month);

        return Math.max(taxMin, ((taxMin < 0 && chargeableTaxToDate < 0) ? 0 : chargeableTaxToDate));
    }

    private double computeLossBf() {
        return 0;
    }

    private double computeOpex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getOpex(psc, year, month);
    }

    private double computeCurrentYearOpex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getCurrentYearOpex(psc, year, month);
    }

    private double computeCapex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getCapex(psc, year, month);
    }

    private double computeCurrentYearCapex(ProductionSharingContract psc, int year, int month) {
        return prodCostBean.getCurrentYearCapex(psc, year, month);
    }

    private double getRoyaltyRate(ProductionSharingContract psc) {
        return psc.getRoyaltyRate();

    }

    private Date makeDate(int year, int month) {
        //Return date af the last day of a given year and month

        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return new GregorianCalendar(year, month - 1, daysInMonth).getTime();
    }
}
