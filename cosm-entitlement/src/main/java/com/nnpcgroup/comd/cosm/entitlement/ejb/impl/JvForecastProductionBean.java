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

        partnerEntitlement = (grossProd
                * et.getPartnerEquity() * 0.01);

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

}
