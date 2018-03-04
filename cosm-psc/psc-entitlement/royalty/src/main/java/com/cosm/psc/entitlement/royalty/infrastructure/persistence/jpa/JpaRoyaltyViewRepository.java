package com.cosm.psc.entitlement.royalty.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyView;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyViewId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyViewRepository;

public class JpaRoyaltyViewRepository implements RoyaltyViewRepository {
	
	@PersistenceContext 
    private EntityManager em;
    

	private EntityManager entityManager() {		
		return em;
	}


	@Override
	public RoyaltyView royaltyViewOfId(RoyaltyViewId royId) {
		RoyaltyView royView = entityManager().find(RoyaltyView.class, royId);
		return royView;
	}


	@Override
	public RoyaltyView royaltyViewOfFiscalPeriod(
			FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		
		return null;
	}


	@Override
	public void store(RoyaltyView royView) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void remove(RoyaltyViewId royId) {
		//TODO		
	}


	@Override
	public List<RoyaltyView> royaltyViewsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo, ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RoyaltyView> allRoyaltyViews() {
		javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(RoyaltyView.class));
        return entityManager().createQuery(cq).getResultList();
	}


	@Override
	public List<RoyaltyView> royaltyViewsOfContract(ProductionSharingContractId pscId) {
		
		return null;
	}


	@Override
	public void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void save(RoyaltyView royView) {
		entityManager().merge(royView);		
	}

	@Override
	public RoyaltyViewId nextRoyaltyViewId() {
		String random = UUID.randomUUID().toString().toUpperCase();

        return new RoyaltyViewId(random.substring(0, random.indexOf("-")));
	}
	
}
