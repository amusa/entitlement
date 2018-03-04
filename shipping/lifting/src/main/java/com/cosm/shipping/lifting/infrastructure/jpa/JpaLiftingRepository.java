/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.shipping.lifting.infrastructure.jpa;

import java.util.Date;

import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.shipping.lifting.domain.model.Lifting;
import com.cosm.shipping.lifting.domain.model.LiftingId;
import com.cosm.shipping.lifting.domain.model.LiftingRepository;

/**
 * @author 18359
 */
@ApplicationScoped
public class JpaLiftingRepository implements LiftingRepository {

	@PersistenceContext 
    private EntityManager em;
	
		
	protected EntityManager entityManager() {		
		return em;
	}
    	  
    
    @Override
    public List<Lifting> liftingsBetween(Date fromDate, Date toDate) {
        java.sql.Date fDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date tDate = new java.sql.Date(toDate.getTime());

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
                
        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(Lifting.class);
        Root<Lifting> e = cq.from(Lifting.class);
        
        Predicate btwPredicate = cb.between(e.get("liftingDate"), fDate, tDate);
        
        try {

            cq.where(btwPredicate);

            liftings = entityManager().createQuery(cq)
                    .getResultList();

        } catch (NoResultException nre) {
            return null;
        }

        return liftings;
    }

    @Override
    public List<Lifting> liftingsOfContractBetween(Date fromDate, Date toDate, ProductionSharingContractId pscId) {
        java.sql.Date fDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date tDate = new java.sql.Date(toDate.getTime());

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        Metamodel m = entityManager().getMetamodel();
        EntityType<Lifting> Lifting_ = m.entity(Lifting.class);

        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(Lifting.class);
        Root<Lifting> e = cq.from(Lifting.class);

        Predicate pscPredicate = cb.equal(e.get("pscId"), pscId);
        Predicate btwPredicate = cb.between(e.get("liftingDate"), fDate, tDate);
        Predicate and = cb.and(pscPredicate, btwPredicate);


        try {

            cq.where(and);

            liftings = entityManager().createQuery(cq)
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
    public List<Lifting> liftingsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        Metamodel m = entityManager().getMetamodel();
        EntityType<Lifting> Lifting_ = m.entity(Lifting.class);

        List<Lifting> liftings;

        CriteriaQuery<Lifting> cq = cb.createQuery(Lifting.class);
        Root<Lifting> e = cq.from(Lifting.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("pscId"), pscId),
                            cb.equal(cb.function("year", Integer.class, e.get("liftingDate")), fiscalPeriod.getYear()),
                            cb.equal(cb.function("month", Integer.class, e.get("liftingDate")), fiscalPeriod.getMonth())
                    )
            );

            Query query = entityManager().createQuery(cq);

            liftings = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return liftings;

    }

    @Override
    public double weightedAveragePrice(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

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
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(wapQuot)
                .where(predicate);

        Number wap;//weighted average price
        try {
            wap = entityManager().createQuery(cq).getSingleResult();
            return wap.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double corporationProceed(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingRoot.get("ownLifting"));
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(sum.alias("corpProceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = entityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double contractorProceed(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingRoot.<Double>get("partnerLifting"));
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(sum.alias("contractorProceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = entityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double monthlyIncome(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

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
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.equal(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(totalRevenue.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = entityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double proceedToDate(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

        Path<Number> ownLiftPath = liftingRoot.get("ownLifting");
        Path<Number> partnerLiftPath = liftingRoot.get("partnerLifting");
        Path<Number> pricePath = liftingRoot.get("price");
        Expression<Number> zero = cb.literal(0.0);

        //ignore null
        Expression<Number> totalLift =
                cb.sum(cb.<Number>selectCase().when(ownLiftPath.isNull(), zero).otherwise(ownLiftPath),
                        cb.<Number>selectCase().when(partnerLiftPath.isNull(), zero).otherwise(partnerLiftPath));
        Expression<Number> totalRevenue = cb.sum(cb.prod(pricePath, totalLift));


        Predicate basePredicate = cb.equal(liftingRoot.get("pscId"), pscId);

        Predicate curYrPredicate = cb.and(
                cb.equal(liftingRoot.get("periodYear"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(liftingRoot.get("periodMonth"), fiscalPeriod.getMonth())
        );

        Predicate prevYrPredicate =
                cb.lessThan(liftingRoot.get("periodYear"), fiscalPeriod.getYear());

        Predicate yearPredicate = cb.or(curYrPredicate, prevYrPredicate);

        Predicate predicate = cb.and(basePredicate, yearPredicate);

        cq.select(totalRevenue.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = entityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double grossIncome(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

        Expression<Double> liftingSum = cb.sum(liftingRoot.<Double>get("ownLifting"), liftingRoot.<Double>get("partnerLifting"));
        Expression<Double> prod = cb.prod(liftingRoot.get("price"), liftingSum);
        Expression<Double> sum = cb.sum(prod);

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(sum.alias("proceed"))
                .where(predicate);

        Number proceed;
        try {
            proceed = entityManager().createQuery(cq).getSingleResult();
            return proceed.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double cashPayment(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Lifting> liftingRoot = cq.from(Lifting.class);

        Expression<Double> cashSum = cb.sum(liftingRoot.<Double>get("cashPayment"));

        Predicate predicate = cb.and(
                cb.equal(liftingRoot.get("pscId"), pscId),
                cb.equal(cb.function("year", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cb.function("month", Integer.class, liftingRoot.get("liftingDate")), fiscalPeriod.getMonth())
        );

        cq.select(cashSum.alias("cashPayment"))
                .where(predicate);

        Number cashPayment;
        try {
            cashPayment = entityManager().createQuery(cq).getSingleResult();
            return cashPayment.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    
    
	@Override
	public LiftingId nextLiftingId() {
		String random = UUID.randomUUID().toString().toUpperCase();

        return new LiftingId(random.substring(0, random.indexOf("-")));
	}
	
	
	@Override
	public void store(Lifting lifting) {
		 entityManager().persist(lifting);			
	}

	@Override
	public void save(Lifting lifting) {
		entityManager().merge(lifting);		
	}

	@Override
	public void remove(Lifting lifting) {
		entityManager().remove(lifting);		
	}
	
	@Override
    public Lifting liftingOfId(Object id) {
        return entityManager().find(Lifting.class, id);
    }

    @Override
    public List<Lifting> allLiftings() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Lifting.class));
        return entityManager().createQuery(cq).getResultList();
    }
	
	

}
