/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.*;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.contract.RegularContract;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author 18359
 * @param <T>
 */
@Dependent
public abstract class JvForecastServicesImpl<T extends Forecast> extends CommonServicesImpl<T> implements JvForecastServices<T>, Serializable {

    private static final Logger LOG = Logger.getLogger(JvForecastServicesImpl.class.getName());

    @Inject
    GeneralController genController;

    public JvForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T computeOpeningStock(T forecast) {
        Forecast prod = getPreviousMonthProduction(forecast);
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

        LOG.log(Level.INFO, "Gross Forecast = DailyProd * Days => {0} * {1} = {2}", new Object[]{prodVolume, days, grossProd});

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
    public T enrich(T production) {
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
        Contract cs = forecast.getContract();
        ContractPK cPK = new ContractPK();
        cPK.setFiscalArrangementId(cs.getFiscalArrangement().getId());
        cPK.setCrudeTypeCode(cs.getCrudeType().getCode());
        LOG.log(Level.INFO,"class of forecast Contract {0} is {1}", new Object[]{cs, cs.getClass()});
       // Contract contract = cs;
        
//        if (cs instanceof RegularContract) {
//            FiscalArrangement fa = new FiscalArrangement(cs.getFiscalArrangement().getId());
//            CrudeType ct = new CrudeType(cs.getCrudeType().getCode());
//             contract = new RegularContract(fa, ct);
//        } else if (cs instanceof CarryContract) {
//            contract = new CarryContract(cs.getFiscalArrangement(), cs.getCrudeType());
//        } else if (contract instanceof ModifiedCarryContract) {
//            contract = new ModifiedCarryContract(cs.getFiscalArrangement(), cs.getCrudeType());
//        }

        FiscalPeriod prevFp = getPreviousFiscalPeriod(year, month);

        T f = find(new ForecastPK(prevFp.getYear(), prevFp.getMonth(), cs.getFiscalArrangement().getId(), cs.getCrudeType().getCode()));
        //T f = findByContractPeriod(prevFp.getYear(), prevFp.getMonth(), cs);

        return f;
    }

}
