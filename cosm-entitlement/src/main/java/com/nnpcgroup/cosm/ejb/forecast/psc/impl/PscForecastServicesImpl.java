/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc.impl;

import com.nnpcgroup.cosm.ejb.forecast.impl.ForecastServicesImpl;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecast;
import com.nnpcgroup.cosm.util.COSMPersistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaDelete;

/**
 * @author 18359
 */
@Stateless
@Local(PscForecastServices.class)
public class PscForecastServicesImpl extends AbstractCrudServicesImpl<PscForecast> implements PscForecastServices, Serializable {

    private static final Logger LOG = Logger.getLogger(PscForecastServicesImpl.class.getName());

    private static final long serialVersionUID = 8993596753945847377L;

    public PscForecastServicesImpl() {
        super(PscForecast.class);
    }

    public PscForecastServicesImpl(Class<PscForecast> entityClass) {
        super(entityClass);
    }

    @Inject
    @COSMPersistence
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PscForecast> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<PscForecast> productions;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(JvForecast.class);
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
    public PscForecast find(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        PscForecast forecast;

        CriteriaQuery cq = cb.createQuery();
        Root<PscForecast> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            forecast = (PscForecast) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return forecast;

    }

    @Override
    public void delete(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // create delete
        CriteriaDelete<JvForecast> delete = cb.
                createCriteriaDelete(JvForecast.class);

        // set the root class
        Root e = delete.from(JvForecast.class);

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
