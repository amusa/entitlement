package com.cosm.psc.psc.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.infrastructure.persistence.jpa.JpaRepository;
import com.cosm.psc.psc.domain.model.ProductionSharingContract;
import com.cosm.psc.psc.domain.model.ProductionSharingContractRepository;


@ApplicationScoped
public class JpaProductionSharingContractRepository extends JpaRepository<ProductionSharingContract> implements ProductionSharingContractRepository {
	
	public JpaProductionSharingContractRepository(Class<ProductionSharingContract> entityClass) {
		super(entityClass);		
	}

	@PersistenceContext 
    private EntityManager em;
    
	@Override
	protected EntityManager entityManager() {		
		return em;
	}

	@Override
	public ProductionSharingContract productionSharingContractOfId(ProductionSharingContractId pscId) {
		return find(pscId.getId());
	}

	@Override
	public List<ProductionSharingContract> productionSharingContracts() {
		return findAll();
	}

	@Override
	public ProductionSharingContractId nextContractId() {
		String random = UUID.randomUUID().toString().toUpperCase();

        return new ProductionSharingContractId(random.substring(0, random.indexOf("-")));
	}
		

}
