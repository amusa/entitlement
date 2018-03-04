package com.cosm.psc.entitlement.profitoil.infrastructure.cdi;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.util.CacheKey;
import com.cosm.common.util.CacheUtil;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilAllocation;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilService;

/**
 * Created by Ayemi on 20/02/2018.
 */
@Dependent
public class DefaultProfitOilService implements ProfitOilService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CacheUtil<CacheKey, Double> rFactorCache = new CacheUtil<>("rFactor", 100);
	private CacheUtil<CacheKey, Allocation> corporationProfitOilCache = new CacheUtil<>("corporationProfitOil", 100);
	private CacheUtil<CacheKey, Allocation> contractorProfitOilCache = new CacheUtil<>("contractorProfitOil", 100);
	private CacheUtil<CacheKey, Double> profitOilCache = new CacheUtil<>("profitOil", 100);
	
	
	
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
    public double computeRFactor(ProductionSharingContractId pscId, int year, int month) {
        CacheKey cacheKey = new CacheKey(pscId, year, month);

        if (rFactorCache.cache().containsKey(cacheKey)) {
            return rFactorCache.cache().get(cacheKey);
        }

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        double costOil = costOilService.computeCumMonthlyCharge(pscId, prevFp.getYear(), prevFp.getMonth());
        double profitOil = computeCumContractorMonthlyCharge(pscId, prevFp.getYear(), prevFp.getMonth());
        double cost = prodCostBean.getCostToDate(pscId, prevFp.getYear(), prevFp.getMonth());

        double rfactor = (cost != 0) ? (costOil + profitOil) / cost : 0;

        rFactorCache.cache().put(cacheKey, rfactor);

        return rfactor;
    }

    @Override
    public Allocation computeCorporationProfitOilAllocation(ProductionSharingContractId pscId, int year, int month) {
        CacheKey cacheKey = new CacheKey(pscId, year, month);

        if (corporationProfitOilCache.cache().containsKey(cacheKey)) {
            return corporationProfitOilCache.cache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(pscId, year, month)) {
            return new ProfitOilAllocation();
        }

        ProfitOilAllocation allocation = new ProfitOilAllocation();

        double profitOil = computeProfitOil(pscId, year, month);
        double rfactor = computeRFactor(pscId, year, month);
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

        corporationProfitOilCache.cache().put(cacheKey, allocation);

        return allocation;
    }

    @Override
    public Allocation computeContractorProfitOilAllocation(ProductionSharingContractId pscId, int year, int month) {
        CacheKey cacheKey = new CacheKey(pscId, year, month);

        if (contractorProfitOilCache.cache().containsKey(cacheKey)) {
            return contractorProfitOilCache.cache().get(cacheKey);
        }

        if (!prodCostBean.fiscalPeriodExists(pscId, year, month)) {
            return new ProfitOilAllocation();
        }

        ProfitOilAllocation allocation = new ProfitOilAllocation();

        double profitOil = computeProfitOil(pscId, year, month);
        double rfactor = computeRFactor(pscId, year, month);
        double profitRate = getProfitRate(rfactor);
        double contractorLiftProceed = liftingBean.getContractorProceed(pscId, year, month);
        double profitOilCharge = profitOil * profitRate;
        double costOilReceived = costOilService.computeReceived(pscId, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = computeContractorProfitOilAllocation(pscId, prevFp.getYear(), prevFp.getMonth());
        allocation.setMonthlyCharge(profitOilCharge);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setDeductibles(costOilReceived);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());
        allocation.setPrevCumMonthlyCharge(prevAlloc.getCumMonthlyCharge());

        contractorProfitOilCache.cache().put(cacheKey, allocation);

        return allocation;
    }


    @Override
    public double computeProfitOil(ProductionSharingContractId pscId, int year, int month) {
        CacheKey cacheKey = new CacheKey(pscId, year, month);

        if (profitOilCache.cache().containsKey(cacheKey)) {
            return profitOilCache.cache().get(cacheKey);
        }

        double currentProfitOil = computeCurrentProfitOil(pscId, year, month);
        double cummulativeProfitOil = computeCummulativeProfitOil(pscId, year, month);
        double profitOil = Math.max(0, currentProfitOil - cummulativeProfitOil);

        profitOilCache.cache().put(cacheKey, profitOil);

        return profitOil;
    }

    public double computeCumProfitOil(ProductionSharingContractId pscId, int year, int month) {
        if (!prodCostBean.fiscalPeriodExists(psc, year, month)) {
            return 0;
        }

        double profitOil = computeProfitOil(pscId, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        return profitOil + computeCumProfitOil(pscId, prevFp.getYear(), prevFp.getMonth());

    }

    @Override
    public double computeContractorMonthlyCharge(ProductionSharingContractId pscId, int year, int month) {
        return computeContractorProfitOilAllocation(psc, year, month).getMonthlyCharge();
    }

    @Override
    public double computeCumContractorMonthlyCharge(ProductionSharingContractId pscId, int year, int month) {
        return computeContractorProfitOilAllocation(pscId, year, month).getCumMonthlyCharge();
    }

    private double computeCurrentProfitOil(ProductionSharingContractId pscId, int year, int month) {
        double proceed = liftingBean.getMonthlyIncome(pscId, year, month);
        double royalty = royaltyService.computeMonthlyCharge(pscId, year, month);
        double costOil = costOilService.computeMonthlyCharge(pscId, year, month);
        double taxOil = taxOilService.computeMonthlyCharge(pscId, year, month);

        return proceed - royalty - costOil - taxOil;
    }


    private double computeCummulativeProfitOil(ProductionSharingContractId pscId, int year, int month) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        double prevProceedCum = liftingBean.getMonthlyIncome(pscId, prevFp.getYear(), prevFp.getMonth());
        double prevRoyaltyCum = royaltyService.computeCumMonthlyCharge(pscId, prevFp.getYear(), prevFp.getMonth());
        double prevCostOilCum = costOilService.computeCumMonthlyCharge(pscId, prevFp.getYear(), prevFp.getMonth());
        double prevTaxOilCum = taxOilService.computeCumMonthlyCharge(pscId, prevFp.getYear(), prevFp.getMonth());
        double prevProfitOilCum = computeCumProfitOil(pscId, prevFp.getYear(), prevFp.getMonth());

        return Math.max(0, prevProfitOilCum + prevTaxOilCum + prevCostOilCum + prevRoyaltyCum - prevProceedCum);
    }

    private double getProfitRate(double rfactor) {
        if (rfactor < 1.2) {
            return 0.7;
        } else if (rfactor > 2.5) {
            return 0.25;
        } else if (rfactor >= 1.2 && rfactor <= 2.5) {
            return 0.25 + ((2.5 - rfactor) / (2.5 - 1.2)) * 0.45;
        }
        return 0;
    }
}
