package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.ScheduleB1Service;
import com.nnpcgroup.cosm.controller.CostOilController;
import com.nnpcgroup.cosm.cdi.FiscalPeriodService;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.cdi.TaxServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;
import com.nnpcgroup.cosm.report.schb1.*;
import com.nnpcgroup.cosm.util.CacheKey;
import com.nnpcgroup.cosm.util.CacheUtil;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayemi on 23/03/2017.
 */
@Dependent
public class ScheduleB1ServiceImpl implements ScheduleB1Service {
    @Inject
    private CacheUtil cache;

    @Inject
    private TaxServices taxBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    private CostOilController costOilController;

    @EJB
    private ProductionCostServices prodCostBean;

    @EJB
    private PscLiftingServices liftingBean;


    @Override
    public List<ProceedAllocation> processProceedAllocation(ProductionSharingContract psc, int year, int month) {
        List<ProceedAllocation> proceedAllocationList = new ArrayList<>();

        ProceedAllocation royPa = new ProceedAllocation();
        Allocation royAlloc = processRoyaltyAllocation(psc, year, month);
        royPa.setCategoryTitle("ROYALTY OIL");
        royPa.setMonthlyChargeBfw(royAlloc.getChargeBfw());
        royPa.setMonthlyCharge(royAlloc.getMonthlyCharge());
        royPa.setRecoverable(royAlloc.getRecoverable());
        royPa.setCorporationProceed(royAlloc.getReceived());
        royPa.setMonthlyChargeCfw(royAlloc.getChargeCfw());

        proceedAllocationList.add(royPa);

        ProceedAllocation coPa = new ProceedAllocation();
        Allocation costOilAlloc = processCostOilAllocation(psc, year, month);
        coPa.setCategoryTitle("COST OIL");
        coPa.setMonthlyChargeBfw(costOilAlloc.getChargeBfw());
        coPa.setMonthlyCharge(costOilAlloc.getMonthlyCharge());
        coPa.setRecoverable(costOilAlloc.getRecoverable());
        coPa.setContractorProceed(costOilAlloc.getReceived());
        coPa.setMonthlyChargeCfw(costOilAlloc.getChargeCfw());

        proceedAllocationList.add(coPa);

        ProceedAllocation toPa = new ProceedAllocation();
        Allocation taxOilAlloc = processTaxOilAllocation(psc, year, month);
        toPa.setCategoryTitle("TAX OIL");
        toPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
        toPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
        toPa.setRecoverable(taxOilAlloc.getRecoverable());
        toPa.setCorporationProceed(taxOilAlloc.getReceived());
        toPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(toPa);

        ProceedAllocation corpPoPa = new ProceedAllocation();
        // Allocation profOilAlloc = processProfitOil(this.psc, this.periodYear, this.periodMonth);
        corpPoPa.setCategoryTitle("CORP PROFIT OIL");
//        corpPoPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
//        corpPoPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
//        corpPoPa.setRecoverable(taxOilAlloc.getRecoverable());
//        corpPoPa.setContractorProceed(taxOilAlloc.getReceived());
//        corpPoPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(corpPoPa);

        ProceedAllocation contPoPa = new ProceedAllocation();
        // Allocation profOilAlloc = processProfitOil(this.psc, this.periodYear, this.periodMonth);
        contPoPa.setCategoryTitle("CONT PROFIT OIL");
//        contPoPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
//        contPoPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
//        contPoPa.setRecoverable(taxOilAlloc.getRecoverable());
//        contPoPa.setContractorProceed(taxOilAlloc.getReceived());
//        contPoPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(contPoPa);

        return proceedAllocationList;

    }

    @Override
    public RoyaltyAllocation processRoyaltyAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getRoyaltyAllocationCache().containsKey(cacheKey)) {
            return cache.getRoyaltyAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new RoyaltyAllocation();
        }

        RoyaltyAllocation allocation = new RoyaltyAllocation();

        double royalty = taxBean.computeRoyalty(psc, year, month);
        double corpLiftProceed = liftingBean.getCorporationProceed(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = processRoyaltyAllocation(psc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(royalty);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        cache.getRoyaltyAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public CostOilAllocation processCostOilAllocation(ProductionSharingContract psc, int year, int month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getCostOilAllocationCache().containsKey(cacheKey)) {
            return cache.getCostOilAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new CostOilAllocation();
        }

        CostOilAllocation allocation = new CostOilAllocation();

        double costOil = costOilController.computeCostOilCum(psc, year, month);
        double contractorLiftProceed = liftingBean.getContractorProceed(psc, year, month);
        double proceed = liftingBean.getTotalProceed(psc, year, month);

        costOil = Math.max(0, Math.min(proceed * psc.getCostRecoveryLimit(), costOil));
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = processCostOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
//        double costOilBfw = costOilController.computeCostOilBfw(lpsc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(costOil);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        cache.getCostOilAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public TaxOilAllocation processTaxOilAllocation(ProductionSharingContract psc, Integer year, Integer month) {
        CacheKey cacheKey = new CacheKey(psc, year, month);

        if (cache.getTaxOilAllocationCache().containsKey(cacheKey)) {
            return cache.getTaxOilAllocationCache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return new TaxOilAllocation();
        }

        TaxOilAllocation allocation = new TaxOilAllocation();

        double taxOil = taxBean.computeTaxOil(psc, year, month);
        double corpLiftProceed = liftingBean.getCorporationProceed(psc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = processTaxOilAllocation(psc, prevFp.getYear(), prevFp.getMonth());
        Allocation royAlloc = processRoyaltyAllocation(psc, year, month);//(this.psc, this.periodYear, this.periodMonth)

        allocation.setMonthlyCharge(taxOil);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setRoyalty(royAlloc.getReceived());
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        cache.getTaxOilAllocationCache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public List<LiftingSummary> getLiftingSummary(ProductionSharingContract psc, int year, int month) {
        List<LiftingSummary> liftingSummaryList = new ArrayList<>();

        if (psc != null && year != 0 && month != 0) {
            List<PscLifting> pscLiftings = liftingBean.find(psc, year, month);

            double proceed = 0;

            for (PscLifting lifting : pscLiftings) {

                proceed += lifting.getRevenue();

                LiftingSummary liftSumm = new LiftingSummary();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                String liftingDate = dateFormat.format(lifting.getLiftingDate());
                double corpProceed = lifting.getOwnLifting() * lifting.getPrice();
                double contProceed = lifting.getPartnerLifting() * lifting.getPrice();

                liftSumm.setLiftingDate(liftingDate);
                liftSumm.setLiftingVolume(lifting.getTotalLifting());
                liftSumm.setOsPrice(lifting.getPrice());
                liftSumm.setProceed(proceed);
                liftSumm.setCorporationProceed(corpProceed);
                liftSumm.setContractorProceed(contProceed);

                liftingSummaryList.add(liftSumm);
            }
        }
        return liftingSummaryList;
    }

}
