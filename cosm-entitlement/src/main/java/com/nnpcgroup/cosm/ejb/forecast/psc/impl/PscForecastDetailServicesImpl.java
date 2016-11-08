/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc.impl;

import com.nnpcgroup.cosm.ejb.forecast.impl.ForecastDetailServicesImpl;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.entity.contract.OilField;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetail;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */
@Stateless
@Local(PscForecastDetailServices.class)
@Dependent
public class PscForecastDetailServicesImpl extends ForecastDetailServicesImpl<PscForecastDetail> implements PscForecastDetailServices, Serializable {

    private static final Logger LOG = Logger.getLogger(PscForecastDetailServicesImpl.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public PscForecastDetailServicesImpl() {
        super(PscForecastDetail.class);
    }

    public PscForecastDetailServicesImpl(Class<PscForecastDetail> entityClass) {
        super(entityClass);
    }

    @Override
    public PscForecastDetail find(int year, int month, OilField oilField) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        PscForecastDetail forecastDetail;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("oilField"), oilField)
                    ));

            Query query = getEntityManager().createQuery(cq);

            forecastDetail = (PscForecastDetail) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return forecastDetail;
    }

    @Override
    public void delete(int year, int month, OilField oilField) {

    }

    @Override
    public PscForecastDetail getPreviousMonthProduction(PscForecastDetail production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecastDetail getNextMonthProduction(PscForecastDetail production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecastDetail enrich(PscForecastDetail production) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecastDetail computeGrossProduction(PscForecastDetail forecast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecastDetail computeDailyProduction(PscForecastDetail forecast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
