/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastDetailServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author 18359
 * @param <T>
 */
public class JvForecastDetailServicesImpl<T extends JvForecastDetail> extends ForecastDetailServicesImpl<T> implements JvForecastDetailServices<T>, Serializable {

    //private static final Logger LOG = Logger.getLogger(JvForecastDetailServicesImpl.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeGrossProduction(T forecast) {
        Double prodVolume = forecast.getDailyProduction();
        int periodYear = forecast.getPeriodYear();
        int periodMonth = forecast.getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double grossProd = prodVolume * days;

        LOG.log(Level.INFO, String.format("Gross Production = DailyProd * Days => %f * %d = %f", new Object[]{prodVolume, days, grossProd}));

        forecast.setGrossProduction(grossProd);
        return forecast;
    }

    @Override
    public T getPreviousMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
//        ContractPK cPK = forecast.getForecastDetailPK().getContractPK();
//        ForecastPK fPK = new ForecastPK(prevFp.getYear(), prevFp.getMonth(), forecast.getForecast().getFiscalArrangement().getId());

        T f = find(prevFp.getYear(), prevFp.getMonth(), forecast.getContract());
//        T f = find(new JvForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

    @Override
    public T getNextMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
//        ContractPK cPK = forecast.getContract().getContractPK();
//        ForecastPK fPK = new ForecastPK(nextFp.getYear(), nextFp.getMonth(), forecast.getForecastDetailPK().getForecastPK().getFiscalArrangementId());

        T f = find(nextFp.getYear(), nextFp.getMonth(), forecast.getContract());
//        T f = find(new JvForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

//    @Override
//    public void delete(List<T> jvDetails) {
//        jvDetails.stream().forEach((fd) -> {
//            delete(fd.getPeriodYear(), fd.getPeriodMonth(), fd.getContract());
//        });
//    }
    @Override
    public T enrich(T production) throws NoRealizablePriceException {
        return computeGrossProduction(production);
    }

    @Override
    public T find(int year, int month, Contract contract) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T forecastDetail;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), contract)
                    ));

            Query query = getEntityManager().createQuery(cq);

            forecastDetail = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return forecastDetail;
    }

    @Override
    public void delete(int year, int month, Contract contract) {
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
                        cb.equal(e.get("contract"), contract)
                ));

        // perform update
        getEntityManager().createQuery(delete).executeUpdate();
    }

}
