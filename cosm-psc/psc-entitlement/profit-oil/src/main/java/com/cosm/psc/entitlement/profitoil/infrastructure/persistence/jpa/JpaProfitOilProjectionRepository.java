package com.cosm.psc.entitlement.profitoil.infrastructure.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.profitoil.domain.model.AllocationId;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjection;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjectionId;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjectionRepository;

public class JpaProfitOilProjectionRepository implements ProfitOilProjectionRepository {


	@PersistenceContext 
    private EntityManager em;
    
	
	private EntityManager entityManager() {		
		return em;
	}

	@Override
	public ProfitOilProjection profitOilProjectionOfId(ProfitOilProjectionId poId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProfitOilProjection profitOilProjectionOfFiscalPeriod(FiscalPeriod fiscalPeriod,
			ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(ProfitOilProjection poProj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(ProfitOilProjection poProj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(ProfitOilProjectionId poId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProfitOilProjection> profitOilProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo,
			ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfitOilProjection> profitOilProjectionsOfContract(ProductionSharingContractId pscId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfitOilProjection> allProfitOilProjections() {
		javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(ProfitOilProjection.class));
        return entityManager().createQuery(cq).getResultList();
	}

	@Override
	public ProfitOilProjectionId nextId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllocationId nextAllocationId() {
		// TODO Auto-generated method stub
		return null;
	}

}
