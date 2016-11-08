/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting.impl;

import com.nnpcgroup.cosm.ejb.lifting.JvLiftingServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.lifting.JvLifting;
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
@Local(JvLiftingServices.class)
public class JvLiftingServicesImpl extends LiftingServicesImpl<JvLifting> implements JvLiftingServices {

    public JvLiftingServicesImpl() {
        super(JvLifting.class);
    }

    @Override
    public List<JvLifting> find(Contract contract, Date fromDate, Date toDate) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<JvLifting> Lifting_ = m.entity(entityClass);

        List<JvLifting> liftings;

        CriteriaQuery<JvLifting> cq = cb.createQuery(entityClass);
        Root<JvLifting> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("contract"), contract),
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
