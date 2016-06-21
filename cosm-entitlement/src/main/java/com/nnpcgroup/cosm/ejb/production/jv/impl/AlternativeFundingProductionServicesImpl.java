/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.PriceBean;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionServices;
import com.nnpcgroup.cosm.entity.FiscalArrangementPK;
import com.nnpcgroup.cosm.entity.Price;
import com.nnpcgroup.cosm.entity.PricePK;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProduction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public abstract class AlternativeFundingProductionServicesImpl<T extends AlternativeFundingProduction, E extends AlternativeFundingContract> extends JvProductionServicesImpl<T, E> implements AlternativeFundingProductionServices<T, E> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionServicesImpl.class.getName());

    @EJB
    PriceBean priceBean;

    @EJB
    ContractServices contractBean;

    public AlternativeFundingProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T production) {
        T prod = getPreviousMonthProduction(production);
        Double openingStock = null;
        Double partnerOpeningStock = null;
        if (prod != null) {
            openingStock = prod.getClosingStock();
            partnerOpeningStock = prod.getPartnerOpeningStock();
            production.setOpeningStock(openingStock);
            production.setPartnerOpeningStock(partnerOpeningStock);
        } else {
            production.setOpeningStock(0.0);
            production.setPartnerOpeningStock(0.0);
        }

        LOG.log(Level.INFO, "Own Opening Stock=>{0} Partner Opening Stock=>{1} ", new Object[]{openingStock, partnerOpeningStock});

        return production;
    }

    @Override
    public T enrich(T production) {
        LOG.log(Level.INFO, "Enriching production {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeAlternativeFunding(
                                        computeEntitlement(
                                                computeOpeningStock(production)
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public T grossProductionChanged(T production) {
        LOG.log(Level.INFO, "Gross production changed");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeAlternativeFunding(
                                        computeEntitlement(
                                                computeOpeningStock(
                                                        overLiftReset(production)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private T computeCummulative(T production) {
        T prev = getPreviousMonthProduction(production);
        Double sharedOilCum = production.getSharedOil() != null ? production.getSharedOil() : new Double(0);
        Double carryOilCum = production.getCarryOil();
        Double CTECum = production.getCarryTaxExpenditure();
        Double CTRCum = production.getCarryTaxRelief();
        Double RCECum = production.getResidualCarryExpenditure();
        Double CCCACum = production.getCapitalCarryCostAmortized();
        Double CORCum = production.getCarryOilReceived();

        if (prev != null) {
            LOG.log(Level.INFO, "SharedOilCum = {0}, Previous Production = {1}, prev.getSharedOilCum() = {2}", new Object[]{sharedOilCum, prev, prev.getSharedOilCum()});

            sharedOilCum += prev.getSharedOilCum();
            carryOilCum += prev.getCarryOilCum();
            CTECum += prev.getCarryTaxExpenditureCum();
            CTRCum += prev.getCarryTaxReliefCum();
            RCECum += prev.getResidualCarryExpenditureCum();
            CCCACum += prev.getCapitalCarryCostAmortizedCum();
            CORCum += prev.getCarryOilReceivedCum();
        }

        production.setSharedOilCum(sharedOilCum);
        production.setCarryOilCum(carryOilCum);
        production.setCarryTaxExpenditureCum(CTECum);
        production.setCarryTaxReliefCum(CTRCum);
        production.setResidualCarryExpenditureCum(RCECum);
        production.setCapitalCarryCostAmortizedCum(CCCACum);
        production.setCarryOilReceivedCum(CORCum);

        return production;
    }

    @Override
    public T computeAlternativeFunding(T production) {
        return computeCummulative(
                computeSharedOil(
                        computeCarryOilReceived(
                                computeCarryOil(
                                        computeResidualCarryExpenditure(
                                                computeGuaranteedNotionalMargin(
                                                        computeCarryTaxRelief(
                                                                computeCarryTaxExpenditure(
                                                                        computeCapitalCarryCostAmortized(production)
                                                                )
                                                        )
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

    private boolean isSharedOilTerminalPeriodDue(T production) {
        ContractPK cPK = new ContractPK(production.getFiscalArrangementId(), production.getCrudeTypeCode());
        E afContract = (E) contractBean.find(cPK);//production.getContract();
        Double terminalPeriod = afContract.getTerminalPeriod();

        if (terminalPeriod == null) {
            return false;
        }

        Long sharedOilPeriod = getSharedOilPeriod(afContract);

        LOG.log(Level.INFO, "Shared Oil Received for {0} of {1} months...", new Object[]{sharedOilPeriod, terminalPeriod});

        return sharedOilPeriod >= terminalPeriod;
    }

    private boolean isSharedOilTerminalValueDue(T production) {
        T prev = getPreviousMonthProduction(production);
        Double sharedOilCum = new Double(0);

        if (prev != null) {
            sharedOilCum = prev.getSharedOilCum();
        }

        //E afContract = (E) production.getContract();
        ContractPK cPK = new ContractPK(production.getFiscalArrangementId(), production.getCrudeTypeCode());
        E afContract = (E) contractBean.find(cPK);
        
        Double terminalSharedOil = afContract.getTerminalSharedOil();

        if (terminalSharedOil == null) {
            return false;
        }
        LOG.log(Level.INFO, "Shared Oil value to date: {0} out of {1}", new Object[]{sharedOilCum, terminalSharedOil});
        return sharedOilCum >= terminalSharedOil;
    }

    private boolean isShareOilTerminate(T production) {
        return isSharedOilTerminalPeriodDue(production) || isSharedOilTerminalValueDue(production);
    }

    @Override
    public T computeSharedOil(T production) {
        if (isShareOilTerminate(production)) {
            LOG.log(Level.INFO, "Bypassing Shared Oil computation. Terminal condition reached.");
            return production;
        }

        Double sharedOil;
        Double ownEquity = production.getOwnShareEntitlement() != null ? production.getOwnShareEntitlement() : new Double(0);
        Double carryOil = production.getCarryOil() != null ? production.getCarryOil() : new Double(0);

        //E afContract = (E) production.getContract();
        ContractPK cPK = new ContractPK(production.getFiscalArrangementId(), production.getCrudeTypeCode());
        E afContract = (E) contractBean.find(cPK);
        Double sharedOilRatio = afContract.getSharedOilRatio();

        sharedOil = (ownEquity - carryOil) * sharedOilRatio * 0.01;
        production.setSharedOil(sharedOil);

        LOG.log(Level.INFO, "Shared Oil = ( NNPC Equity - Carry Oil ) * Shared Oil Ratio => ( {0} - {1} ) * {2} * 0.01 = {3}", new Object[]{ownEquity, carryOil, sharedOilRatio, sharedOil});

        return production;

    }

    @Override
    public T computeCarryOil(T production) {
        Double carryOil;
        Double RCE = production.getResidualCarryExpenditure() != null ? production.getResidualCarryExpenditure() : 0.0;
        Double nnpcEquity = production.getOwnShareEntitlement() != null ? production.getOwnShareEntitlement() : new Double(0);

        carryOil = Math.min(RCE, nnpcEquity);

        production.setCarryOil(carryOil);

        LOG.log(Level.INFO, "Carry Oil = MIN(RCE, NNPC EQUITY) => MIN({0}, {1}) = {2}", new Object[]{RCE, nnpcEquity, carryOil});

        return production;
    }

    @Override
    public T computeGuaranteedNotionalMargin(T production) {
        PricePK pricePK = new PricePK();
        pricePK.setPeriodMonth(production.getPeriodMonth());
        pricePK.setPeriodYear(production.getPeriodYear());

        Double GNM = null;// = 4.1465; //TODO:temporary placeholder

        Price price = priceBean.find(pricePK);
        if (price != null) {
            LOG.log(Level.INFO, "Realizable Price found {0}", price.getRealizablePrice());
            GNM = price.getRealizablePrice() * 0.12225;
        } else {
            LOG.log(Level.INFO, "Realizable Price NOT found {0}");
        }

        production.setGuaranteedNotionalMargin(GNM);
        LOG.log(Level.INFO, "Guaranteed National Margin (GNM)=>{0}", GNM);

        return production;
    }

    @Override
    public T computeResidualCarryExpenditure(T production) {
        Double RCE;
        Double CCCA = production.getCapitalCarryCostAmortized() != null ? production.getCapitalCarryCostAmortized() : 0.0;
        Double CTR = production.getCarryTaxRelief() != null ? production.getCarryTaxRelief() : 0.0;
        Double COR = production.getCarryOilReceived() != null ? production.getCarryOilReceived() : 0.0;
        Double IGNM = production.getGuaranteedNotionalMargin() != null ? production.getGuaranteedNotionalMargin() : 0.0;

        RCE = (Math.max(0, (CCCA - CTR - COR))) / IGNM;
        production.setResidualCarryExpenditure(RCE);

        LOG.log(Level.INFO, "RCE = MAX(0, CCCA - CTR - COR)/IGNM => MAX(0, ({0} - {1} - {2}))/{3} = {4}", new Object[]{CCCA, CTR, COR, IGNM, RCE});

        return production;
    }

    public T computeCarryOilReceived(T production) {
        T prev = getPreviousMonthProduction(production);
        Double COR;
        Double carryOil = 0.0;
        Double margin;

        if (prev != null) {
            carryOil = prev.getCarryOil() != null ? prev.getCarryOil() : 0.0;
        }
        margin = production.getGuaranteedNotionalMargin() != null ? production.getGuaranteedNotionalMargin() : 0.0;

        COR = carryOil * margin;
        production.setCarryOilReceived(COR);
        return production;
    }

    @Override
    public T computeCarryTaxRelief(T production) {
        Double CTR;
        Double CTE = production.getCarryTaxExpenditure();
        CTR = CTE * 0.85;
        production.setCarryTaxRelief(CTR);
        LOG.log(Level.INFO, "CTR = CTE * 85% => {0} * 0.85 = {1}", new Object[]{CTE, CTR});

        return production;
    }

    @Override
    public T computeCapitalCarryCostAmortized(T production) {
        Double CCCA;
        Double tangible = computeTangibleCost(production);
        Double intangible = computeIntangibleCost(production);
        CCCA = (tangible * 0.20) + intangible;
        production.setCapitalCarryCostAmortized(CCCA);

        LOG.log(Level.INFO, "Capital Carry Cost Armotized (CCCA) = Tangible * 20% + Intangible => {0} * 0.20 + {1} = {2}", new Object[]{tangible, intangible, CCCA});

        return production;
    }

    @Override
    public T computeCarryTaxExpenditure(T production) {
        Double CCCA;
        Double PIA;

        CCCA = production.getCapitalCarryCostAmortized();
        PIA = computePetroleumInvestmentAllowance(production);

        Double CTE = CCCA + PIA;
        production.setCarryTaxExpenditure(CTE);

        LOG.log(Level.INFO, "Carry Tax Expenditure (CTE) = CCCA + PIA => {0} + {1} = {2}", new Object[]{CCCA, PIA, CTE});

        return production;
    }

    public Double computePetroleumInvestmentAllowance(T production) {
        Double tangibleCost = computeTangibleCost(production);
        Double PIA = tangibleCost * 0.10;

        LOG.log(Level.INFO, "Tangible Cost = Tangible * 10% => {0} * 0.10 = {1}", new Object[]{tangibleCost, PIA});

        return PIA;
    }

    public Double computeCarryCapitalCost(T production) {
        Double tangibleCost = production.getTangibleCost();
        Double intangibleCost = production.getIntangibleCost();

        Double CCC = tangibleCost + intangibleCost;

        LOG.log(Level.INFO, "Carry Capital Cost (CCC) = Tangible + Intangible => {0} + {1} = {2}", new Object[]{tangibleCost, intangibleCost, CCC});

        return CCC;
    }

    private Double computeTangibleCost(T production) {

        LOG.log(Level.INFO, "Tangible Cost => {0}", new Object[]{production.getTangibleCost()});

        return production.getTangibleCost() != null ? production.getTangibleCost() : new Double(0);
    }

    private Double computeIntangibleCost(T production) {

        LOG.log(Level.INFO, "Intangible Cost => {0}", new Object[]{production.getIntangibleCost()});

        return production.getIntangibleCost() != null ? production.getIntangibleCost() : new Double(0);
    }

    @Override
    public Double computeCarryOilCum(E cs) {

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
    public Double computeSharedOilCum(E cs) {
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
    public Double computeResidualCarryExpenditureCum(E cs) {
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
    public Double computeCarryTaxReliefCum(E cs) {
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
    public Double computeCarryTaxExpenditureCum(E cs) {
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
    public Double computeCapitalCarryCostAmortizedCum(E cs) {
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
    public Long getSharedOilPeriod(E contract) {
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
                + "FROM AlternativeFundingProduction f  WHERE f.contract = :contract AND f.sharedOil != null", Long.class);
        query.setParameter("contract", contract);

        long sharedOilPeriod = query.getSingleResult();

        LOG.log(Level.INFO, "Shared Oil Received for {0} months...", sharedOilPeriod);

        return sharedOilPeriod;

    }
}
