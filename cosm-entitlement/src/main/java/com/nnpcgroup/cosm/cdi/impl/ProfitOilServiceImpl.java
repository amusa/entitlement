package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.*;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.ProfitOilAllocation;
import com.nnpcgroup.cosm.util.CacheKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by Ayemi on 27/04/2017.
 */
@Dependent
public class ProfitOilServiceImpl implements ProfitOilService {

    @Inject
    private CacheUtil cache;

    @Inject
    private RoyaltyService royaltyService;

    @Inject
    private CostOilService costOilService;

    @Inject
    private TaxOilService taxOilService;

    @EJB
    private PscLiftingServices liftingBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @EJB
    private ProductionCostServices prodCostBean;

    @Override
    public double computeRFactor(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getRfactorCache().containsKey(cacheKey)) {
            return cache.getRfactorCache().get(cacheKey);
        }

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        double costOil = costOilService.computeCumMonthlyCharge(psc, prevFp.getYear(), prevFp.getMonth());
        double profitOil = computeCumContractorMonthlyCharge(psc, prevFp.getYear(), prevFp.getMonth());
        double cost = prodCostBean.getCostToDate(psc, prevFp.getYear(), prevFp.getMonth());

        double rfactor = (costOil + profitOil) / cost;

        cache.getRfactorCache().put(cacheKey, rfactor);

        return rfactor;
    }

    @Override
    public Allocation computeCorporationProfitOilAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getCorporationProfitOilCache().containsKey(cacheKey)) {
            return cache.getCorporationProfitOilCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new ProfitOilAllocation();
        }

        ProfitOilAllocation allocation = new ProfitOilAllocation();

        double profitOil = computeProfitOil(psc, year, month);
        double rfactor = computeRFactor(psc, year, month);
        double profitRate = getProfitRate(rfactor);
        double corporationLiftProceed = liftingBean.getCorporationProceed(psc, year, month);
        double profitOilCharge = profitOil * (1 - profitRate);
        double royaltyReceived = royaltyService.computeReceived(psc, year, month);
        double taxOilReceived = taxOilService.computeReceived(psc, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeCorporationProfitOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
        allocation.setMonthlyCharge(profitOilCharge);
        allocation.setLiftingProceed(corporationLiftProceed);
        allocation.setDeductibles(royaltyReceived + taxOilReceived);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        cache.getCorporationProfitOilCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public Allocation computeContractorProfitOilAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getContractorProfitOilCache().containsKey(cacheKey)) {
            return cache.getContractorProfitOilCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new ProfitOilAllocation();
        }

        ProfitOilAllocation allocation = new ProfitOilAllocation();

        double profitOil = computeProfitOil(psc, year, month);
        double rfactor = computeRFactor(psc, year, month);
        double profitRate = getProfitRate(rfactor);
        double contractorLiftProceed = liftingBean.getContractorProceed(psc, year, month);
        double profitOilCharge = profitOil * profitRate;
        double costOilReceived = costOilService.computeReceived(psc, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeContractorProfitOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
        allocation.setMonthlyCharge(profitOilCharge);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setDeductibles(costOilReceived);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        cache.getContractorProfitOilCache().put(cacheKey, allocation);

        return allocation;
    }


    @Override
    public double computeProfitOil(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getProfitOilCache().containsKey(cacheKey)) {
            return cache.getProfitOilCache().get(cacheKey);
        }

        double currentProfitOil = computeCurrentProfitOil(psc, year, month);
        double cummulativeProfitOil = computeCummulativeProfitOil(psc, year, month);
        double profitOil = Math.max(0, currentProfitOil - cummulativeProfitOil);

        cache.getProfitOilCache().put(cacheKey, profitOil);

        return profitOil;
    }

    public double computeCumProfitOil(ProductionSharingContract psc, int year, int month) {
        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return 0;
        }

        double profitOil = computeProfitOil(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        return profitOil + computeCumProfitOil(psc, prevFp.getYear(), prevFp.getMonth());

    }

    @Override
    public double computeContractorMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeContractorProfitOilAllocation(psc, year, month).getMonthlyCharge();
    }

    @Override
    public double computeCumContractorMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeContractorProfitOilAllocation(psc, year, month).getCumMonthlyCharge();
    }

    private double computeCurrentProfitOil(ProductionSharingContract psc, int year, int month) {
        double proceed = liftingBean.getMonthlyIncome(psc, year, month);
        double royalty = royaltyService.computeMonthlyCharge(psc, year, month);
        double costOil = costOilService.computeMonthlyCharge(psc, year, month);
        double taxOil = taxOilService.computeMonthlyCharge(psc, year, month);

        return proceed - royalty - costOil - taxOil;
    }


    private double computeCummulativeProfitOil(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        double prevProceedCum = liftingBean.getMonthlyIncome(psc, prevFp.getYear(), prevFp.getMonth());
        double prevRoyaltyCum = royaltyService.computeCumMonthlyCharge(psc, prevFp.getYear(), prevFp.getMonth());
        double prevCostOilCum = costOilService.computeCumMonthlyCharge(psc, prevFp.getYear(), prevFp.getMonth());
        double prevTaxOilCum = taxOilService.computeCumMonthlyCharge(psc, prevFp.getYear(), prevFp.getMonth());
        double prevProfitOilCum = computeCumProfitOil(psc, prevFp.getYear(), prevFp.getMonth());

        return Math.max(0, prevProfitOilCum + prevTaxOilCum + prevCostOilCum + prevRoyaltyCum - prevProceedCum);
    }

    private double getProfitRate(double rfactor) {
        if (rfactor < 1.2) {
            return 0.7;
        } else if (rfactor > 2.5) {
            return 0.25;
        } else if (rfactor > 1.2 && rfactor < 2.5) {
            return 0.25 + ((2.5 - rfactor) / (2.5 - 1.2)) * 0.45;
        }
        return 0;
    }
}