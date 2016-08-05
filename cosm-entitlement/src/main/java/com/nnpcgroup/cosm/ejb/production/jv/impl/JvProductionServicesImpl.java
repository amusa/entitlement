/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public abstract class JvProductionServicesImpl<T extends Production, E extends Contract> extends CommonServicesImpl<T> implements JvProductionServices<T, E> {

    private static final Logger LOG = Logger.getLogger(JvProductionServicesImpl.class.getName());

    @EJB
    FiscalArrangementBean fiscalBean;

    public JvProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T enrich(T production) throws NoRealizablePriceException {
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
    public T computeEntitlement(T production) {
        LOG.info("computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

//        fa = production.getContract().getFiscalArrangement();
        fa = fiscalBean.find(production.getFiscalArrangementId());

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;

        Double netProduction = production.getNetProduction() != null ? production.getNetProduction() : 0;

        ownEntitlement = (netProduction
                * et.getOwnEquity() * 0.01);
        LOG.log(Level.INFO, "Own Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{netProduction, et.getOwnEquity(), ownEntitlement});

        partnerEntitlement = (netProduction
                * et.getPartnerEquity() * 0.01);
        LOG.log(Level.INFO, "Partner Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{netProduction, et.getPartnerEquity(), partnerEntitlement});

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
        Double overlift = production.getOverlift() != null ? production.getOverlift() : 0.0;
        Double partnerOverlift = production.getPartnerOverlift() != null ? production.getPartnerOverlift() : 0.0;

        availability = ownEntitlement + openingStock + overlift;
        partnerAvailability = partnerEntitlement + partnerOpeningStock + partnerOverlift;

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
                        computeOverlift(
                                computeClosingStock(
                                        computeAvailability(
                                                overLiftReset(production)
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public T computeOverlift(T production) {
        Double closingStock = production.getClosingStock();

        if (closingStock < 0) {
            production.setClosingStock(0.0);
            production.setOverlift(-1 * closingStock);
            production.setPartnerOverlift(closingStock);
        }

        Double partnerClosingStock = production.getPartnerClosingStock();

        if (partnerClosingStock < 0) {
            production.setPartnerClosingStock(0.0);
            production.setPartnerOverlift(-1 * partnerClosingStock);
            production.setOverlift(partnerClosingStock);
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
                                                overLiftReset(production)
                                        )
                                )
                        )
                )
        );
    }

    public T overLiftReset(T production) {
        production.setOverlift(null);
        production.setPartnerOverlift(null);
        return production;
    }

    @Override
    public T computeLifting(T production) {
        Double liftableVolume, partnerLiftableVolume;
        Integer cargoes, partnerCargoes;
        Double availability = production.getAvailability();
        Double partnerAvailability = production.getPartnerAvailability();

        cargoes = (int) (availability / 950000.0);
        partnerCargoes = (int) (partnerAvailability / 950000.0);
        liftableVolume = cargoes * 950000.0;
        partnerLiftableVolume = partnerCargoes * 950000.0;

        production.setCargos(cargoes);
        production.setLifting(liftableVolume);
        production.setPartnerCargos(partnerCargoes);
        production.setPartnerLifting(partnerLiftableVolume);

        return production;
    }

    @Override
    public T getPreviousMonthProduction(T production) {
        int month = production.getPeriodMonth();
        int year = production.getPeriodYear();

        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);

        //T prod = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);
        T prod = find(new ProductionPK(prevFp.getYear(), prevFp.getMonth(), production.getFiscalArrangementId(), production.getCrudeTypeCode()));

        return prod;

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

    @Override
    public T computeOpeningStock(T production) {
        Production prod = getPreviousMonthProduction(production);
        if (prod != null) {
            Double openingStock = prod.getClosingStock();
            Double partnerOpeningStock = prod.getPartnerOpeningStock();
            production.setOpeningStock(openingStock);
            production.setPartnerOpeningStock(partnerOpeningStock);
        } else {
            production.setOpeningStock(0.0);
            production.setPartnerOpeningStock(0.0);
        }
        return production;
    }
}
