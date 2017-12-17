package com.cosm.psc.infrastructure;

import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.psc.domain.shared.CacheKey;
import com.cosm.psc.domain.shared.CacheUtil;
import com.cosm.psc.domain.model.Allocation;
import com.cosm.psc.domain.model.CostOilAllocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.cost.CostOilDetail;
import com.cosm.psc.domain.model.cost.CostQueryRepository;
import com.cosm.psc.domain.model.lifting.LiftingQueryRepository;
import com.cosm.psc.domain.service.CostOilService;
import com.cosm.psc.domain.service.TaxOilService;

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

    @Inject
    private CostQueryRepository costQueryRepository;

    @EJB
    private LiftingQueryRepository liftingQueryRepository;

    @Override
    public double computeCostOil(ProductionSharingContract psc, int year, int month) {
        return computeCostOilDetail(psc, year, month).getCostOilCum();
    }

    private CostOilDetail computeCostOilDetail(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getCostOilDetailCache().containsKey(cacheKey)) {
            return cache.getCostOilDetailCache().get(cacheKey);
        }

        if (!costQueryRepository.fiscalPeriodExists(psc, year, month)) {
            return new CostOilDetail();
        }

        Double armotizedCapex, opex, eduTaxDiff, monthlyCurrentCharge;

        armotizedCapex = costQueryRepository.getCapitalAllowanceRecovery(psc, year, month);
        opex = costQueryRepository.getOpex(psc, year, month);
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
        if (!costQueryRepository.fiscalPeriodExists(psc, year, month)) {
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
        double proceed = liftingQueryRepository.getMonthlyIncome(psc, year, month);
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

        if (!costQueryRepository.fiscalPeriodExists(psc, year, month)) {
            return new CostOilAllocation();
        }

        CostOilAllocation allocation = new CostOilAllocation();

//        double costOil = computeCostOil(psc, year, month);
        double contractorLiftProceed = liftingQueryRepository.getContractorProceed(psc, year, month);
//        double proceed = liftingQueryRepository.getMonthlyIncome(psc, year, month);
//        double costRecoveryLimitRate = psc.getCostRecoveryLimit() / 100.0;
//
//        costOil = Math.max(0, Math.min(proceed * costRecoveryLimitRate, costOil));
        double costOilCharge = computeMonthlyCurrentCharge(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeCostOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(costOilCharge);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        cache.getCostOilAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        return computeCostOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
    }

    @Override
    public double computeMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeCostOilAllocation(psc, year, month).getMonthlyCharge();
    }

    @Override
    public double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month) {
        return computeCostOilAllocation(psc, year, month).getCumMonthlyCharge();
    }

    @Override
    public double computeReceived(ProductionSharingContract psc, int year, int month) {
        return computeCostOilAllocation(psc, year, month).getReceived();
    }
}
