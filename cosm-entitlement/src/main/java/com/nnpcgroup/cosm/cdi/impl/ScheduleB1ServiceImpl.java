package com.nnpcgroup.cosm.cdi.impl;

import com.nnpcgroup.cosm.cdi.*;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;
import com.nnpcgroup.cosm.report.schb1.*;
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
    private RoyaltyService royaltyService;

    @Inject
    private CostOilService costOilService;

    @Inject
    private TaxOilService taxBean;

    @EJB
    private PscLiftingServices liftingBean;


    @Override
    public List<ProceedAllocation> computeProceedAllocation(ProductionSharingContract psc, int year, int month) {
        List<ProceedAllocation> proceedAllocationList = new ArrayList<>();

        ProceedAllocation royPa = new ProceedAllocation();
        Allocation royAlloc = royaltyService.computeRoyaltyAllocation(psc, year, month);
        royPa.setCategoryTitle("ROYALTY OIL");
        royPa.setMonthlyChargeBfw(royAlloc.getChargeBfw());
        royPa.setMonthlyCharge(royAlloc.getMonthlyCharge());
        royPa.setRecoverable(royAlloc.getRecoverable());
        royPa.setCorporationProceed(royAlloc.getReceived());
        royPa.setMonthlyChargeCfw(royAlloc.getChargeCfw());

        proceedAllocationList.add(royPa);

        ProceedAllocation coPa = new ProceedAllocation();
        Allocation costOilAlloc = costOilService.computeCostOilAllocation(psc, year, month);
        coPa.setCategoryTitle("COST OIL");
        coPa.setMonthlyChargeBfw(costOilAlloc.getChargeBfw());
        coPa.setMonthlyCharge(costOilAlloc.getMonthlyCharge());
        coPa.setRecoverable(costOilAlloc.getRecoverable());
        coPa.setContractorProceed(costOilAlloc.getReceived());
        coPa.setMonthlyChargeCfw(costOilAlloc.getChargeCfw());

        proceedAllocationList.add(coPa);

        ProceedAllocation toPa = new ProceedAllocation();
        Allocation taxOilAlloc = taxBean.computeTaxOilAllocation(psc, year, month);
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
    public List<LiftingSummary> getLiftingSummary(ProductionSharingContract psc, int year, int month) {
        List<LiftingSummary> liftingSummaryList = new ArrayList<>();

        if (psc != null && year != 0 && month != 0) {
            List<PscLifting> pscLiftings = liftingBean.find(psc, year, month);

            double proceed = 0;

            for (PscLifting lifting : pscLiftings) {

                proceed = lifting.getRevenue();

                LiftingSummary liftSumm = new LiftingSummary();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                String liftingDate = dateFormat.format(lifting.getLiftingDate());
                double corpProceed = lifting.getOwnProceed();
                double contProceed = lifting.getPartnerProceed();

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
