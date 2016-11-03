/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.production.jv.JvProductionDetailServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionDetail;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionDetailPK;
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
 * @param <E>
 */
public abstract class JvProductionDetailServicesImpl<T extends JvProductionDetail, E extends JvContract> extends ProductionDetailServicesImpl<T> implements JvProductionDetailServices<T, E> {

    private static final Logger LOG = Logger.getLogger(JvProductionDetailServicesImpl.class.getName());

    @EJB
    FiscalArrangementBean fiscalBean;

    public JvProductionDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeGrossProduction(T forecast) {
        Double prodVolume = forecast.getDailyProduction();
        int periodYear = forecast.getPeriodYear();
        int periodMonth = forecast.getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double grossProd = prodVolume * days;

        LOG.log(Level.INFO, "Gross Production = DailyProd * Days => {0} * {1} = {2}", new Object[]{prodVolume, days, grossProd});

        forecast.setGrossProduction(grossProd);
        return forecast;
    }

    @Override
    public T computeDailyProduction(T forecast) {
        Double grossProd = forecast.getGrossProduction();
        int periodYear = forecast.getPeriodYear();
        int periodMonth = forecast.getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double dailyProd = grossProd / days;

        LOG.log(Level.INFO, "Daily Production = GrossProd / Days => {0} / {1} = {2}", new Object[]{grossProd, days, dailyProd});

        forecast.setDailyProduction(dailyProd);
        return forecast;
    }

    @Override
    public T getPreviousMonthProduction(T production) {
        int month = production.getPeriodMonth();
        int year = production.getPeriodYear();

        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
        ContractPK cPK = production.getContract().getContractPK();
        ProductionPK pPK = new ProductionPK(prevFp.getYear(), prevFp.getMonth(), production.getProduction().getFiscalArrangement().getId());

        T prod = find(new JvProductionDetailPK(pPK, cPK));

        return prod;

    }

    @Override
    public T getNextMonthProduction(T production) {
        int month = production.getPeriodMonth();
        int year = production.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
        ContractPK cPK = production.getContract().getContractPK();
        ProductionPK pPK = new ProductionPK(nextFp.getYear(), nextFp.getMonth(), production.getProduction().getFiscalArrangement().getId());

        T prod = find(new JvProductionDetailPK(pPK, cPK));

        return prod;
    }

    @Override
    public T enrich(T production) throws NoRealizablePriceException {
        LOG.log(Level.INFO, "Enriching production {0}...", production);
        return computeDailyProduction(production);
    }

    @Override
    public T find(int year, int month, E contract) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T productionDetail;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), contract)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productionDetail = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return productionDetail;
    }

    @Override
    public void delete(int year, int month, E contract) {
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
