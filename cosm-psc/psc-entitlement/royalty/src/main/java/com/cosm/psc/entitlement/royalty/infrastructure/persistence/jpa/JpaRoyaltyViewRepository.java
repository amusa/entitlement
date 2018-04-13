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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        RoyaltyView royView;

        CriteriaQuery cq = cb.createQuery();
        Root<RoyaltyView> e = cq.from(RoyaltyView.class);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("fiscalPeriod"), fiscalPeriod), cb.equal(e.get("pscId"), pscId)));

            Query query = entityManager().createQuery(cq);

            royView = (RoyaltyView) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return royView;
    }

    @Override
    public void store(RoyaltyView royView) {
        entityManager().persist(royView);

    }

    @Override
    public void remove(RoyaltyViewId royId) {
        entityManager().remove(royId);
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
        CriteriaBuilder cb = entityManager().getCriteriaBuilder();

        List<RoyaltyView> royViiews;

        CriteriaQuery cq = cb.createQuery();
        Root<RoyaltyView> e = cq.from(RoyaltyView.class);
        try {
            cq.select(e).where(
                    cb.equal(e.get("pscId"), pscId));

            Query query = entityManager().createQuery(cq);

            royViiews = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return royViiews;
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
