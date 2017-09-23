package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.FiscalPeriodService;
import com.nnpcgroup.cosm.cdi.RoyaltyService;
import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.crude.CrudePriceBean;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.Model1993Psc;
import com.nnpcgroup.cosm.entity.Model2005Psc;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.RoyaltyAllocation;
import com.nnpcgroup.cosm.util.CacheKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ayemi on 29/03/2017.
 */
@Dependent
public class RoyaltyServiceImpl implements RoyaltyService {

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
    public double computeRoyalty(ProductionSharingContract psc, int year, int month) {
        double royalty, royRate, grossProd;

        //royRate = getRoyaltyRate(psc);
        grossProd = productionBean.getGrossProduction(psc, year, month);
        royRate = computeRoyaltyRate(psc, year, month, grossProd);
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

    @Override
    public double computeRoyaltyCum(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);
        Double royalty;

        if (cache.getRoyaltyCache().containsKey(cacheKey)) {
            return cache.getRoyaltyCache().get(cacheKey);
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

        CacheKey cacheKey = new CacheKey(psc, fp.getYear(), fp.getMonth());
        double currentMonthRoyalty = computeRoyalty(psc, fp.getYear(), fp.getMonth());
        double previousMonthRoyalty = computeRoyaltyCum(psc, fp.getPreviousFiscalPeriod());
        double royalty = currentMonthRoyalty + previousMonthRoyalty;

        cache.getRoyaltyCache().put(cacheKey, royalty);

        return royalty;

    }


    @Override
    public Allocation computeRoyaltyAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getRoyaltyAllocationCache().containsKey(cacheKey)) {
            return cache.getRoyaltyAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new RoyaltyAllocation();
        }

        RoyaltyAllocation allocation = new RoyaltyAllocation();

        double royalty = computeRoyalty(psc, year, month);
        double corpLiftProceed = liftingBean.getCorporationProceed(psc, year, month);
        //handling cash payment
        double cashPayment = liftingBean.getCashPayment(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeRoyaltyAllocation(psc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(royalty);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setCashPayment(cashPayment);
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        cache.getRoyaltyAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        return computeRoyaltyAllocation(psc, prevFp.getYear(), prevFp.getMonth());
    }

    @Override
    public double computeMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeRoyaltyAllocation(psc, year, month).getMonthlyCharge();
    }

    @Override
    public double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeRoyaltyAllocation(psc, year, month).getCumMonthlyCharge();
    }

    @Override
    public double computeReceived(ProductionSharingContract psc, int year, int month) {
        return computeRoyaltyAllocation(psc, year, month).getReceived();
    }

    private int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }


    public double computeRoyaltyRate(ProductionSharingContract psc, int year, int month, double grossProd) {
        //TODO:candidate for subclass
        int days = genController.daysOfMonth(year, month);
        Double dailyProd = grossProd / days;

        if (psc instanceof Model2005Psc) {
            if (dailyProd < 2000.0) {
                return 5.0;
            } else if (dailyProd >= 2000.0 && dailyProd < 5000.0) {
                return 7.5;
            } else if (dailyProd >= 5000.0 && dailyProd < 10000.0) {
                return 15.0;
            }
            return 20.0;
        } else if (psc instanceof Model1993Psc) {
            return psc.getRoyaltyRate();
        }
        return 0;
    }
}
