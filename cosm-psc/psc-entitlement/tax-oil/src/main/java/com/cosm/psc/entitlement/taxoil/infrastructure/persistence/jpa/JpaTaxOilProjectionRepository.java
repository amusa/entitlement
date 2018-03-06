package com.cosm.psc.entitlement.taxoil.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjection;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjectionId;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjectionRepository;

public class JpaTaxOilProjectionRepository implements TaxOilProjectionRepository {

	
	@PersistenceContext 
    private EntityManager em;
    

	private EntityManager entityManager() {		
		return em;
	}
	
	
	@Override
	public TaxOilProjection taxOilProjectionOfId(TaxOilProjectionId toId) {		
		return entityManager().find(TaxOilProjection.class, toId);
	}

	@Override
	public TaxOilProjection taxOilProjectionOfFiscalPeriod(FiscalPeriod fiscalPeriod,
			ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(TaxOilProjection toProj) {
		entityManager().persist(toProj);

	}

	@Override
	public void save(TaxOilProjection toProj) {
		entityManager().merge(toProj);

	}

	@Override
	public void remove(TaxOilProjectionId toId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TaxOilProjection> taxOilProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo,
			ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaxOilProjection> taxOilProjectionsOfContract(ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaxOilProjection> allTaxOilProjections() {
		javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(TaxOilProjection.class));
        return entityManager().createQuery(cq).getResultList();
	}

	@Override
	public TaxOilProjectionId nextTaxOilProjectionId() {
		String random = UUID.randomUUID().toString().toUpperCase();

        return new TaxOilProjectionId(random.substring(0, random.indexOf("-")));
	}

}
