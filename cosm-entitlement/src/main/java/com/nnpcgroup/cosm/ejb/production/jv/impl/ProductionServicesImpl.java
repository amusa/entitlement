/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionServicesImpl<T extends Production> extends AbstractCrudServicesImpl<T> implements ProductionServices<T> {

    private static final Logger LOG = Logger.getLogger(ProductionServicesImpl.class.getName());

    public ProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public List<T> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;
    }

    @Override
    public T findByContractPeriod(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T production;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            production = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;

    }

    @Override
    public void delete(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // create delete
        CriteriaDelete<T> delete = cb.
                createCriteriaDelete(entityClass);

        // set the root class
        Root e = delete.from(entityClass);

        // set where clause
        delete.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month),
                        cb.equal(e.get("fiscalArrangement"), fa)
                ));

        // perform update
        getEntityManager().createQuery(delete).executeUpdate();
    }

}
