package com.cosm.psc.psc.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.psc.domain.model.ProductionSharingContract;
import com.cosm.psc.psc.domain.model.ProductionSharingContractRepository;

@ApplicationScoped
public class JpaProductionSharingContractRepository implements ProductionSharingContractRepository {

    public JpaProductionSharingContractRepository() {

    }

    @PersistenceContext(unitName = "pscPU")
    private EntityManager em;

    private EntityManager entityManager() {
        return em;
    }

    @Override
    public ProductionSharingContractId nextContractId() {
        String random = UUID.randomUUID().toString().toUpperCase();

        return new ProductionSharingContractId(random.substring(0, random.indexOf("-")));
    }

    @Override
    public void add(ProductionSharingContract psc) {
        entityManager().persist(psc);
    }

    @Override
    public void save(ProductionSharingContract psc) {
        entityManager().merge(psc);

    }

    @Override
    public void remove(ProductionSharingContract psc) {
        entityManager().remove(psc);

    }

    @Override
    public List<ProductionSharingContract> allProductionSharingContracts() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(ProductionSharingContract.class));
        return entityManager().createQuery(cq).getResultList();
    }

    @Override
    public ProductionSharingContract productionSharingContractOfId(ProductionSharingContractId pscId) {
        return entityManager().find(ProductionSharingContract.class, pscId);
    }

    @Override
    public List<ProductionSharingContract> productionSharingContracts() {
        // TODO Auto-generated method stub
        return null;
    }

}
