/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.cost.impl;

import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.cdi.TaxServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.cost.CostItem;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 * @author 18359
 */
@Stateless
@Local(ProductionCostServices.class)
public class ProductionCostServicesImpl extends CommonServicesImpl<ProductionCost> implements ProductionCostServices, Serializable {

    @Inject
    private TaxServices taxBean;

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

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        cq.select(sum.alias("amountTotal"))
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
    public double getCurrentYearOpex(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double opex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));

        Predicate basePredicate
                = cb.equal(cost.get("psc"), psc);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("periodYear"), year),
                cb.lessThanOrEqualTo(cost.get("periodMonth"), month)
        );

        //Fix: current year cumulative
//        Predicate priorYrPredicate = cb.and(
//                cb.lessThan(cost.get("periodYear"), year)
//        );

//        Predicate yearsPredicate = cb.or(currYrPredicate, priorYrPredicate);

        Predicate opexPredicate = cb.equal(cost.get("costItem").get("costCategory").get("code"), "OPEX");

        Predicate predicate = cb.and(basePredicate, currYrPredicate, opexPredicate);

        cq.select(sum.alias("amountCum"))
                .where(predicate
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

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        cq.select(sum.alias("amountTotal"))
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
    public double getCurrentYearCapex(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double capex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));

        Predicate basePredicate
                = cb.equal(cost.get("psc"), psc);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("periodYear"), year),
                cb.lessThanOrEqualTo(cost.get("periodMonth"), month)
        );

//        Predicate priorYrPredicate = cb.and(
//                cb.lessThan(cost.get("periodYear"), year)
//        );

//        Predicate yearsPredicate = cb.or(currYrPredicate, priorYrPredicate);

        Predicate opexPredicate = cb.equal(cost.get("costItem").get("costCategory").get("code"), "CAPEX");

        Predicate predicate = cb.and(basePredicate, currYrPredicate, opexPredicate);
        cq.select(sum.alias("amountCum"))
                .where(predicate
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

        Predicate rightPredicate = cb.and(
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

        Predicate lessYearPredicate = cb.or(rightPredicate, midPredicate, leftPredicate);

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

    @Override
    public Double getEducationTax(ProductionSharingContract psc, Integer year, Integer month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double eduTax;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> e = cq.from(ProductionCost.class);
        Expression<Double> edTaxExpr = cb.toDouble(e.<Double>get("amount"));
        try {
            cq.select(edTaxExpr).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("psc"), psc),
                            cb.equal(e.get("costItem").get("code"), "1006")//Education tax code: TODO:
                    ));

            eduTax = getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return eduTax;
    }

    public boolean fiscalPeriodExists(ProductionSharingContract psc, Integer year, Integer month) {
        List<ProductionCost> prodCosts = find(psc, year, month);
        return prodCosts != null && !prodCosts.isEmpty();
    }

    @Override
    public Map<CostItem, Double> getProdCostItemCosts(ProductionSharingContract psc, Integer year, Integer month) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<ProductionCost> cost = cq.from(ProductionCost.class);

        Predicate basePredicate
                = cb.equal(cost.get("psc"), psc);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("periodYear"), year),
                cb.lessThanOrEqualTo(cost.get("periodMonth"), month)
        );

        Predicate predicate = cb.and(basePredicate, currYrPredicate);

        Path<CostItem> costItemPath = cost.get("costItem");
        costItemPath.alias("costItem");
        Selection<Number> amountSelection = cb.sum(cost.get("amount")).alias("amountCum");

        cq.multiselect(
                costItemPath,
                amountSelection)
                .groupBy(costItemPath)
                .where(predicate);

        TypedQuery<Tuple> tq = getEntityManager().createQuery(cq);
        List<Tuple> tupleResult = tq.getResultList();

        Map<CostItem, Double> costItemMap = new HashMap<>();

        for (Tuple result : tupleResult) {
            CostItem costItem = result.get("costItem", CostItem.class);
            Double amountCum = result.get("amountCum", Double.class);
            costItemMap.put(costItem, amountCum);
        }

        return costItemMap;
    }

    @Override
    public boolean fiscalPeriodExists(ProductionSharingContract psc, FiscalPeriod fp) {
        return fiscalPeriodExists(psc, fp.getYear(), fp.getMonth());
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
