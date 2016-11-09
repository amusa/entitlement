/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting.impl;

import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * @author 18359
 */
@Stateless
@Local(PscLiftingServices.class)
public class PscLiftingServicesImpl extends LiftingServicesImpl<PscLifting> implements PscLiftingServices {

    public PscLiftingServicesImpl() {
        super(PscLifting.class);
    }

    @Override
    public List<PscLifting> find(ProductionSharingContract psc, Date fromDate, Date toDate) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<PscLifting> Lifting_ = m.entity(entityClass);

        List<PscLifting> liftings;

        CriteriaQuery<PscLifting> cq = cb.createQuery(entityClass);
        Root<PscLifting> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("psc"), psc),
                            cb.between(e.get("liftingDate"), fromDate, toDate)
                    )
            );

            Query query = getEntityManager().createQuery(cq);

            liftings = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return liftings;
    }

}