/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class JvProductionServicesImpl<T extends Production> extends CommonServicesImpl<T> implements JvProductionServices<T> {

    private static final Logger LOG = Logger.getLogger(JvProductionServicesImpl.class.getName());

    public JvProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T enrich(T production) {
        LOG.log(Level.INFO, "Enriching production {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeOpeningStock(production)
                                )
                        )
                )
        );
    }

    @Override
    public T findByContractPeriod(int year, int month, Contract cs) {
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
        Root e = cq.from(entityClass);
        try {
            cq.where(
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
    public List<T> findByYearAndMonth(int year, int month) {
        LOG.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        List<T> productions = getEntityManager().createQuery(
                "SELECT p FROM Production p WHERE p.periodYear = :year and p.periodMonth = :month and TYPE(p) = JvForecastProduction")
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return productions;
    }
    
    @Override
    public T computeEntitlement(T production) {
        LOG.info("computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContract().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;
        Double grossProd = production.getGrossProduction();

        grossProd = grossProd == null ? 0 : grossProd;

        ownEntitlement = (grossProd
                * et.getOwnEquity() * 0.01);
        LOG.log(Level.INFO, "Own Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{grossProd, et.getOwnEquity(), ownEntitlement});

        partnerEntitlement = (grossProd
                * et.getPartnerEquity() * 0.01);
        LOG.log(Level.INFO, "Partner Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{grossProd, et.getPartnerEquity(), partnerEntitlement});

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public T computeAvailability(T production) {
        Double availability, partnerAvailability;
        Double ownEntitlement = production.getOwnShareEntitlement();
        Double partnerEntitlement = production.getPartnerShareEntitlement();
        Double openingStock = production.getOpeningStock();
        Double partnerOpeningStock = production.getPartnerOpeningStock();

        availability = ownEntitlement + openingStock;
        partnerAvailability = partnerEntitlement + partnerOpeningStock;

        production.setAvailability(availability);
        production.setPartnerAvailability(partnerAvailability);

        return production;
    }

    @Override
    public T computeClosingStock(T production) {
        Double closingStock, partnerClosingStock;
        Double availability = production.getAvailability();
        Double partnerAvailability = production.getPartnerAvailability();
        Double lifting = production.getLifting();
        Double partnerLifting = production.getPartnerLifting();

        closingStock = availability - lifting;
        partnerClosingStock = partnerAvailability - partnerLifting;
        production.setClosingStock(closingStock);
        production.setPartnerClosingStock(partnerClosingStock);

        return production;
    }
    
    @Override
    public T liftingChanged(T production) {
        LOG.log(Level.INFO, "Lifting changed {0}...", production);
        return computeClosingStock(
                computeAvailability(
                        computeStockAdjustment(
                                computeClosingStock(
                                        computeAvailability(
                                                stockAdjustmentReset(production)
                                        )
                                )
                        )
                )
        );
    }
    
    @Override
    public T computeStockAdjustment(T production) {
        Double closingStock = production.getClosingStock();

        if (closingStock < 0) {
            production.setClosingStock(0.0);
            production.setStockAdjustment(-1 * closingStock);
            production.setPartnerStockAdjustment(closingStock);
        }

        Double partnerClosingStock = production.getPartnerClosingStock();

        if (partnerClosingStock < 0) {
            production.setPartnerClosingStock(0.0);
            production.setPartnerStockAdjustment(-1 * partnerClosingStock);
            production.setStockAdjustment(partnerClosingStock);
        }

        return production;

    }
    
    @Override
    public T grossProductionChanged(T production) {
        LOG.log(Level.INFO, "Gross production changed");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeOpeningStock(
                                                stockAdjustmentReset(production)
                                        )
                                )
                        )
                )
        );
    }
    
    public T stockAdjustmentReset(T production) {
        production.setStockAdjustment(null);
        production.setPartnerStockAdjustment(null);
        return production;
    }


}
