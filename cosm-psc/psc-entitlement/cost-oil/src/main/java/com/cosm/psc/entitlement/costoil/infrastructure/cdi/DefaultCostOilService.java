package com.cosm.psc.entitlement.costoil.infrastructure.cdi;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.util.CacheKey;
import com.cosm.common.util.CacheUtil;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilAllocation;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilDetail;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilService;

/**
 * Created by Ayemi on 20/02/2018.
 */
@Dependent
public class DefaultCostOilService implements CostOilService {
    private CacheUtil<CacheKey, CostOilDetail> cache = new CacheUtil<>("costOil", 100);

    @Inject
    private TaxOilService taxBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private PscLiftingServices liftingBean;

    @Override
    public double computeCostOil(ProductionSharingContractId pscId, int year, int month) {
        return computeCostOilDetail(pscId, year, month).getCostOilCum();
    }

    private CostOilDetail computeCostOilDetail(ProductionSharingContractId pscId, int year, int month) {
        CacheKey cacheKey = new CacheKey(pscId, year, month);

        if (cache.cache().containsKey(cacheKey)) {
            return cache.cache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(pscId, year, month)) {
            return new CostOilDetail();
        }

        Double armotizedCapex, opex, eduTaxDiff, monthlyCurrentCharge;

        armotizedCapex = prodCostBean.getCapitalAllowanceRecovery(pscId, year, month);
        opex = prodCostBean.getOpex(pscId, year, month);
        eduTaxDiff = computeEducationTaxDiff(pscId, year, month);

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

        cache.cache().put(cacheKey, costOilDetail);

        return costOilDetail;

    }

    private double computeCostOilBfw(ProductionSharingContractId pscId, Integer year, Integer month) {
        double costOilDue, costOilAllocationReceived;

        CostOilDetail costOilDetail = computeCostOilDetail(pscId, year, month);
//        monthlyCurrentCharge = computeMonthlyCurrentCharge(psc, year, month, costOilDetail.getCostOil());
        CostOilAllocation costOilAllocation = computeCostOilAllocation(pscId, year, month);
        costOilDue = costOilDetail.getCostOilCum();
        costOilAllocationReceived = costOilAllocation.getReceived();

        return Math.max(0, costOilDue - costOilAllocationReceived);
    }

    @Override
    public CostOilDetail buildCostOilDetail(ProductionSharingContractId pscId, int year, int month) {
        return computeCostOilDetail(pscId, year, month);
    }

    private double computeEducationTaxDiff(ProductionSharingContractId pscId, int year, int month) {
        if (!prodCostBean.fiscalPeriodExists(pscId, year, month)) {
            return 0.0;
        }

        Double eduTax = taxBean.computeEducationTax(pscId, year, month);

        if (month == 1) {
            if (eduTax != null) {
                return eduTax;
            }
            return 0;
        }

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        return eduTax - computeEducationTaxDiff(pscId, prevFp.getYear(), prevFp.getMonth());
    }

    private double computeMonthlyCurrentCharge(ProductionSharingContractId pscId, Integer year, Integer month) {
        double proceed = liftingBean.getMonthlyIncome(pscId, year, month);
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
