/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.production.infrastructure.persistence.jpa;

import com.cosm.common.infrastructure.persistence.jpa.JpaQueryRepository;
import com.cosm.psc.production.domain.model.Production;
import com.cosm.psc.production.domain.repository.ProductionQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;


/**
 *
 * @author amusa
 */
@ApplicationScoped
public class JpaProductionQueryRepository extends JpaQueryRepository<Production> implements ProductionQueryRepository {

    @PersistenceContext(unitName = "CostPU")
    private EntityManager em;

    public JpaProductionQueryRepository() {
        super(Production.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public boolean productionExits(int pscId, int oilFieldId, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery cq = cb.createQuery();
        Root<Production> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("oilFieldId"), oilFieldId),
                            cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month)
                    ));

            Query query = getEntityManager().createQuery(cq);

            return !query.getResultList().isEmpty();

        } catch (NoResultException nre) {

        }

        return false;

    }

    @Override
    public boolean isFirstProductionOfYear(int pscId, int oilFieldId, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<Production> production = cq.from(entityClass);

        Expression<Integer> min = cb.min(production.<Integer>get("periodMonth"));

        Integer firstMonth;

        try {
            cq.select(min.alias("firstProdMonth"))
                    .where(
                            cb.and(
                                    cb.equal(production.get("pscId"), pscId),
                                    cb.equal(production.get("oilFieldId"), oilFieldId),
                                    cb.equal(production.get("periodYear"), year)

                            )
                    );

            Query query = getEntityManager().createQuery(cq);

            firstMonth = (Integer) query.getSingleResult();

            if (firstMonth == null) {
                return false;
            }
            return firstMonth.intValue() == month;

        } catch (NoResultException nre) {

        }

        return false;
    }

    @Override
    public boolean isFirstOmlProduction(int pscId, int oilFieldId, int year, int month) {
        //TODO:implement
        return false;
    }

    @Override
    public Production findSingle(int year, int month, int pscId, int oilFieldId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Production production;

        CriteriaQuery cq = cb.createQuery();
        Root<Production> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("oilFieldId"), oilFieldId)
                    ));

            Query query = getEntityManager().createQuery(cq);

            production = (Production) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;
    }

    @Override
    public List<Production> find(int year, int pscId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<Production> pscDetails;

        CriteriaQuery cq = cb.createQuery();
        Root<Production> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("pscId"), pscId)
                    ));

            Query query = getEntityManager().createQuery(cq);

            pscDetails = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return pscDetails;
    }

    @Override
    public List<Production> find(int year, int pscId, int oilFieldId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<Production> productions;

        CriteriaQuery cq = cb.createQuery();
        Root<Production> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(
                            cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("oilFieldId"), oilFieldId)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;
    }

    @Override
    public double getGrossProduction(int pscId, int oilFieldId, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double grossProdCum;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Production> production = cq.from(entityClass);

        Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));
        cq.select(sum.alias("grossProduction"))
                .where(
                        cb.and(
                                cb.equal(production.get("pscId"), pscId),
                                cb.equal(production.get("oilFieldId"), oilFieldId),
                                cb.equal(production.get("periodYear"), year),
                                cb.equal(production.get("periodMonth"), month)
                        )
                );

        grossProdCum = getEntityManager().createQuery(cq).getSingleResult();

        if (grossProdCum == null) {
            return 0;
        }

        return grossProdCum;

    }

    @Override
    public double getGrossProductionToDate(int pscId, int oilFieldId, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double grossProdCum;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Production> production = cq.from(entityClass);

        Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));

        Predicate basePredicate
                = cb.and(
                        cb.equal(production.get("pscId"), pscId),
                cb.equal(production.get("oilFieldId"), oilFieldId)
        );

        Predicate currYrPredicate = cb.and(
                cb.equal(production.get("periodYear"), year),
                cb.lessThanOrEqualTo(production.get("periodMonth"), month)
        );

        Predicate priorYrPredicate = cb.and(
                cb.lessThan(production.get("periodYear"), year)
        );

        Predicate yearsPredicate = cb.or(currYrPredicate, priorYrPredicate);

        Predicate predicate = cb.and(basePredicate, yearsPredicate);

        cq.select(sum.alias("grossProduction"))
                .where(predicate);

        grossProdCum = getEntityManager().createQuery(cq).getSingleResult();

        if (grossProdCum == null) {
            return 0;
        }

        return grossProdCum;

    }



}
