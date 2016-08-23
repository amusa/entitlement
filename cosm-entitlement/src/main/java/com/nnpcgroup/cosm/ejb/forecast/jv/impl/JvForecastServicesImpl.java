/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */

//@Dependent
public abstract class JvForecastServicesImpl<T extends JvForecast> extends ForecastServicesImpl<T> implements JvForecastServices<T>, Serializable{

    private static final Logger LOG = Logger.getLogger(JvForecastServicesImpl.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T forecast) {
        JvForecast prod = getPreviousMonthProduction(forecast);
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

        LOG.log(Level.INFO, "Gross JvForecastServices = DailyProd * Days => {0} * {1} = {2}", new Object[]{prodVolume, days, grossProd});

        forecast.setGrossProduction(grossProd);
        return forecast;
    }

    @Override
    public T openingStockChanged(T forecast) {
        LOG.log(Level.INFO, "Opening Stock changed {0}...", forecast);
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

        cargoes = (int) (availability / 950000.0);
        partnerCargoes = (int) (partnerAvailability / 950000.0);
        liftableVolume = cargoes * 950000.0;
        partnerLiftableVolume = partnerCargoes * 950000.0;

        forecast.setCargos(cargoes);
        forecast.setLifting(liftableVolume);
        forecast.setPartnerCargos(partnerCargoes);
        forecast.setPartnerLifting(partnerLiftableVolume);

        return forecast;
    }

    @Override
    public T enrich(T production) throws NoRealizablePriceException {
        LOG.log(Level.INFO, "Enriching production {0}...", production);
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
    public T getPreviousMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);
        ContractPK cPK = forecast.getContract().getContractPK();


        T f = find(new ForecastPK(prevFp.getYear(), prevFp.getMonth(), cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

    @Override
    public T getNextMonthProduction(T forecast) {
        int month = forecast.getPeriodMonth();
        int year = forecast.getPeriodYear();
        FiscalPeriod nextFp = getNextFiscalPeriod(year, month);
        ContractPK cPK = forecast.getContract().getContractPK();


        T f = find(new ForecastPK(nextFp.getYear(), nextFp.getMonth(), cPK));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }


}
