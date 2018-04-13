package com.cosm.psc.entitlement.costoil.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjection;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjectionId;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjectionRepository;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaCostOilProjectionRepository implements CostOilProjectionRepository {

    @PersistenceContext
    private EntityManager em;

    private EntityManager entityManager() {
        return em;
    }

    @Override
    public CostOilProjection costOilProjectionOfId(CostOilProjectionId coId) {
        return entityManager().find(CostOilProjection.class, coId);
    }

    @Override
    public Optional<CostOilProjection> costOilProjectionOfPeriod(FiscalPeriod fiscalPeriod,
            ProductionSharingContractId pscId) {

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        CostOilProjection costOilProjection;

        CriteriaQuery cq = cb.createQuery();
        Root<CostOilProjection> e = cq.from(CostOilProjection.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod), cb.equal(e.get("pscId"), pscId)));

            Query query = entityManager().createQuery(cq);

            costOilProjection = (CostOilProjection) query.getSingleResult();
        } catch (NoResultException nre) {
            return Optional.empty();
        }

        return Optional.ofNullable(costOilProjection);
    }

    @Override
    public void store(CostOilProjection coProj
    ) {
        entityManager().persist(coProj);
    }

    @Override
    public void save(CostOilProjection coProj
    ) {
        entityManager().merge(coProj);
    }

    @Override
    public void remove(CostOilProjectionId coId
    ) {

    }

    @Override
    public void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId
    ) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<CostOilProjection> costOilProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo,
             ProductionSharingContractId pscId
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CostOilProjection> costOilProjectionsOfContract(ProductionSharingContractId pscId
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CostOilProjection> allCostOilProjections() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(CostOilProjection.class));
        return entityManager().createQuery(cq).getResultList();
    }

    @Override
    public CostOilProjectionId nextCostOilProjectionId() {
        String random = UUID.randomUUID().toString().toUpperCase();

        return new CostOilProjectionId(random.substring(0, random.indexOf("-")));
    }

}
