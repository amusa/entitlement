/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
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

/**
 * @author 18359
 */
@Stateless
@Local(JvForecastServices.class)
public class JvForecastServicesImpl extends ForecastServicesImpl<JvForecast> implements JvForecastServices, Serializable {

    private static final Logger LOG = Logger.getLogger(JvForecastServicesImpl.class.getName());

    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastServicesImpl() {
        super(JvForecast.class);
    }

    public JvForecastServicesImpl(Class<JvForecast> entityClass) {
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
    public List<JvForecast> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<JvForecast> productions;

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
    public JvForecast findByContractPeriod(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        JvForecast forecast;

        CriteriaQuery cq = cb.createQuery();
        Root<JvForecast> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract").get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            forecast = (JvForecast) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return forecast;

    }

}
