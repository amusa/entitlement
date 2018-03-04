/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.account.infrastructure.persistence.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.cosm.account.domain.model.CostItem;
import com.cosm.account.domain.model.ProductionCost;
import com.cosm.account.domain.model.ProductionCostId;
import com.cosm.account.domain.model.ProductionCostRepository;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.infrastructure.persistence.jpa.JpaRepository;


/**
 * @author 18359
 */
@ApplicationScoped
public class JpaProductionCostRepository extends JpaRepository<ProductionCost> implements ProductionCostRepository, Serializable {
   	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext 
    private EntityManager em;
    
	@Override
	protected EntityManager entityManager() {		
		return em;
	}

    public JpaProductionCostRepository() {
        super(ProductionCost.class);
    }

    @Override
    public List<ProductionCost> costItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId){ //previously getProductionCost()
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod),
                            cb.equal(e.get("pscId"), pscId)
                    ));

            Query query = entityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public List<ProductionCost> opexCostItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) { //previously getOpex()

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod),
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("costItem").get("costCategory").get("code"), "OPEX")
                    ));

            Query query = entityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public List<ProductionCost> capexCostItemsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) { //previously getCapex()

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        List<ProductionCost> prodCosts;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(ProductionCost.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod),
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("costItem").get("costCategory").get("code"), "CAPEX")
                    ));

            Query query = entityManager().createQuery(cq);

            prodCosts = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return prodCosts;
    }

    @Override
    public double costToDate(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double costToDate;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));

        Predicate basePredicate = cb.equal(cost.get("pscId"), pscId);

        Predicate curYrPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cost.get("fiscalPeriod").get("month"), fiscalPeriod.getMonth())
        );

        Predicate prevYrPredicate =
                cb.lessThan(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear());

        Predicate yearPredicate = cb.or(curYrPredicate, prevYrPredicate);

        Predicate predicate = cb.and(basePredicate, yearPredicate);

        cq.select(sum.alias("costToDate"))
                .where(predicate);

        costToDate = entityManager().createQuery(cq).getSingleResult();

        if (costToDate == null) {
            return 0;
        }

        return costToDate;
    }

    @Override
    public double opexOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) { //previously getOpex()

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double opex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        cq.select(sum.alias("amountTotal"))
                .where(
                        cb.and(
                                cb.equal(cost.get("pscId"), pscId),
                                cb.equal(cost.get("fiscalPeriod"), fiscalPeriod),                                
                                cb.equal(cost.get("costItem").get("costCategory").get("code"), "OPEX")
                        )
                );

        opex = entityManager().createQuery(cq).getSingleResult();

        if (opex == null) {
            return 0;
        }

        return opex;
    }

    @Override
    public double opexOfYearToMonth(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double opex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));

        Predicate basePredicate
                = cb.equal(cost.get("pscId"), pscId);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cost.get("fiscalPeriod").get("month"), fiscalPeriod.getMonth())
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

        opex = entityManager().createQuery(cq).getSingleResult();

        if (opex == null) {
            return 0;
        }

        return opex;
    }

    @Override
    public double capexOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double capex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        cq.select(sum.alias("amountTotal"))
                .where(
                        cb.and(
                                cb.equal(cost.get("pscId"), pscId),
                                cb.equal(cost.get("fiscalPeriod"), fiscalPeriod),                               
                                cb.equal(cost.get("costItem").get("costCategory").get("code"), "CAPEX")
                        )
                );

        capex = entityManager().createQuery(cq).getSingleResult();

        if (capex == null) {
            return 0;
        }

        return capex;
    }

    @Override
    public double capexOfYearToMonth(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double capex;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));

        Predicate basePredicate
                = cb.equal(cost.get("pscId"), pscId);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cost.get("fiscalPeriod"), fiscalPeriod.getMonth())
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

        capex = entityManager().createQuery(cq).getSingleResult();

        if (capex == null) {
            return 0;
        }

        return capex;
    }

    @Override
    public double capitalAllowanceRecovery(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double capitalAllowance;//Armotization to date

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> cost = cq.from(entityClass);

        Expression<Double> sum = cb.sum(cost.<Double>get("amount"));
        Expression<Double> prod = cb.prod(sum, (1 / 60.0));

        Predicate basePredicate = cb.and(
                cb.equal(cost.get("pscId"), pscId),
                cb.equal(cost.get("costItem").get("costCategory").get("code"), "CAPEX")
        );

        Predicate fullYearPredicate = cb.between(cost.get("fiscalPeriod")
        		.get("year"), (fiscalPeriod.getYear() - 4), fiscalPeriod.getYear());

        Predicate rightPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cost.get("fiscalPeriod").get("month"), fiscalPeriod.getMonth())
        );

        Predicate midPredicate = cb.and(
                cb.lessThan(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.greaterThan(cost.get("fiscalPeriod").get("year"), (fiscalPeriod.getYear() - 5))
        );

        Predicate leftPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), (fiscalPeriod.getYear() - 5)),
                cb.greaterThan(cost.get("periodMonth").get("month"), (fiscalPeriod.getMonth() + 1) % 12)
        );

        Predicate lessYearPredicate = cb.or(rightPredicate, midPredicate, leftPredicate);

        Predicate predicate;
        if (fiscalPeriod.getMonth() == 12) {
            predicate = cb.and(basePredicate, fullYearPredicate);
        } else {
            predicate = cb.and(basePredicate, lessYearPredicate);
        }

        cq.select(prod.alias("ca"))
                .where(predicate);

        capitalAllowance = entityManager().createQuery(cq).getSingleResult();

        if (capitalAllowance == null) {
            return 0;
        }

        return capitalAllowance;
    }

    @Override
    public Double educationTaxOfCost(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId){
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        Double eduTax;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<ProductionCost> e = cq.from(ProductionCost.class);
        Expression<Double> edTaxExpr = cb.toDouble(e.<Double>get("amount"));
        try {
            cq.select(edTaxExpr).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod),
                            cb.equal(e.get("pscId"), pscId),
                            cb.equal(e.get("costItem").get("code"), "1006")//Education tax code: TODO:
                    ));

            eduTax = entityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return eduTax;
    }

   
    @Override
    public Map<CostItem, Double> currentYearCostItems(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<ProductionCost> cost = cq.from(ProductionCost.class);

        Predicate basePredicate
                = cb.equal(cost.get("pscId"), pscId);

        Predicate currYrPredicate = cb.and(
                cb.equal(cost.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
                cb.lessThanOrEqualTo(cost.get("fiscalPeriod").get("month"), fiscalPeriod.getMonth())
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

        TypedQuery<Tuple> tq = entityManager().createQuery(cq);
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
	public ProductionCostId nextProductionCostId() {
		String random = UUID.randomUUID().toString().toUpperCase();

        return new ProductionCostId(random.substring(0, random.indexOf("-")));
	}

 
    
}
