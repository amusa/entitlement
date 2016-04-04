/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.ejb.impl.ProductionServicesImpl;
import com.nnpcgroup.cosm.entity.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class JvForecastServicesImpl<T> extends ProductionServicesImpl<T> implements JvForecastServices<T> {

    private static final Logger log = Logger.getLogger(JvForecastServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Inject
    GeneralController genController;

    public JvForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        log.info("ForecastBean::setEntityManager() called...");
        return em;
    }

    @Override
    public abstract List<T> findByYearAndMonth(int year, int month);

        
    @Override
    public abstract T computeEntitlement(T forecast);

    @Override
    public abstract T createInstance();

    @Override
    public T computeOpeningStock(T forecast) {
        Forecast prod = (Forecast) getPreviousMonthProduction(forecast);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            Double partnerOpeningStock = prod.getPartnerOpeningStock();
            ((Forecast) forecast).setOpeningStock(openingStock);
            ((Forecast) forecast).setPartnerOpeningStock(partnerOpeningStock);
        } else {
            ((Forecast) forecast).setOpeningStock(0.0);
            ((Forecast) forecast).setPartnerOpeningStock(0.0);
        }
        return forecast;
    }

    @Override
    public T getPreviousMonthProduction(T forecast) {
        int month = ((Forecast) forecast).getPeriodMonth();
        int year = ((Forecast) forecast).getPeriodYear();
        Contract cs = ((Forecast) forecast).getContract();

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
    public T computeGrossProduction(T forecast) {
        Double prodVolume = ((Forecast) forecast).getProductionVolume();
        int periodYear =((Forecast)forecast).getPeriodYear();
        int periodMonth=((Forecast)forecast).getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double grossProd = prodVolume * days;

        log.log(Level.INFO, "Gross Forecast=>{0} * {1} = {2}", new Object[]{grossProd, days, grossProd});

        ((Forecast) forecast).setGrossProduction(grossProd);
        return forecast;
    }
    
    @Override
    public T openingStockChanged(T forecast){
         log.log(Level.INFO, "Opening Stock changed {0}...", forecast);
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
        Double availability = ((Forecast) forecast).getAvailability();
        Double partnerAvailability = ((Forecast) forecast).getPartnerAvailability();

        cargoes = (int) (availability / 950000.0);
        partnerCargoes = (int) (partnerAvailability / 950000.0);
        liftableVolume = cargoes * 950000.0;
        partnerLiftableVolume = partnerCargoes * 950000.0;
        
        ((Forecast) forecast).setCargos(cargoes);
        ((Forecast) forecast).setLifting(liftableVolume);
        ((Forecast) forecast).setPartnerCargos(partnerCargoes);
        ((Forecast) forecast).setPartnerLifting(partnerLiftableVolume);
        
        return forecast;
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
}
