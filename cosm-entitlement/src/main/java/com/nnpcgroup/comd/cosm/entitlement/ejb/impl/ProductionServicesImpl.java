/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.controller.GeneralController;
import com.nnpcgroup.comd.cosm.entitlement.ejb.ProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.entity.Terminal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionServicesImpl<T extends Production> extends AbstractCrudServicesImpl<T> implements ProductionServices<T> {

    private static final Logger log = Logger.getLogger(ProductionServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Inject
    GeneralController genController;

    public ProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        log.info("ProductionBean::setEntityManager() called...");
        return em;
    }

    @Override
    public abstract List<T> findByYearAndMonth(int year, int month);

    @Override
    public T findByContractPeriod(int year, int month, ContractStream cs) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T production;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contractStream"), cs)
                    ));

            Query query = getEntityManager().createQuery(cq);

            production = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return production;
    }

    @Override
    public List<T> findByContractPeriod(int year, int month, FiscalArrangement fa){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contractStream").get("fiscalArrangement"), fa)
                    ));

            Query query = getEntityManager().createQuery(cq);

            productions = query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }

        return productions;

    }

    @Override
    public List<T> getTerminalProduction(int year, int month, Terminal terminal){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<T> productions;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contractStream")
                                    .get("crudeType")
                                    .get("terminal"), terminal)
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

    @Override
    public abstract T createInstance();

    @Override
    public T computeOpeningStock(T production) {
        Production prod = (Production) getPreviousMonthProduction(production);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            ((Production) production).setOpeningStock(openingStock);
        } else {
            ((Production) production).setOpeningStock(0.0);
        }
        return production;
    }

    @Override
    public T getPreviousMonthProduction(T production) {
        int month = ((Production) production).getPeriodMonth();
        int year = ((Production) production).getPeriodYear();
        ContractStream cs = ((Production) production).getContractStream();

        if (month > 1) {
            --month;
        } else {
            month = 12;
            --year;
        }

        T prod = findByContractPeriod(year, month, cs);

        return prod;

    }

    @Override
    public T computeClosingStock(T production) {
        Double closingStock;
        Double availability = ((Production) production).getAvailability();
        Double lifting = ((Production) production).getLifting();

        closingStock = availability - lifting;
        ((Production) production).setClosingStock(closingStock);

        return production;
    }

    @Override
    public T computeGrossProduction(T production) {
        Double prodVolume = ((Production) production).getProductionVolume();
        int days = genController.daysOfMonth(production.getPeriodYear(), production.getPeriodMonth());
        Double grossProd = prodVolume * days;

        log.log(Level.INFO, "Gross Production=>{0} * {1} = {2}", new Object[]{grossProd, days, grossProd});

        ((Production) production).setGrossProduction(grossProd);
        return production;
    }

    @Override
    public T enrich(T production) {
        log.log(Level.INFO, "Enriching production {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeGrossProduction(
                                                computeOpeningStock(production)
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public T computeAvailability(T production) {
        Double availability;
        Double ownEntitlement = ((Production) production).getOwnShareEntitlement();
        Double openingStock = ((Production) production).getOpeningStock();

        availability = ownEntitlement + openingStock;

        ((Production) production).setAvailability(availability);
        log.log(Level.INFO, "Availability {0}...", availability);

        return production;
    }

    @Override
    public T computeLifting(T production) {
        Double liftableVolume;
        Integer cargoes;
        Double availability = ((Production) production).getAvailability();

        cargoes = (int) (availability / 950000.0);
        liftableVolume = cargoes * 950000.0;

        ((Production) production).setCargos(cargoes);
        ((Production) production).setLifting(liftableVolume);
        log.log(Level.INFO, "liftable volume={0}, cargos={1}, availability={2}...",
                new Object[]{liftableVolume, cargoes, availability});

        return production;
    }
}
