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
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.util.COSMPersistence;
import javax.persistence.criteria.CriteriaDelete;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class CommonServicesImpl<T> extends AbstractCrudServicesImpl<T> implements CommonServices<T> {

    //private static final Logger log = Logger.getLogger(CommonServicesImpl.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();
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
        return em;
    }

    @Override
    public List<T> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, String.format("Parameters: year=%d, month=%d", new Object[]{year, month}));

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
    public List<T> findByContractPeriod(int year, int month, Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;
    }

    @Override
    public List<T> findByContractPeriod(int year, Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("contract"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;
    }

    @Override
    public T findSingleByContractPeriod(int year, int month, Contract cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T production;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
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
    public List<T> findAnnualProduction(int year, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
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

//    @Override
//    public T openingStockChanged(T production) {
//        LOG.log(Level.INFO, "Opening Stock changed {0}...");
//        return computeClosingStock(
//                computeLifting(
//                        computeAvailability(production)
//                )
//        );
//    }

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
//                        + "FROM JvForecastDetailServices f WHERE f.periodYear = :periodYear "
//                        + "AND f.periodMonth = :periodMonth AND f.contract.crudeType = :crudeType", entityClass);
//        query.setParameter("periodYear", year);
//        query.setParameter("periodMonth", month);
//        query.setParameter("crudeType", terminal.getCrudeType());
//
//        List<T> productions = query.getResultList();
        return productions;
    }

    
    @Override
    public List<T> find(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> forecastDetails;

        CriteriaQuery cq = cb.createQuery();
        Root<T> e = cq.from(entityClass);
        try {

            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("fiscalArrangement"), fa)
                    ));
            Query query = getEntityManager().createQuery(cq);

            forecastDetails = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return forecastDetails;
    }
    
    @Override
    public void delete(int year, int month, FiscalArrangement fa) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaDelete<T> delete = cb.
                createCriteriaDelete(entityClass);

        Root e = delete.from(entityClass);

        delete.where(
                cb.and(cb.equal(e.get("periodYear"), year),
                        cb.equal(e.get("periodMonth"), month),
                        cb.equal(e.get("fiscalArrangement"), fa)
                ));

        getEntityManager().createQuery(delete).executeUpdate();

        // perform update
//        Query query = getEntityManager().createQuery(
//                "DELETE "
//                + "FROM ForecastDetail f WHERE f.periodYear = :year AND f.periodMonth = :month AND f.contract.fiscalArrangement = :fa ");
//        query.setParameter("year", year)
//                .setParameter("month", month)
//                .setParameter("fa", fa);
//
//        query.executeUpdate();
    }

    @Override
    public void delete(List<T> jvDetails) {
        jvDetails.stream().forEach((fd) -> {
            delete(fd);
        });
    }

}
