/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastDetailServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetailPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author 18359
 * @param <T>
 */
public abstract class JvForecastDetailServicesImpl<T extends JvForecastDetail> extends ForecastDetailServicesImpl<T> implements JvForecastDetailServices<T>, Serializable {

    //private static final Logger LOG = Logger.getLogger(JvForecastDetailServicesImpl.class.getName());
    private static final Logger LOG = LogManager.getRootLogger();
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T forecast) {
        JvForecastDetail prod = getPreviousMonthProduction(forecast);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            Double partnerOpeningStock = prod.getPartnerClosingStock();
            forecast.setOpeningStock(openingStock);
            forecast.setPartnerOpeningStock(partnerOpeningStock);
        } else {
            forecast.setOpeningStock(0.0);
            forecast.setPartnerOpeningStock(0.0);
        }
        return forecast;
    }

    @Override
    public T computeGrossProduction(T forecast) {
        Double prodVolume = forecast.getProductionVolume();
        int periodYear = forecast.getPeriodYear();
        int periodMonth = forecast.getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double grossProd = prodVolume * days;

        LOG.log(Level.INFO, String.format("Gross Production = DailyProd * Days => %f * %d = %f", new Object[]{prodVolume, days, grossProd}));

        forecast.setGrossProduction(grossProd);
        return forecast;
    }

    @Override
    public T openingStockChanged(T forecast) {
        LOG.log(Level.INFO, "Opening Stock changed:");
        return computeClosingStock(
                computeLifting(
                        computeAvailability(forecast)
                )
        );
    }

    @Override
    public T computeLifting(T forecast) {
        Double liftableVolume, partnerLiftableVolume;
        Integer cargoes, partnerCargoes;
        Double availability = forecast.getAvailability();
        Double partnerAvailability = forecast.getPartnerAvailability();

        if (forecast.getLifting() == null) {
            cargoes = (int) (availability / 950000.0);
            liftableVolume = cargoes * 950000.0;
            forecast.setCargos(cargoes);
            forecast.setLifting(liftableVolume);
        }

        if (forecast.getPartnerLifting() == null) {
            partnerCargoes = (int) (partnerAvailability / 950000.0);
            partnerLiftableVolume = partnerCargoes * 950000.0;
            forecast.setPartnerCargos(partnerCargoes);
            forecast.setPartnerLifting(partnerLiftableVolume);
        }

        return forecast;
    }

    @Override
    public T enrich(T production) throws NoRealizablePriceException {
        LOG.log(Level.INFO, "Enriching production:");
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
    public T computeEntitlement(T production) {
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
        Double grossProd = production.getGrossProduction();

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
    public T getPreviousMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
        ContractPK cPK = forecast.getForecastDetailPK().getContract();
        ForecastPK fPK = new ForecastPK(prevFp.getYear(), prevFp.getMonth(), forecast.getForecast().getFiscalArrangement().getId());

        T f = find(new ForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

    @Override
    public T getNextMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
        ContractPK cPK = forecast.getContract().getContractPK();
        ForecastPK fPK = new ForecastPK(nextFp.getYear(), nextFp.getMonth(), forecast.getForecastDetailPK().getForecast().getFiscalArrangementId());

        T f = find(new ForecastDetailPK(fPK, cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
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

    @Override
    public void delete(int year, int month, FiscalArrangement fa) {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//      
//        CriteriaDelete<T> delete = cb.
//                createCriteriaDelete(entityClass);
//
//        Root e = delete.from(entityClass);
//
//        delete.where(
//                cb.and(cb.equal(e.get("periodYear"), year),
//                        cb.equal(e.get("periodMonth"), month),
//                        cb.equal(e.get("contract").get("fiscalArrangement"), fa)                        
//                ));
//
//        getEntityManager().createQuery(delete).executeUpdate();

        // perform update
        Query query = getEntityManager().createQuery(
                "DELETE "
                + "FROM ForecastDetail f WHERE f.periodYear = :year AND f.periodMonth = :month AND f.contract.fiscalArrangement = :fa ");
        query.setParameter("year", year)
                .setParameter("month", month)
                .setParameter("fa", fa);

        query.executeUpdate();
    }

    @Override
    public void delete(List<T> jvDetails) {
        jvDetails.stream().forEach((fd) -> {
            delete(fd.getPeriodYear(), fd.getPeriodMonth(), fd.getContract());
        });
    }

}
