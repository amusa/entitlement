package com.cosm.psc.infrastructure;

import com.cosm.psc.domain.shared.CacheUtil;
import com.cosm.psc.domain.model.Allocation;
import com.cosm.psc.domain.model.LiftingSummary;
import com.cosm.psc.domain.model.ProceedAllocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.lifting.Lifting;
import com.cosm.psc.domain.model.lifting.LiftingQueryRepository;
import com.cosm.psc.domain.service.*;

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

    @Inject
    private ProfitOilService profitOilService;

    @Inject
    private LiftingQueryRepository liftingQueryRepository ;


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
        Allocation corpProfOilAlloc = profitOilService.computeCorporationProfitOilAllocation(psc, year, month);
        corpPoPa.setCategoryTitle("CORP PROFIT OIL");
        corpPoPa.setMonthlyChargeBfw(corpProfOilAlloc.getChargeBfw());
        corpPoPa.setMonthlyCharge(corpProfOilAlloc.getMonthlyCharge());
        corpPoPa.setRecoverable(corpProfOilAlloc.getRecoverable());
        corpPoPa.setCorporationProceed(corpProfOilAlloc.getReceived());
        corpPoPa.setMonthlyChargeCfw(corpProfOilAlloc.getChargeCfw());

        proceedAllocationList.add(corpPoPa);

        ProceedAllocation contPoPa = new ProceedAllocation();
        Allocation contProfOilAlloc = profitOilService.computeContractorProfitOilAllocation(psc, year, month);
        contPoPa.setCategoryTitle("CONT PROFIT OIL");
        contPoPa.setMonthlyChargeBfw(contProfOilAlloc.getChargeBfw());
        contPoPa.setMonthlyCharge(contProfOilAlloc.getMonthlyCharge());
        contPoPa.setRecoverable(contProfOilAlloc.getRecoverable());
        contPoPa.setContractorProceed(contProfOilAlloc.getReceived());
        contPoPa.setMonthlyChargeCfw(contProfOilAlloc.getChargeCfw());

        proceedAllocationList.add(contPoPa);

        return proceedAllocationList;

    }

    @Override
    public List<LiftingSummary> getLiftingSummary(ProductionSharingContract psc, int year, int month) {
        List<LiftingSummary> liftingSummaryList = new ArrayList<>();

        if (psc != null && year != 0 && month != 0) {
            List<Lifting> pscLiftings = liftingQueryRepository.find(psc, year, month);

            double proceed = 0;

            for (Lifting lifting : pscLiftings) {

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
