package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.CostOilService;
import com.nnpcgroup.cosm.cdi.FiscalPeriodService;
import com.nnpcgroup.cosm.cdi.TaxOilService;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostOilDetail;
import com.nnpcgroup.cosm.report.schb1.Allocation;
import com.nnpcgroup.cosm.report.schb1.CostOilAllocation;
import com.nnpcgroup.cosm.util.CacheKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by Ayemi on 29/03/2017.
 */
@Dependent
public class CostOilServiceImpl implements CostOilService {
    @Inject
    private CacheUtil cache;

    @Inject
    private TaxOilService taxBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private PscLiftingServices liftingBean;

    @Override
    public double computeCostOil(ProductionSharingContract psc, int year, int month) {
        return computeCostOilDetail(psc, year, month).getCostOilCum();
    }

    private CostOilDetail computeCostOilDetail(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getCostOilDetailCache().containsKey(cacheKey)) {
            return cache.getCostOilDetailCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new CostOilDetail();
        }

        Double armotizedCapex, opex, eduTaxDiff, monthlyCurrentCharge;

        armotizedCapex = prodCostBean.getCapitalAllowanceRecovery(psc, year, month);
        opex = prodCostBean.getOpex(psc, year, month);
        eduTaxDiff = computeEducationTaxDiff(psc, year, month);

        CostOilDetail costOilDetail = new CostOilDetail();
        costOilDetail.setArmotizedCapex(armotizedCapex);
        costOilDetail.setOpex(opex);
        costOilDetail.setEducationTax(eduTaxDiff);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

//        CostOilDetail prevCostOilDetail = computeCostOilDetail(psc, prevFp.getYear(), prevFp.getMonth());
//        CostOilAllocation prevCostOilAllocation = computeCostOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
//        monthlyCurrentCharge = computeMonthlyCurrentCharge(psc, prevFp.getYear(), prevFp.getMonth(), prevCostOilDetail.getCostOilCum());
//        double costOilBfw = Math.max(0, prevCostOilDetail.getCostOilCum() - prevCostOilAllocation.getReceived());

        double costOilBfw = computeCostOilBfw(psc, prevFp.getYear(), prevFp.getMonth());
        costOilDetail.setCostOilBfw(costOilBfw);

        cache.getCostOilDetailCache().put(cacheKey, costOilDetail);

        return costOilDetail;

    }

    private double computeCostOilBfw(ProductionSharingContract psc, Integer year, Integer month) {
        double costOilDue, costOilAllocationReceived;

        CostOilDetail costOilDetail = computeCostOilDetail(psc, year, month);
//        monthlyCurrentCharge = computeMonthlyCurrentCharge(psc, year, month, costOilDetail.getCostOil());
        CostOilAllocation costOilAllocation = computeCostOilAllocation(psc, year, month);
        costOilDue = costOilDetail.getCostOilCum();
        costOilAllocationReceived = costOilAllocation.getReceived();

        return Math.max(0, costOilDue - costOilAllocationReceived);
    }

    @Override
    public CostOilDetail buildCostOilDetail(ProductionSharingContract psc, int year, int month) {
        return computeCostOilDetail(psc, year, month);
    }

    private double computeEducationTaxDiff(ProductionSharingContract psc, int year, int month) {
        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return 0.0;
        }

        Double eduTax = taxBean.computeEducationTax(psc, year, month);

        if (month == 1) {
            if (eduTax != null) {
                return eduTax;
            }
            return 0;
        }

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        return eduTax - computeEducationTaxDiff(psc, prevFp.getYear(), prevFp.getMonth());
    }

    private double computeMonthlyCurrentCharge(ProductionSharingContract psc, Integer year, Integer month) {
        double proceed = liftingBean.getMonthlyIncome(psc, year, month);
        double costRecoveryLimitRate = psc.getCostRecoveryLimit() / 100.0;
        double costOil = computeCostOil(psc, year, month);
        double costOilCharge = Math.max(0, Math.min(proceed * costRecoveryLimitRate, costOil));

//        return Math.max(0, Math.min(proceed * costRecoveryLimitRate, costOilCharge));
        return costOilCharge;
    }

    @Override
    public CostOilAllocation computeCostOilAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getCostOilAllocationCache().containsKey(cacheKey)) {
            return cache.getCostOilAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new CostOilAllocation();
        }

        CostOilAllocation allocation = new CostOilAllocation();

//        double costOil = computeCostOil(psc, year, month);
        double contractorLiftProceed = liftingBean.getContractorProceed(psc, year, month);
//        double proceed = liftingBean.getMonthlyIncome(psc, year, month);
//        double costRecoveryLimitRate = psc.getCostRecoveryLimit() / 100.0;
//
//        costOil = Math.max(0, Math.min(proceed * costRecoveryLimitRate, costOil));
        double costOilCharge = computeMonthlyCurrentCharge(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeCostOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(costOilCharge);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        cache.getCostOilAllocationCache().put(cacheKey, allocation);

        return allocation;
    }
}
