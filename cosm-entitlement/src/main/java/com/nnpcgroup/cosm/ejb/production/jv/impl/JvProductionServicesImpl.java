/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.entity.production.jv.JvProduction;
import com.nnpcgroup.cosm.util.COSMPersistence;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvProductionServices.class)
public class JvProductionServicesImpl extends ProductionServicesImpl<JvProduction> implements JvProductionServices {

    private static final Logger LOG = Logger.getLogger(JvProductionServicesImpl.class.getName());

    @Inject
    @COSMPersistence
    private EntityManager em;

    public JvProductionServicesImpl() {
        super(JvProduction.class);
    }

    public JvProductionServicesImpl(Class<JvProduction> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public JvProduction findByContractPeriod(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        JvProduction production;

        CriteriaQuery cq = cb.createQuery();
        Root<JvProduction> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            production = (JvProduction) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;

    }

    @Override
    public void delete(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // create delete
        CriteriaDelete<JvProduction> delete = cb.
                createCriteriaDelete(JvProduction.class);

        // set the root class
        Root e = delete.from(JvProduction.class);

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
