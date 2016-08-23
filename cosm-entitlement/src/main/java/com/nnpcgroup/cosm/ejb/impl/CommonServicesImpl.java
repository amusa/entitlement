/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.Terminal;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.util.COSMPersistence;

import javax.persistence.TypedQuery;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class CommonServicesImpl<T> extends AbstractCrudServicesImpl<T> implements CommonServices<T> {

    private static final Logger log = Logger.getLogger(CommonServicesImpl.class.getName());

//    @PersistenceContext(unitName = "entitlementPU")
//    private EntityManager em;
    @Inject
    @COSMPersistence
    private EntityManager em;

    @Inject
    GeneralController genController;

    public CommonServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        log.info("ProductionBean::setEntityManager() called...");
        return em;
    }

//    @Override
//    public List<T> findByYearAndMonth(int year, int month) {
//        log.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});
//
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//
//        List<T> productions;
//
//        CriteriaQuery cq = cb.createQuery();
//        Root e = cq.from(entityClass);
//        try {
//            cq.where(
//                    cb.and(cb.equal(e.get("periodYear"), year),
//                            cb.equal(e.get("periodMonth"), month)
//                    ));
//
//            Query query = getEntityManager().createQuery(cq);
//
//            productions = query.getResultList();
//        } catch (NoResultException nre) {
//            return null;
//        }
//
//        return productions;
//    }
    @Override
    public T findByContractPeriod(int year, int month, Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T production;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            production = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;
    }

    @Override
    public List<T> findByContractPeriod(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract").get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;

    }

    @Override
    public abstract T computeEntitlement(T production);

//    @Override
//    public abstract T createInstance();

    @Override
    public abstract T computeOpeningStock(T production);

    @Override
    public FiscalPeriod getPreviousFiscalPeriod(FiscalPeriod fp) {
        int month = fp.getMonth();
        int year = fp.getYear();

        return getPreviousFiscalPeriod(year, month);
    }

    @Override
    public FiscalPeriod getPreviousFiscalPeriod(int year, int month) {
        if (month > 1) {
            --month;
        } else {
            month = 12;
            --year;
        }

        return new FiscalPeriod(year, month);
    }

    @Override
    public FiscalPeriod getNextFiscalPeriod(int year, int month) {
        int mt = (month % 12) + 1;
        int yr = year;
        
        if (mt == 1) {           
            ++yr;
        }

        return new FiscalPeriod(yr, mt);
    }

    @Override
    public T openingStockChanged(T production) {
        log.log(Level.INFO, "Opening Stock changed {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(production)
                )
        );
    }

    @Override
    public List<T> getTerminalProduction(int year, int month, Terminal terminal) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract").get("crudeType"), terminal.getCrudeType())
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

//        TypedQuery<T> query = getEntityManager().createQuery(
//                "SELECT f "
//                        + "FROM JvForecastServices f WHERE f.periodYear = :periodYear "
//                        + "AND f.periodMonth = :periodMonth AND f.contract.crudeType = :crudeType", entityClass);
//        query.setParameter("periodYear", year);
//        query.setParameter("periodMonth", month);
//        query.setParameter("crudeType", terminal.getCrudeType());
//
//        List<T> productions = query.getResultList();
        return productions;
    }

}
