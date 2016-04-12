/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvAlternativeFundingForecastServices;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
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
    private static final long serialVersionUID = -5826414842990437262L;

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

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

        sharedOil = (ownEquity - carryOil) * sharedOilRatio * 0.01;
        forecast.setSharedOil(sharedOil);

        LOG.log(Level.INFO, "Shared Oil = ( NNPC Equity - Carry Oil ) * Shared Oil Ratio => ( {0} - {1} ) * {2} * 0.01 = {3}", new Object[]{ownEquity, carryOil, sharedOilRatio, sharedOil});

        return forecast;
    }

    @Override
    public T computeCarryOil(T forecast) {
        double carryOil;
        double RCE = computeResidualCarryExpenditure(forecast);
        double IGNM = computeGuaranteedNotionalMargin(forecast);

        carryOil = RCE / IGNM;

        forecast.setCarryOil(carryOil);

        LOG.log(Level.INFO, "Carry Oil = RCE / IGNM => {0} / {1} = {2}", new Object[]{RCE, IGNM, carryOil});

        return forecast;
    }

    private Double computeGuaranteedNotionalMargin(T forecast) {
        Double GNM = 4.1465; //TODO:temporary placeholder

        LOG.log(Level.INFO, "Guaranteed National Margin (GNM)=>{0}", GNM);

        return GNM;
    }

    private Double computeResidualCarryExpenditure(T forecast) {
        double RCE;
        double CTE = computeCarryTaxExpenditure(forecast);
        double CTR = computeCarryTaxRelief(forecast);

        RCE = CTE - CTR;

        LOG.log(Level.INFO, "RCE = CTE - CTR => {0} - {1} = {2}", new Object[]{CTE, CTR, RCE});

        return RCE;
    }

    private Double computeCarryTaxRelief(T forecast) {
        double CTR;
        double CTE = computeCarryTaxExpenditure(forecast);
        CTR = CTE * 0.85;

        LOG.log(Level.INFO, "CTR = CTE * 85% => {0} * 0.85 = {1}", new Object[]{CTE, CTR});

        return CTR;
    }

    private Double computeCapitalCarryCostArmotized(T forecast) {
        double CCCA;
        double tangible = computeTangibleCost(forecast);
        double intangible = computeIntangibleCost(forecast);
        CCCA = (tangible * 0.20) + intangible;

        LOG.log(Level.INFO, "Capital Carry Cost Armotized (CCCA) = Tangible * 20% + Intangible => {0} * 0.20 + {1} = {2}", new Object[]{tangible, intangible, CCCA});

        return CCCA;
    }

    private Double computeCarryTaxExpenditure(T forecast) {
        Double CCCA;
        Double PIA;

        CCCA = computeCapitalCarryCostArmotized(forecast);
        PIA = computePetroleumInvestmentAllowance(forecast);

        Double CTE = CCCA + PIA;

        LOG.log(Level.INFO, "Carry Tax Expenditure (CTE) = CCCA + PIA => {0} + {1} = {2}", new Object[]{CCCA, PIA, CTE});

        return CTE;
    }

    private Double computePetroleumInvestmentAllowance(T forecast) {
        Double tangibleCost = computeTangibleCost(forecast);
        Double PIA = tangibleCost * 0.10;

        LOG.log(Level.INFO, "Tangible Cost = Tangible * 10% => {0} * 0.10 = {1}", new Object[]{tangibleCost, PIA});

        return PIA;
    }

    private double computeCarryCapitalCost(T forecast) {
        Double tangibleCost = forecast.getTangibleCost();
        Double intangibleCost = forecast.getIntangibleCost();

        Double CCC = tangibleCost + intangibleCost;

        LOG.log(Level.INFO, "Carry Capital Cost (CCC) = Tangible + Intangible => {0} + {1} = {2}", new Object[]{tangibleCost, intangibleCost, CCC});

        return CCC;
    }

    private double computeTangibleCost(T forecast) {

        LOG.log(Level.INFO, "Tangible Cost => {0}", new Object[]{forecast.getTangibleCost()});

        return forecast.getTangibleCost();
    }

    private double computeIntangibleCost(T forecast) {

        LOG.log(Level.INFO, "Intangible Cost => {0}", new Object[]{forecast.getIntangibleCost()});

        return forecast.getIntangibleCost();
    }
}
