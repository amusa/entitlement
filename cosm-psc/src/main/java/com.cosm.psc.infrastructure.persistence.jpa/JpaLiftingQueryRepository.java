/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.infrastructure.persistence.jpa;


import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.psc.domain.model.account.ProductionSharingContract;
import com.cosm.psc.domain.model.lifting.Lifting;
import com.cosm.psc.domain.model.lifting.LiftingQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Date;
import java.util.List;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaLiftingQueryRepository extends JpaQueryRepository<Lifting> implements LiftingQueryRepository {

    @PersistenceContext(unitName = "LiftingPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JpaLiftingQueryRepository() {
        super(Lifting.class);
    }


    @Override
    public List<Lifting> find(Date fromDate, Date toDate) {
        java.sql.Date fDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date tDate = new java.sql.Date(toDate.getTime());


        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<Lifting> Lifting_ = m.entity(entityClass);

        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(entityClass);
        Root<Lifting> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.between(e.get("liftingDate"), fDate, tDate)
            );

            Query query = getEntityManager().createQuery(cq);

            liftings = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return liftings;
    }


    @Override
    public List<Lifting> find(ProductionSharingContract psc, Date fromDate, Date toDate) {
        java.sql.Date fDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date tDate = new java.sql.Date(toDate.getTime());

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<Lifting> Lifting_ = m.entity(entityClass);

        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(entityClass);
        Root<Lifting> e = cq.from(entityClass);

        Predicate pscPredicate = cb.equal(e.get("psc"), psc);
        Predicate btwPredicate = cb.between(e.get("liftingDate"), fDate, tDate);
        Predicate and = cb.and(pscPredicate, btwPredicate);


        try {

            cq.where(and);

            liftings = getEntityManager().createQuery(cq)
                    .getResultList();


//            cq.select(e).where(
//                    cb.and(cb.equal(e.get("psc"), psc),
//                            cb.between(e.get("liftingDate"), fDate, tDate)
//                    )
//            );
//
//
//            Query query = getEntityManager().createQuery(cq);
//
//            liftings = query.getResultList();


        } catch (NoResultException nre) {
            return null;
        }

        return liftings;
    }


    @Override
    public List<Lifting> find(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<Lifting> Lifting_ = m.entity(entityClass);

        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(entityClass);
        Root<Lifting> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(
                            cb.equal(e.get("psc"), psc),
                            cb.equal(cb.function("year", Integer.class, e.get("liftingDate")), year),
                            cb.equal(cb.function("month", Integer.class, e.get("liftingDate")), month)
                    )
            );

            Query query = getEntityManager().createQuery(cq);

            liftings = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return liftings;

    }

    @Override
    public double computeWeightedAvePrice(ProductionSharingContract psc, int year, int month) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Path<Number> ownLiftPath = liftingRoot.get("ownLifting");
        Path<Number> partnerLiftPath = liftingRoot.get("partnerLifting");
        Path<Number> pricePath = liftingRoot.get("price");
        Expression<Number> zero = cb.literal(0.0);

        //ignore null
        Expression<Number> totalLift =
                cb.sum(cb.<Number>selectCase().when(ownLiftPath.isNull(), zero).otherwise(ownLiftPath),
                        cb.<Number>selectCase().when(partnerLiftPath.isNull(), zero).otherwise(partnerLiftPath));

        Expression<Number> totalRevenue = cb.sum(cb.prod(pricePath, totalLift));
        Expression<Number> liftingSum = cb.sum(totalLift);

        Selection<Number> wapQuot = cb.quot(totalRevenue, liftingSum);
        wapQuot.alias("wap");

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(wapQuot)
                .where(predicate);

        Number wap;//weighted average price
        try {
            wap = getEntityManager().createQuery(cq).getSingleResult();
            return wap.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getCorporationProceed(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingRoot.get("ownLifting"));
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(sum.alias("corpProceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = getEntityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getContractorProceed(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingRoot.<Double>get("partnerLifting"));
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(sum.alias("contractorProceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = getEntityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getMonthlyIncome(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Path<Number> ownLiftPath = liftingRoot.get("ownLifting");
        Path<Number> partnerLiftPath = liftingRoot.get("partnerLifting");
        Path<Number> pricePath = liftingRoot.get("price");
        Expression<Number> zero = cb.literal(0.0);

        //ignore null
        Expression<Number> totalLift =
                cb.sum(cb.<Number>selectCase().when(ownLiftPath.isNull(), zero).otherwise(ownLiftPath),
                        cb.<Number>selectCase().when(partnerLiftPath.isNull(), zero).otherwise(partnerLiftPath));
        Expression<Number> totalRevenue = cb.sum(cb.prod(pricePath, totalLift));

//

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(totalRevenue.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = getEntityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getProceedToDate(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Path<Number> ownLiftPath = liftingRoot.get("ownLifting");
        Path<Number> partnerLiftPath = liftingRoot.get("partnerLifting");
        Path<Number> pricePath = liftingRoot.get("price");
        Expression<Number> zero = cb.literal(0.0);

        //ignore null
        Expression<Number> totalLift =
                cb.sum(cb.<Number>selectCase().when(ownLiftPath.isNull(), zero).otherwise(ownLiftPath),
                        cb.<Number>selectCase().when(partnerLiftPath.isNull(), zero).otherwise(partnerLiftPath));
        Expression<Number> totalRevenue = cb.sum(cb.prod(pricePath, totalLift));


        Predicate basePredicate =
                cb.equal(liftingRoot.get("psc"), psc)
        ;

        Predicate curYrPredicate = cb.and(
                cb.equal(liftingRoot.get("periodYear"), year),
                cb.lessThanOrEqualTo(liftingRoot.get("periodMonth"), month)
        );

        Predicate prevYrPredicate =
                cb.lessThan(liftingRoot.get("periodYear"), year);

        Predicate yearPredicate = cb.or(curYrPredicate, prevYrPredicate);

        Predicate predicate = cb.and(basePredicate, yearPredicate);

        cq.select(totalRevenue.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = getEntityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getGrossIncome(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Expression<Double> liftingSum = cb.sum(liftingRoot.<Double>get("ownLifting"), liftingRoot.<Double>get("partnerLifting"));
        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingSum);
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.lessThanOrEqualTo(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(sum.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = getEntityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double getCashPayment(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(entityClass);

        Expression<Double> cashSum = cb.sum(liftingRoot.<Double>get("cashPayment"));

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("psc"), psc),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), year),
                cb.lessThanOrEqualTo(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), month)
        );

        cq.select(cashSum.alias("cashPayment"))
                .where(predicate);

        Number cashPayment;
        try {
            cashPayment = getEntityManager().createQuery(cq).getSingleResult();
            return cashPayment.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

}
