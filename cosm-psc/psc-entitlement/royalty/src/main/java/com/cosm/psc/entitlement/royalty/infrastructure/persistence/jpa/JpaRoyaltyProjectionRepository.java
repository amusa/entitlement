package com.cosm.psc.entitlement.royalty.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjection;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjectionId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjectionRepository;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyView;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaRoyaltyProjectionRepository implements RoyaltyProjectionRepository {

    @PersistenceContext
    private EntityManager em;

    private EntityManager entityManager() {
        return em;
    }

    @Override
    public RoyaltyProjection royaltyProjectionOfId(RoyaltyProjectionId royId) {
        RoyaltyProjection royProjection = entityManager().find(RoyaltyProjection.class, royId);
        return royProjection;
    }

    @Override
    public Optional<RoyaltyProjection> royaltyProjectionOfPeriod(
            FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        RoyaltyProjection royView;

        CriteriaQuery cq = cb.createQuery();
        Root<RoyaltyProjection> e = cq.from(RoyaltyProjection.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod), cb.equal(e.get("pscId"), pscId)));

            Query query = entityManager().createQuery(cq);

            royView = (RoyaltyProjection) query.getSingleResult();
        } catch (NoResultException nre) {
            return Optional.empty();
        }

        return Optional.ofNullable(royView);
    }

    @Override
    public void store(RoyaltyProjection royProjection) {
        entityManager().persist(royProjection);

    }

    @Override
    public void remove(RoyaltyProjectionId royId) {
        entityManager().remove(royId);
    }

    @Override
    public List<RoyaltyProjection> royaltyProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo, ProductionSharingContractId pscId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RoyaltyProjection> allRoyaltyProjections() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(RoyaltyView.class));
        return entityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<RoyaltyProjection> royaltyProjectionsOfContract(ProductionSharingContractId pscId) {
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        List<RoyaltyProjection> royProjections;

        CriteriaQuery cq = cb.createQuery();
        Root<RoyaltyProjection> e = cq.from(RoyaltyProjection.class);
        try {
            cq.select(e).where(
                    cb.equal(e.get("pscId"), pscId));

            Query query = entityManager().createQuery(cq);

            royProjections = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return royProjections;
    }

    @Override
    public void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(RoyaltyProjection royProjection) {
        entityManager().merge(royProjection);
    }

    @Override
    public RoyaltyProjectionId nextId() {
        String random = UUID.randomUUID().toString().toUpperCase();

        return new RoyaltyProjectionId(random.substring(0, random.indexOf("-")));
    }

}
