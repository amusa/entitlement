/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvAlternativeFundingForecastServices;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
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
public abstract class JvAlternativeFundingForecastServicesImpl<T extends AlternativeFundingForecast> extends JvForecastServicesImpl<T> implements JvAlternativeFundingForecastServices<T> {

    private static final Logger log = Logger.getLogger(JvAlternativeFundingForecastServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Inject
    GeneralController genController;

    public JvAlternativeFundingForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        log.info("ForecastBean::setEntityManager() called...");
        return em;
    }

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
        int periodYear = ((Forecast) forecast).getPeriodYear();
        int periodMonth = ((Forecast) forecast).getPeriodMonth();
        int days = genController.daysOfMonth(periodYear, periodMonth);
        Double grossProd = prodVolume * days;

        log.log(Level.INFO, "Gross Forecast=>{0} * {1} = {2}", new Object[]{grossProd, days, grossProd});

        ((Forecast) forecast).setGrossProduction(grossProd);
        return forecast;
    }

    @Override
    public T openingStockChanged(T forecast) {
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
        log.log(Level.INFO, "Enriching forecast {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeEntitlement(
                                        computeCarryOil(
                                                computeSharedOil(
                                                        computeGrossProduction(
                                                                computeOpeningStock(production)
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public T computeAvailability(T production) {
        Double availability, partnerAvailability;
        Double ownEntitlement = production.getOwnShareEntitlement();
        Double partnerEntitlement = production.getPartnerShareEntitlement();
        Double openingStock = production.getOpeningStock();
        Double partnerOpeningStock = production.getPartnerOpeningStock();
        Double sharedOil = production.getSharedOil();
        Double carryOil = production.getCarryOil();
        Double carrySharedOil = sharedOil + carryOil;

        availability = ownEntitlement + openingStock - carrySharedOil;
        partnerAvailability = partnerEntitlement + partnerOpeningStock + carrySharedOil;

        production.setAvailability(availability);
        production.setPartnerAvailability(partnerAvailability);

        return production;
    }

    @Override
    public T computeSharedOil(T forecast) {
        double sharedOil;
        double ownEquity = forecast.getOwnShareEntitlement();
        double carryOil = forecast.getCarryOil();

        Contract contract = forecast.getContract();
        assert (contract instanceof AlternativeFundingContract);
        AlternativeFundingContract afContract = (AlternativeFundingContract) contract;
        double sharedOilRatio = afContract.getSharedOilRatio();

        sharedOil = (ownEquity - carryOil) * sharedOilRatio;
        forecast.setSharedOil(sharedOil);
        return forecast;
    }

    @Override
    public T computeCarryOil(T forecast) {
        double carryOil;
        double residualCarryOil = computeResidualCarryOil(forecast);
        double guaranteedNationalMargin = computeGuaranteedNationalMargin(forecast);
        carryOil = residualCarryOil / guaranteedNationalMargin;

        forecast.setCarryOil(carryOil);
        return forecast;
    }

    private Double computeGuaranteedNationalMargin(T forecast) {
        return 10.0; //TODO:temporary placeholder
    }

    private Double computeResidualCarryOil(T forecast) {
        double residualOil;
        double ccca = computeCapitalCarryCostArmotized(forecast);
        double taxRelief = computeTaxRelief(forecast);

        residualOil = ccca - taxRelief;
        return residualOil;
    }

    private Double computeTaxRelief(T forecast) {
        double taxRelief;
        double carryTaxExpenditure = computeCarryTaxExpenditure(forecast);
        taxRelief = carryTaxExpenditure * 0.85;
        return taxRelief;
    }

    private Double computeCapitalCarryCostArmotized(T forecast) {
        double ccca;
        double ccc = computeCarryCapitalCost(forecast);
        ccca = ccc * 0.20;

        return ccca;
    }

    private Double computeCarryTaxExpenditure(T forecast) {
        return computeCapitalCarryCostArmotized(forecast) + computePetroleumInvestmentAllowance(forecast);
    }

    private Double computePetroleumInvestmentAllowance(T forecast) {
        return computeTangibleCost(forecast) * 0.10;
    }

    private double computeCarryCapitalCost(T forecast) {
        return forecast.getTangibleCost() + forecast.getIntangibleCost();
    }

    private double computeTangibleCost(T forecast) {
        return forecast.getTangibleCost();
    }

}
