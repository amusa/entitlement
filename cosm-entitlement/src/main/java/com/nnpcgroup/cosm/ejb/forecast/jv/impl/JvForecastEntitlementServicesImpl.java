/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.impl.ForecastEntitlementServicesImpl;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlement;

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
public abstract class JvForecastEntitlementServicesImpl<T extends JvForecastEntitlement> extends ForecastEntitlementServicesImpl<T> implements JvForecastEntitlementServices<T>, Serializable {

    //private static final Logger LOG = Logger.getLogger(JvForecastDetailServicesImpl.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastEntitlementServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T entitlement) {
        T prod = getPreviousMonthProduction(entitlement);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            Double partnerOpeningStock = prod.getPartnerClosingStock();
            entitlement.setOpeningStock(openingStock);
            entitlement.setPartnerOpeningStock(partnerOpeningStock);
        } else {
            entitlement.setOpeningStock(0.0);
            entitlement.setPartnerOpeningStock(0.0);
        }
        return entitlement;
    }

    @Override
    public T openingStockChanged(T entitlement) {
        LOG.log(Level.INFO, "Opening Stock changed:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(entitlement)
                )
        );
    }

    @Override
    public T computeLifting(T entitlement) {
        Double liftableVolume, partnerLiftableVolume;
        Integer cargoes, partnerCargoes;
        Double availability = entitlement.getAvailability();
        Double partnerAvailability = entitlement.getPartnerAvailability();

        if (entitlement.getLifting() == null) {
            cargoes = (int) (availability / 950000.0);
            liftableVolume = cargoes * 950000.0;
            entitlement.setCargos(cargoes);
            entitlement.setLifting(liftableVolume);
        }

        if (entitlement.getPartnerLifting() == null) {
            partnerCargoes = (int) (partnerAvailability / 950000.0);
            partnerLiftableVolume = partnerCargoes * 950000.0;
            entitlement.setPartnerCargos(partnerCargoes);
            entitlement.setPartnerLifting(partnerLiftableVolume);
        }

        return entitlement;
    }

    @Override
    public T enrich(T entitlement, Double grossProd) throws Exception {
        LOG.log(Level.INFO, "Enriching production:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeOpeningStock(entitlement),
                                        grossProd
                                )
                        )
                )
        );
    }

    @Override
    public T enrich(T production) throws Exception {
        LOG.log(Level.INFO, "Enriching production:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(production)
                )
        );
    }

    @Override
    public T computeEntitlement(T production, Double grossProd) {
        LOG.info("computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContract().getFiscalArrangement();
        //fa = fiscalBean.find(production.getContract().getFiscalArrangementId());

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;

        grossProd = grossProd == null ? 0 : grossProd;

        ownEntitlement = (grossProd
                * et.getOwnEquity() * 0.01);
        LOG.log(Level.INFO, String.format("Own Entitlement=>%f * %f * 0.01 = %f", new Object[]{grossProd, et.getOwnEquity(), ownEntitlement}));

        partnerEntitlement = (grossProd
                * et.getPartnerEquity() * 0.01);
        LOG.log(Level.INFO, String.format("Partner Entitlement=>%f * %f * 0.01 = %f", new Object[]{grossProd, et.getPartnerEquity(), partnerEntitlement}));

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
        LOG.log(Level.INFO, String.format("Own Availability=entitlement + openingStock => %f + %f = %f", new Object[]{ownEntitlement, openingStock, availability}));
        LOG.log(Level.INFO, String.format("Partner Availability=entitlement + openingStock => %f + %f = %f", new Object[]{partnerEntitlement, partnerOpeningStock, partnerAvailability}));

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
        LOG.log(Level.INFO, String.format("ClosingStock=availability - lifting => %f - %f = %f", new Object[]{availability, lifting, closingStock}));
        LOG.log(Level.INFO, String.format("Partner ClosingStock=availability - lifting => %f - %f = %f", new Object[]{partnerAvailability, partnerLifting, partnerClosingStock}));

        return production;
    }

    @Override
    public T getPreviousMonthProduction(T entitlement) {
        int month = entitlement.getPeriodMonth();
        int year = entitlement.getPeriodYear();
        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
//        ContractPK cPK = entitlement.getForecastEntitlementPK().getContractPK();
//        ForecastPK fPK = new ForecastPK(prevFp.getYear(), prevFp.getMonth(), entitlement.getForecast().getFiscalArrangement().getId());

        T f = find(prevFp.getYear(), prevFp.getMonth(), entitlement.getContract());
//        T f = find(new JvForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

    @Override
    public T getNextMonthProduction(T entitlement) {
        int month = entitlement.getPeriodMonth();
        int year = entitlement.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
//        ContractPK cPK = entitlement.getContract().getContractPK();
//        ForecastPK fPK = new ForecastPK(nextFp.getYear(), nextFp.getMonth(), entitlement.getForecastEntitlementPK().getForecastPK().getFiscalArrangementId());

        T f = find(nextFp.getYear(), nextFp.getMonth(), entitlement.getContract());
//        T f = find(new JvForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

    @Override
    public T find(int year, int month, Contract contract) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        T entitlement;

        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(entityClass);
        try {
            cq.select(e).where(
                    cb.and(cb.equal(e.get("periodYear"), year),
                            cb.equal(e.get("periodMonth"), month),
                            cb.equal(e.get("contract"), contract)
                    ));

            Query query = getEntityManager().createQuery(cq);

            entitlement = (T) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return entitlement;
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
