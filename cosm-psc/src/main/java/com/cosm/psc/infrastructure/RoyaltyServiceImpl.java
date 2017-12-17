package com.cosm.psc.infrastructure;

import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.domain.service.internal.FiscalPeriod;
import com.cosm.common.domain.util.DateUtil;
import com.cosm.psc.domain.model.RoyaltyAllocation;
import com.cosm.psc.domain.shared.CacheKey;
import com.cosm.psc.domain.shared.CacheUtil;
import com.cosm.psc.domain.model.Allocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.cost.CostQueryRepository;
import com.cosm.psc.domain.model.lifting.LiftingQueryRepository;
import com.cosm.psc.domain.model.production.ProductionQueryRepository;
import com.cosm.psc.domain.service.RoyaltyService;

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

    @Inject
    private CostQueryRepository costQueryRepository;

    @Inject
    private LiftingQueryRepository liftingQueryRepository ;

//    @Inject
//    private CrudePriceBean priceBean;

    @EJB
    private ProductionQueryRepository productionQueryRepository;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    private CacheUtil cache;

    @Override
    public double computeRoyalty(ProductionSharingContract psc, int year, int month) {
        double royalty, royRate, grossProd;

        //royRate = getRoyaltyRate(psc);
        grossProd = productionQueryRepository.getGrossProduction(psc, year, month);
        int days = DateUtil.daysOfMonth(year, month);
        Double dailyProd = grossProd / days;
        royRate = computeRoyaltyRate(dailyProd);
        double weightedAvePrice = liftingQueryRepository.computeWeightedAvePrice(psc, year, month);

//        grossProdCum = productionQueryRepository.getGrossProductionToDate(psc, year, month);//cummulative production

        double consessionRental = 0;

        if (productionQueryRepository.isFirstProductionOfYear(psc, year, month)) {
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

        if (!costQueryRepository.fiscalPeriodExists(psc, fp)) {
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

        if (!costQueryRepository.fiscalPeriodExists(psc, year, month)) {
            return new RoyaltyAllocation();
        }

        RoyaltyAllocation allocation = new RoyaltyAllocation();

        double royalty = computeRoyalty(psc, year, month);
        double corpLiftProceed = liftingQueryRepository.getCorporationProceed(psc, year, month);
        //handling cash payment
        double cashPayment = liftingQueryRepository.getCashPayment(psc, year, month);
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
}
