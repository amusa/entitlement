/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.cost.impl;

import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author 18359
 */
@Stateless
@Local(ProductionCostServices.class)
public class ProductionCostServicesImpl extends CommonServicesImpl<ProductionCost> implements ProductionCostServices, Serializable {

    public ProductionCostServicesImpl() {
        super(ProductionCost.class);
    }

    @Override
    public List<ProductionCost> find(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("psc"), psc)
                    ));

            Query query = getEntityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public List<ProductionCost> findOpex(ProductionSharingContract psc, Integer year, Integer month) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("psc"), psc),
                            cb.equal(e.get("costItem").get("costCategory").get("code"), "OPEX")
                    ));

            Query query = getEntityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public List<ProductionCost> findCapex(ProductionSharingContract psc, Integer year, Integer month) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("psc"), psc),
                            cb.equal(e.get("costItem").get("costCategory").get("code"), "CAPEX")
                    ));

            Query query = getEntityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public double getOpex(ProductionSharingContract psc, Integer year, Integer month) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double opex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amountCum"));
        cq.select(sum.alias("amountCum"))
                .where(
                        cb.and(
                                cb.equal(cost.get("psc"), psc),
                                cb.equal(cost.get("periodYear"), year),
                                cb.equal(cost.get("periodMonth"), month),
                                cb.equal(cost.get("costItem").get("costCategory").get("code"), "OPEX")
                        )
                );

        opex = getEntityManager().createQuery(cq).getSingleResult();

        if (opex == null) {
            return 0;
        }

        return opex;
    }

    @Override
    public double getCapex(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double capex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amountCum"));
        cq.select(sum.alias("amountCum"))
                .where(
                        cb.and(
                                cb.equal(cost.get("psc"), psc),
                                cb.equal(cost.get("periodYear"), year),
                                cb.equal(cost.get("periodMonth"), month),
                                cb.equal(cost.get("costItem").get("costCategory").get("code"), "CAPEX")
                        )
                );

        capex = getEntityManager().createQuery(cq).getSingleResult();

        if (capex == null) {
            return 0;
        }

        return capex;
    }

    @Override
    public double getCapitalAllowanceRecovery(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double capitalAllowance;//Armotization to date

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        Expression<Double> prod = cb.prod(sum, (1 / 60.0));

        Predicate basePredicate = cb.and(
                cb.equal(cost.get("psc"), psc),
                cb.equal(cost.get("costItem").get("costCategory").get("code"), "CAPEX")
        );

        Predicate fullYearPredicate = cb.between(cost.get("periodYear"), (year - 4), year);

        Predicate righPredicate = cb.and(
                cb.equal(cost.get("periodYear"), year),
                cb.lessThanOrEqualTo(cost.get("periodMonth"), month)
        );

        Predicate midPredicate = cb.and(
                cb.lessThan(cost.get("periodYear"), year),
                cb.greaterThan(cost.get("periodYear"), (year - 5))
        );

        Predicate leftPredicate = cb.and(
                cb.equal(cost.get("periodYear"), (year - 5)),
                cb.greaterThan(cost.get("periodMonth"), (month + 1) % 12)
        );

        Predicate lessYearPredicate = cb.or(righPredicate, midPredicate, leftPredicate);

        Predicate predicate;
        if (month == 12) {
            predicate = cb.and(basePredicate, fullYearPredicate);
        } else {
            predicate = cb.and(basePredicate, lessYearPredicate);
        }

        cq.select(prod.alias("ca"))
                .where(predicate);

        capitalAllowance = getEntityManager().createQuery(cq).getSingleResult();

        if (capitalAllowance == null) {
            return 0;
        }

        return capitalAllowance;
    }

    //TODO:refactor CommonServices interface
    @Override
    public ProductionCost getPreviousMonthProduction(ProductionCost production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductionCost getNextMonthProduction(ProductionCost production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductionCost enrich(ProductionCost production) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
