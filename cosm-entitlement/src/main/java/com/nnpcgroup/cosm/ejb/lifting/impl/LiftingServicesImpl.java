/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting.impl;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.ejb.lifting.LiftingServices;
import com.nnpcgroup.cosm.entity.lifting.Lifting;
import com.nnpcgroup.cosm.util.COSMPersistence;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * @author 18359
 * @param <T>
 */
public abstract class LiftingServicesImpl<T extends Lifting> extends AbstractCrudServicesImpl<T> implements LiftingServices<T> {

    @Inject
    @COSMPersistence
    private EntityManager em;

    public LiftingServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<T> find(Date fromDate, Date toDate) {

        java.sql.Date fDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date tDate = new java.sql.Date(toDate.getTime());


        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<T> Lifting_ = m.entity(entityClass);

        List<T> liftings;

        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.between(e.get("liftingDate"), fDate, tDate)
            );

            Query query = getEntityManager().createQuery(cq);

            liftings = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return liftings;
    }

}
