package com.cosm.psc.production.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.FiscalYear;
import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.event.CrudeProduced;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.Publisher;
import com.cosm.common.event.kafka.EventProducer;
import com.cosm.psc.production.domain.model.Production;
import com.cosm.psc.production.domain.model.ProductionId;
import com.cosm.psc.production.domain.model.ProductionRepository;

@ApplicationScoped
public class JpaProductionRepository implements ProductionRepository {

	@Inject
	EventProducer eventProducer;

	@Inject
	Logger logger;

	@PersistenceContext
	private EntityManager em;

	private EntityManager entityManager() {
		return em;
	}

	@Override
	public Production productionOf(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		Production production;

		CriteriaQuery cq = cb.createQuery();
		Root<Production> e = cq.from(Production.class);
		try {
			cq.select(e).where(cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod), cb.equal(e.get("pscId"), pscId)));

			Query query = entityManager().createQuery(cq);

			production = (Production) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		return production;
	}

	@Override
	public List<Production> productionsOf(FiscalYear fiscalYear, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		List<Production> productions;

		CriteriaQuery cq = cb.createQuery();
		Root<Production> e = cq.from(Production.class);
		try {
			cq.select(e).where(cb.and(cb.equal(e.get("fiscalPeriod").get("year"), fiscalYear.getYear()),
					cb.equal(e.get("pscId"), pscId)));

			Query query = entityManager().createQuery(cq);

			productions = query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}

		return productions;
	}

	@Override
	public List<Production> productionsOf(FiscalYear fiscalYear, OilFieldId oilFieldId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		List<Production> productions;

		CriteriaQuery cq = cb.createQuery();
		Root<Production> e = cq.from(Production.class);
		try {
			cq.select(e).where(cb.and(cb.equal(e.get("fiscalPeriod").get("year"), fiscalYear.getYear()),
					cb.equal(e.get("oilFieldId"), oilFieldId)));

			Query query = entityManager().createQuery(cq);

			productions = query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}

		return productions;
	}

	@Override
	public Production productionOf(FiscalPeriod fiscalPeriod, OilFieldId oilFieldId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		Production production;

		CriteriaQuery cq = cb.createQuery();
		Root<Production> e = cq.from(Production.class);
		try {
			cq.select(e).where(
					cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod), cb.equal(e.get("oilFieldId"), oilFieldId)));

			Query query = entityManager().createQuery(cq);

			production = (Production) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		return production;
	}

	@Override
	public double grossProductionOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		Double grossProdCum;

		CriteriaQuery<Double> cq = cb.createQuery(Double.class);
		Root<Production> production = cq.from(Production.class);

		Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));
		cq.select(sum.alias("grossProduction")).where(cb.and(cb.equal(production.get("pscId"), pscId),
				cb.equal(production.get("fiscalPeriod"), fiscalPeriod)));

		grossProdCum = entityManager().createQuery(cq).getSingleResult();

		if (grossProdCum == null) {
			return 0;
		}

		return grossProdCum;
	}

	@Override
	public double grossProductionOfFiscalYear(FiscalYear fiscalYear, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		Double grossProdCum;

		CriteriaQuery<Double> cq = cb.createQuery(Double.class);
		Root<Production> production = cq.from(Production.class);

		Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));
		cq.select(sum.alias("grossProduction")).where(cb.and(cb.equal(production.get("pscId"), pscId),
				cb.equal(production.get("fiscalPeriod").get("year"), fiscalYear.getYear())));

		grossProdCum = entityManager().createQuery(cq).getSingleResult();

		if (grossProdCum == null) {
			return 0;
		}

		return grossProdCum;
	}

	@Override
	public double grossProductionToDate(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		Double grossProdCum;

		CriteriaQuery<Double> cq = cb.createQuery(Double.class);
		Root<Production> production = cq.from(Production.class);

		Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));

		Predicate basePredicate = cb.equal(production.get("pscId"), pscId);

		Predicate currYrPredicate = cb.and(cb.equal(production.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()),
				cb.lessThanOrEqualTo(production.get("fiscalPeriod").get("month"), fiscalPeriod.getMonth()));

		Predicate priorYrPredicate = cb
				.and(cb.lessThan(production.get("fiscalPeriod").get("year"), fiscalPeriod.getYear()));

		Predicate yearsPredicate = cb.or(currYrPredicate, priorYrPredicate);

		Predicate predicate = cb.and(basePredicate, yearsPredicate);

		cq.select(sum.alias("grossProduction")).where(predicate);

		grossProdCum = entityManager().createQuery(cq).getSingleResult();

		if (grossProdCum == null) {
			return 0;
		}

		return grossProdCum;
	}

	@Override
	public boolean isFirstProductionOfYear(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();

		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		Root<Production> production = cq.from(Production.class);

		Expression<Integer> min = cb.min(production.<Integer>get("fiscalPeriod").get("month"));

		Integer firstMonth;

		try {
			cq.select(min.alias("firstProdMonth")).where(cb.and(cb.equal(production.get("pscId"), pscId),
					cb.equal(production.get("fiscalPeriod").get("year"), fiscalPeriod.getYear())));

			Query query = entityManager().createQuery(cq);

			firstMonth = (Integer) query.getSingleResult();

			if (firstMonth == null) {
				return false;
			}
			return firstMonth.intValue() == fiscalPeriod.getMonth();

		} catch (NoResultException nre) {

		}

		return false;
	}

	@Override
	public boolean isFirstOmlProduction(FiscalYear fiscalYear, ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void store(Production production) {
		entityManager().persist(production);
	}

	@Override
	public void remove(Production production) {
		entityManager().remove(production);

	}

	@Override
	public void save(Production production) {
		entityManager().merge(production);

	}

	@Override
	public Production productionOfId(ProductionId productionId) {
		return entityManager().find(Production.class, productionId);
	}

	@Override
	public ProductionId nextProductionId() {
		String random = UUID.randomUUID().toString().toUpperCase();

		return new ProductionId(random.substring(0, random.indexOf("-")));
	}

}
