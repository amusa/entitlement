/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.JvForecastProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.EquityType;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvForecastProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastProductionServices.class)
public class JvForecastProductionBean extends JvProductionServicesImpl<JvForecastProduction> implements JvForecastProductionServices {

    private static final Logger log = Logger.getLogger(JvForecastProductionBean.class.getName());

    public JvForecastProductionBean() {
        super(JvForecastProduction.class);
        log.info("JvProductionBean::constructor activated...");

    }

    @Override
    public JvForecastProduction createInstance() {
        log.info("JvProductionBean::createProductionEntity() called...");
        return new JvForecastProduction();
    }

    @Override
    public List<JvForecastProduction> findByYearAndMonth(int year, int month) {
        log.log(Level.INFO, "Parameters: year={0}, month={1}", new Object[]{year, month});

        List<JvForecastProduction> productions = getEntityManager().createQuery(
                "SELECT p FROM Production p WHERE p.periodYear = :year and p.periodMonth = :month and TYPE(p) = JvForecastProduction")
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return productions;
    }

    @Override
    public JvForecastProduction computeEntitlement(JvForecastProduction production) {
        log.info("JvProductionBean::computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContractStream().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;
        Double grossProd = production.getGrossProduction();

        grossProd = grossProd == null ? 0 : grossProd;

        ownEntitlement = (grossProd
                * et.getOwnEquity() * 0.01);
        log.log(Level.INFO, "Own Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{grossProd, et.getOwnEquity(), ownEntitlement});

        partnerEntitlement = (grossProd
                * et.getPartnerEquity() * 0.01);
        log.log(Level.INFO, "Partner Entitlement=>{0} * {1} * 0.01 = {2}", new Object[]{grossProd, et.getPartnerEquity(), partnerEntitlement});

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public JvForecastProduction enrich(JvForecastProduction production) {
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

    @Override
    public JvForecastProduction computeAvailability(JvForecastProduction production) {
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
    public JvForecastProduction computeClosingStock(JvForecastProduction production) {
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
}
