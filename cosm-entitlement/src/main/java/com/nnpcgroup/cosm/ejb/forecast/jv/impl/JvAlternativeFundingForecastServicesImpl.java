/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvAlternativeFundingForecastServices;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 */
@Dependent
public abstract class JvAlternativeFundingForecastServicesImpl<T extends AlternativeFundingForecast> extends JvForecastServicesImpl<T> implements JvAlternativeFundingForecastServices<T> {

    private static final Logger LOG = Logger.getLogger(JvAlternativeFundingForecastServicesImpl.class.getName());
    private static final long serialVersionUID = -5826414842990437262L;

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    public JvAlternativeFundingForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOG.info("returning entityManager...");

        return em;
    }

    @Override
    public T computeOpeningStock(T forecast) {
        T prev = getPreviousMonthProduction(forecast);
        Double prevClosingStock = null;
        Double partnerPrevClosingStock = null;
        if (prev != null) {
            prevClosingStock = prev.getClosingStock();
            partnerPrevClosingStock = prev.getPartnerClosingStock();
            forecast.setOpeningStock(prevClosingStock);
            forecast.setPartnerOpeningStock(partnerPrevClosingStock);
        } else {
            LOG.log(Level.INFO, "Previous forecast {0}.", new Object[]{prev});
            forecast.setOpeningStock(0.0);
            forecast.setPartnerOpeningStock(0.0);
        }

        LOG.log(Level.INFO, "Own Opening Stock=>{0} Partner Opening Stock=>{1} ", new Object[]{prevClosingStock, partnerPrevClosingStock});

        return forecast;
    }

    @Override
    public T openingStockChanged(T forecast) {
        LOG.log(Level.INFO, "Opening Stock changed {0}...", forecast);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(forecast)
                )
        );
    }

    @Override
    public T computeLifting(T forecast) {
        Double liftableVolume, partnerLiftableVolume;
        Integer cargoes, partnerCargoes;
        Double availability = ((Forecast) forecast).getAvailability();
        Double partnerAvailability = ((Forecast) forecast).getPartnerAvailability();

        cargoes = (int) (availability / 950000.0);
        partnerCargoes = (int) (partnerAvailability / 950000.0);
        liftableVolume = cargoes * 950000.0;
        partnerLiftableVolume = partnerCargoes * 950000.0;

        ((Forecast) forecast).setCargos(cargoes);
        ((Forecast) forecast).setLifting(liftableVolume);
        ((Forecast) forecast).setPartnerCargos(partnerCargoes);
        ((Forecast) forecast).setPartnerLifting(partnerLiftableVolume);

        LOG.log(Level.INFO, "Own Liftable Vol=>{0}, Own Cargoes=>{1} : Partner Liftable Vol=>{2}, Partner Cargoes=>{3}", new Object[]{liftableVolume, cargoes, partnerLiftableVolume, partnerCargoes});

        return forecast;
    }

    @Override
    public T enrich(T production) {
        LOG.log(Level.INFO, "Enriching forecast {0}...", production);
        return computeCummulative(
                computeClosingStock(
                        computeLifting(
                                computeAvailability(
                                        computeAlternativeFunding(
                                                computeEntitlement(
                                                        computeGrossProduction(
                                                                computeOpeningStock(production)
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private T computeCummulative(T forecast) {
        T prev = getPreviousMonthProduction(forecast);
        Double sharedOilCum = forecast.getSharedOil() != null ? forecast.getSharedOil() : new Double(0);
        Double carryOilCum = forecast.getCarryOil();
        Double CTECum = forecast.getCarryTaxExpenditure();
        Double CTRCum = forecast.getCarryTaxRelief();
        Double RCECum = forecast.getResidualCarryExpenditure();
        Double CCCACum = forecast.getCapitalCarryCostAmortized();

        if (prev != null) {
            LOG.log(Level.INFO, "SharedOilCum = {0}, Previous Forecast = {1}, prev.getSharedOilCum() = {2}", new Object[]{sharedOilCum, prev, prev.getSharedOilCum()});

            sharedOilCum += prev.getSharedOilCum();
            carryOilCum += prev.getCarryOilCum();
            CTECum += prev.getCarryTaxExpenditureCum();
            CTRCum += prev.getCarryTaxReliefCum();
            RCECum += prev.getResidualCarryExpenditureCum();
            CCCACum += prev.getCapitalCarryCostAmortizedCum();
        }

        forecast.setSharedOilCum(sharedOilCum);
        forecast.setCarryOilCum(carryOilCum);
        forecast.setCarryTaxExpenditureCum(CTECum);
        forecast.setCarryTaxReliefCum(CTRCum);
        forecast.setResidualCarryExpenditureCum(RCECum);
        forecast.setCapitalCarryCostAmortizedCum(CCCACum);

        return forecast;
    }

    public T computeAlternativeFunding(T production) {
        return computeSharedOil(
                computeCarryOil(
                        computeGuaranteedNotionalMargin(
                                computeResidualCarryExpenditure(
                                        computeCarryTaxRelief(
                                                computeCarryTaxExpenditure(
                                                        computeCapitalCarryCostAmortized(production)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public T computeAvailability(T production) {
        Double availability, partnerAvailability;
        Double ownEntitlement = production.getOwnShareEntitlement();
        Double partnerEntitlement = production.getPartnerShareEntitlement();
        Double openingStock = production.getOpeningStock();
        Double partnerOpeningStock = production.getPartnerOpeningStock();
        Double sharedOil = production.getSharedOil() != null ? production.getSharedOil() : new Double(0);
        Double carryOil = production.getCarryOil() != null ? production.getCarryOil() : new Double(0);
        Double carrySharedOil = sharedOil + carryOil;

        availability = ownEntitlement + openingStock - carrySharedOil;
        partnerAvailability = partnerEntitlement + partnerOpeningStock + carrySharedOil;

        production.setAvailability(availability);
        production.setPartnerAvailability(partnerAvailability);

        LOG.log(Level.INFO, "Own Availability=>{0} : Partner Availability=>{1}", new Object[]{availability, partnerAvailability});

        return production;
    }

    private boolean isSharedOilTerminalPeriodDue(T forecast) {
        Contract contract = forecast.getContract();
        assert (contract instanceof AlternativeFundingContract);
        AlternativeFundingContract afContract = (AlternativeFundingContract) contract;

        Double terminalPeriod = afContract.getTerminalPeriod();

        if (terminalPeriod == null) {
            return false;
        }

        Long sharedOilPeriod = getSharedOilPeriod(afContract);

        LOG.log(Level.INFO, "Shared Oil Received for {0} of {1} months...", new Object[]{sharedOilPeriod, terminalPeriod});

        return sharedOilPeriod >= terminalPeriod;
    }

    private boolean isSharedOilTerminalValueDue(T forecast) {
        T prev = getPreviousMonthProduction(forecast);
        Double sharedOilCum = new Double(0);

        if (prev != null) {
            sharedOilCum = prev.getSharedOilCum();
        }

        Contract contract = forecast.getContract();
        assert (contract instanceof AlternativeFundingContract);
        AlternativeFundingContract afContract = (AlternativeFundingContract) contract;

        Double terminalSharedOil = afContract.getTerminalSharedOil();

        if (terminalSharedOil == null) {
            return false;
        }
        LOG.log(Level.INFO, "Shared Oil value to date: {0} out of {1}", new Object[]{sharedOilCum, terminalSharedOil});
        return sharedOilCum >= terminalSharedOil;
    }

    private boolean isShareOilTerminate(T forecast) {
        return isSharedOilTerminalPeriodDue(forecast) || isSharedOilTerminalValueDue(forecast);
    }

    @Override
    public T computeSharedOil(T forecast) {
        if (isShareOilTerminate(forecast)) {
            LOG.log(Level.INFO, "Bypassing Shared Oil computation. Terminal condition reached.");
            return forecast;
        }

        Double sharedOil;
        Double ownEquity = forecast.getOwnShareEntitlement();
        Double carryOil = forecast.getCarryOil();

        Contract contract = forecast.getContract();
        assert (contract instanceof AlternativeFundingContract);
        AlternativeFundingContract afContract = (AlternativeFundingContract) contract;
        Double sharedOilRatio = afContract.getSharedOilRatio();

        sharedOil = (ownEquity - carryOil) * sharedOilRatio * 0.01;
        forecast.setSharedOil(sharedOil);

        LOG.log(Level.INFO, "Shared Oil = ( NNPC Equity - Carry Oil ) * Shared Oil Ratio => ( {0} - {1} ) * {2} * 0.01 = {3}", new Object[]{ownEquity, carryOil, sharedOilRatio, sharedOil});

        return forecast;

    }

    @Override
    public T computeCarryOil(T forecast) {
        Double carryOil;
        Double RCE = forecast.getResidualCarryExpenditure();
        Double IGNM = forecast.getGuaranteedNotionalMargin();

        carryOil = RCE / IGNM;

        forecast.setCarryOil(carryOil);

        LOG.log(Level.INFO, "Carry Oil = RCE / IGNM => {0} / {1} = {2}", new Object[]{RCE, IGNM, carryOil});

        return forecast;
    }

    @Override
    public T computeGuaranteedNotionalMargin(T forecast) {
        Double GNM = 4.1465; //TODO:temporary placeholder
        forecast.setGuaranteedNotionalMargin(GNM);
        LOG.log(Level.INFO, "Guaranteed National Margin (GNM)=>{0}", GNM);

        return forecast;
    }

    @Override
    public T computeResidualCarryExpenditure(T forecast) {
        Double RCE;
        Double CTE = forecast.getCarryTaxExpenditure();
        Double CTR = forecast.getCarryTaxRelief();

        RCE = CTE - CTR;
        forecast.setResidualCarryExpenditure(RCE);

        LOG.log(Level.INFO, "RCE = CTE - CTR => {0} - {1} = {2}", new Object[]{CTE, CTR, RCE});

        return forecast;
    }

    @Override
    public T computeCarryTaxRelief(T forecast) {
        Double CTR;
        Double CTE = forecast.getCarryTaxExpenditure();
        CTR = CTE * 0.85;
        forecast.setCarryTaxRelief(CTR);
        LOG.log(Level.INFO, "CTR = CTE * 85% => {0} * 0.85 = {1}", new Object[]{CTE, CTR});

        return forecast;
    }

    @Override
    public T computeCapitalCarryCostAmortized(T forecast) {
        Double CCCA;
        Double tangible = computeTangibleCost(forecast);
        Double intangible = computeIntangibleCost(forecast);
        CCCA = (tangible * 0.20) + intangible;
        forecast.setCapitalCarryCostAmortized(CCCA);

        LOG.log(Level.INFO, "Capital Carry Cost Armotized (CCCA) = Tangible * 20% + Intangible => {0} * 0.20 + {1} = {2}", new Object[]{tangible, intangible, CCCA});

        return forecast;
    }

    @Override
    public T computeCarryTaxExpenditure(T forecast) {
        Double CCCA;
        Double PIA;

        CCCA = forecast.getCapitalCarryCostAmortized();
        PIA = computePetroleumInvestmentAllowance(forecast);

        Double CTE = CCCA + PIA;
        forecast.setCarryTaxExpenditure(CTE);

        LOG.log(Level.INFO, "Carry Tax Expenditure (CTE) = CCCA + PIA => {0} + {1} = {2}", new Object[]{CCCA, PIA, CTE});

        return forecast;
    }

    public Double computePetroleumInvestmentAllowance(T forecast) {
        Double tangibleCost = computeTangibleCost(forecast);
        Double PIA = tangibleCost * 0.10;

        LOG.log(Level.INFO, "Tangible Cost = Tangible * 10% => {0} * 0.10 = {1}", new Object[]{tangibleCost, PIA});

        return PIA;
    }

    public Double computeCarryCapitalCost(T forecast) {
        Double tangibleCost = forecast.getTangibleCost();
        Double intangibleCost = forecast.getIntangibleCost();

        Double CCC = tangibleCost + intangibleCost;

        LOG.log(Level.INFO, "Carry Capital Cost (CCC) = Tangible + Intangible => {0} + {1} = {2}", new Object[]{tangibleCost, intangibleCost, CCC});

        return CCC;
    }

    private Double computeTangibleCost(T forecast) {

        LOG.log(Level.INFO, "Tangible Cost => {0}", new Object[]{forecast.getTangibleCost()});

        return forecast.getTangibleCost() != null ? forecast.getTangibleCost() : new Double(0);
    }

    private Double computeIntangibleCost(T forecast) {

        LOG.log(Level.INFO, "Intangible Cost => {0}", new Object[]{forecast.getIntangibleCost()});

        return forecast.getIntangibleCost() != null ? forecast.getIntangibleCost() : new Double(0);
    }

    @Override
    public T findByContractPeriod(int year, int month, Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T production;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            production = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;
    }

    @Override
    public Double computeCarryOilCum(Contract cs) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("carryOil"));
        q.select(sum.alias("carryOil"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        // OR q.select(cb.sum(t.<Double>get("carryOil")).alias("D"));
        return getEntityManager().createQuery(q).getSingleResult();

    }

    @Override
    public Double computeSharedOilCum(Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("sharedOil"));
        q.select(sum.alias("sharedOil"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        return getEntityManager().createQuery(q).getSingleResult();
    }

    @Override
    public Double computeResidualCarryExpenditureCum(Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("residualCarryExpenditure"));
        q.select(sum.alias("residualCarryExpenditure"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        return getEntityManager().createQuery(q).getSingleResult();
    }

    @Override
    public Double computeCarryTaxReliefCum(Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("carryTaxRelief"));
        q.select(sum.alias("carryTaxRelief"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        return getEntityManager().createQuery(q).getSingleResult();
    }

    @Override
    public Double computeCarryTaxExpenditureCum(Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("carryTaxExpenditure"));
        q.select(sum.alias("carryTaxExpenditure"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        return getEntityManager().createQuery(q).getSingleResult();
    }

    @Override
    public Double computeCapitalCarryCostAmortizedCum(Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> q = cb.createQuery(Double.class);
        Root<T> t = q.from(entityClass);

        Expression<Double> sum = cb.sum(t.<Double>get("capitalCarryCostAmortized"));
        q.select(sum.alias("capitalCarryCostAmortized"))
                .where(
                        cb.equal(t.get("contract"), cs)
                );

        return getEntityManager().createQuery(q).getSingleResult();
    }

    //@Override
    public Long getSharedOilPeriod(AlternativeFundingContract contract) {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<Long> q = cb.createQuery(Long.class);
//        Root<T> t = q.from(entityClass);
//
//        q.select(cb.count(q.from(entityClass)));
////        q.where(
////                cb.and(
////                        cb.equal(t.get("contract"), contract),
////                        cb.notEqual(t.get("sharedOil"), "")
////                )
////        );
//        q.where(
//                cb.equal(t.get("contract"), contract)
//        );
//
//        Long sharedOilPeriod = getEntityManager().createQuery(q).getSingleResult();

        LOG.log(Level.INFO, "Entity type is {0}...", entityClass);

        TypedQuery<Long> query = getEntityManager().createQuery(
                "SELECT COUNT(f) "
                + "FROM AlternativeFundingForecast f  WHERE f.contract = :contract AND f.sharedOil != null", Long.class);
        query.setParameter("contract", contract);

        long sharedOilPeriod = query.getSingleResult();

        LOG.log(Level.INFO, "Shared Oil Received for {0} months...", sharedOilPeriod);

        return sharedOilPeriod;

    }
}
