/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc.impl;

import com.nnpcgroup.cosm.ejb.forecast.impl.ForecastDetailServicesImpl;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscForecastDetailServices;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.OilField;
import com.nnpcgroup.cosm.entity.cost.ProductionCost;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetail;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 */
@Stateless
@Local(PscForecastDetailServices.class)
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
        Root<PscForecastDetail> e = cq.from(entityClass);
        try {
            cq.select(e).where(
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
    public List<PscForecastDetail> find(int year, ProductionSharingContract psc) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<PscForecastDetail> pscDetails;

        CriteriaQuery cq = cb.createQuery();
        Root<PscForecastDetail> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("psc"), psc)
                    ));

            Query query = getEntityManager().createQuery(cq);

            pscDetails = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return pscDetails;
    }

    @Override
    public List<PscForecastDetail> find(int year, OilField oilField) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<PscForecastDetail> pscDetails;

        CriteriaQuery cq = cb.createQuery();
        Root<PscForecastDetail> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("oilField"), oilField)
                    ));

            Query query = getEntityManager().createQuery(cq);

            pscDetails = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return pscDetails;
    }

    @Override
    public double getGrossProduction(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double grossProdCum;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<PscForecastDetail> production = cq.from(entityClass);

        Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));
        cq.select(sum.alias("grossProduction"))
                .where(
                        cb.and(
                                cb.equal(production.get("fiscalArrangement"), psc),
                                cb.equal(production.get("periodYear"), year),
                                cb.equal(production.get("periodMonth"), month)
                        )
                );

        grossProdCum = getEntityManager().createQuery(cq).getSingleResult();

        if (grossProdCum == null) {
            return 0;
        }

        return grossProdCum;

    }

    @Override
    public double getGrossProductionToDate(ProductionSharingContract psc, int year, int month) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Double grossProdCum;

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<PscForecastDetail> production = cq.from(entityClass);

        Expression<Double> sum = cb.sum(production.<Double>get("grossProduction"));

        Predicate basePredicate
                = cb.equal(production.get("fiscalArrangement"), psc);

        Predicate currYrPredicate = cb.and(
                cb.equal(production.get("periodYear"), year),
                cb.lessThanOrEqualTo(production.get("periodMonth"), month)
        );

        Predicate priorYrPredicate = cb.and(
                cb.lessThan(production.get("periodYear"), year)
        );

        Predicate yearsPredicate = cb.or(currYrPredicate, priorYrPredicate);

        Predicate predicate=cb.and(basePredicate, yearsPredicate);
        
        cq.select(sum.alias("grossProduction"))
                .where(predicate);

        
        grossProdCum = getEntityManager().createQuery(cq).getSingleResult();

        if (grossProdCum == null) {
            return 0;
        }

        return grossProdCum;
       
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

    @Override
    public void delete(int year, int month, OilField oilField) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
