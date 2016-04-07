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
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 * @param <T>
 */
@Dependent
public abstract class JvAlternativeFundingForecastServicesImpl<T extends AlternativeFundingForecast> extends JvForecastServicesImpl<T> implements JvAlternativeFundingForecastServices<T> {

    private static final Logger LOG = Logger.getLogger(JvAlternativeFundingForecastServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Inject
    GeneralController genController;

    public JvAlternativeFundingForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOG.info("returning entityManager...");
        
        return em;
    }

    @Override
    public T computeOpeningStock(T forecast) {
        Forecast prod = (Forecast) getPreviousMonthProduction(forecast);
        Double openingStock = null;
        Double partnerOpeningStock = null;
        if (prod != null) {
            openingStock = prod.getClosingStock();
            partnerOpeningStock = prod.getPartnerOpeningStock();
            ((Forecast) forecast).setOpeningStock(openingStock);
            ((Forecast) forecast).setPartnerOpeningStock(partnerOpeningStock);
        } else {
            ((Forecast) forecast).setOpeningStock(0.0);
            ((Forecast) forecast).setPartnerOpeningStock(0.0);
        }
        
        LOG.log(Level.INFO, "Own Opening Stock=>{0} Partner Opening Stock=>{1} ", new Object[]{openingStock, partnerOpeningStock});
       
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

        LOG.log(Level.INFO, "Gross Forecast=>{0} * {1} = {2}", new Object[]{grossProd, days, grossProd});

        ((Forecast) forecast).setGrossProduction(grossProd);
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

        LOG.log(Level.INFO, "Own Liftable Vol=>{0}, Own Cargoes=>{1} : Partner Liftable Vol=>{2}, Partner Cargoes=>{3}", new Object[]{liftableVolume, cargoes, partnerLiftableVolume, partnerCargoes});

        return forecast;
    }

    @Override
    public T enrich(T production) {
        LOG.log(Level.INFO, "Enriching forecast {0}...", production);
        return computeClosingStock(
                computeLifting(
                        computeAvailability(
                                computeSharedOil(
                                        computeCarryOil(
                                                computeEntitlement(
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

        LOG.log(Level.INFO, "Own Availability=>{0} : Partner Availability=>{1}", new Object[]{availability, partnerAvailability});

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

        LOG.log(Level.INFO, "Shared Oil = ( NNPC Equity - Carry Oil ) * Shared Oil Ratio => ( {0} - {1} ) * {2} = {3}", new Object[]{ownEquity, carryOil, sharedOil});

        return forecast;
    }

    @Override
    public T computeCarryOil(T forecast) {
        double carryOil;
        double residualCarryOil = computeResidualCarryOil(forecast);
        double guaranteedNotionalMargin = computeGuaranteedNotionalMargin(forecast);

        carryOil = residualCarryOil / guaranteedNotionalMargin;

        forecast.setCarryOil(carryOil);

        LOG.log(Level.INFO, "Carry Oil = Residual Carry Oil / GNM => {0} / {1} = {2}", new Object[]{residualCarryOil, guaranteedNotionalMargin, carryOil});

        return forecast;
    }

    private Double computeGuaranteedNotionalMargin(T forecast) {
        Double gnm = 4.50; //TODO:temporary placeholder

        LOG.log(Level.INFO, "Guaranteed National Margin (GNM)=>{0}", gnm);

        return gnm;
    }

    private Double computeResidualCarryOil(T forecast) {
        double residualOil;
        double ccca = computeCapitalCarryCostArmotized(forecast);
        double taxRelief = computeTaxRelief(forecast);

        residualOil = ccca - taxRelief;

        LOG.log(Level.INFO, "Residual Oil = CCCA - Tax Relief => {0} - {1} = {2}", new Object[]{ccca, taxRelief, residualOil});

        return residualOil;
    }

    private Double computeTaxRelief(T forecast) {
        double taxRelief;
        double carryTaxExpenditure = computeCarryTaxExpenditure(forecast);
        taxRelief = carryTaxExpenditure * 0.85;

        LOG.log(Level.INFO, "Tax Relief = CTE * 85% => {0} * 0.85 = {1}", new Object[]{carryTaxExpenditure, taxRelief});

        return taxRelief;
    }

    private Double computeCapitalCarryCostArmotized(T forecast) {
        double ccca;
        double ccc = computeCarryCapitalCost(forecast);
        ccca = ccc * 0.20;

        LOG.log(Level.INFO, "Capital Carry Cost Armotized (CCCA) = CCC * 20% => {0} * 0.20 = {1}", new Object[]{ccc, ccca});

        return ccca;
    }

    private Double computeCarryTaxExpenditure(T forecast) {
        Double ccca;
        Double pia;

        ccca = computeCapitalCarryCostArmotized(forecast);
        pia = computePetroleumInvestmentAllowance(forecast);

        Double cte = ccca + pia;

        LOG.log(Level.INFO, "Carry Tax Expenditure (CTE) = CCCA + PIA => {0} + {1} = {2}", new Object[]{ccca, pia, cte});

        return cte;
    }

    private Double computePetroleumInvestmentAllowance(T forecast) {
        Double tangibleCost = computeTangibleCost(forecast);
        Double pia = tangibleCost * 0.10;

        LOG.log(Level.INFO, "Tangible Cost = Tangible * 10% => {0} * 0.10 = {1}", new Object[]{tangibleCost, pia});

        return pia;
    }

    private double computeCarryCapitalCost(T forecast) {
        Double tangibleCost = forecast.getTangibleCost();
        Double intangibleCost = forecast.getIntangibleCost();

        Double ccca = tangibleCost + intangibleCost;

        LOG.log(Level.INFO, "Carry Tax Expenditure (CTE) => {0} + {1} = {2}", new Object[]{tangibleCost, intangibleCost, ccca});

        return ccca;
    }

    private double computeTangibleCost(T forecast) {

        LOG.log(Level.INFO, "Tangible Cost => {0}", new Object[]{forecast.getTangibleCost()});

        return forecast.getTangibleCost();
    }

}
