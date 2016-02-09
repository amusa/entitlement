/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.EquityType;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.JointVenture;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.util.JVACTUAL;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@JVACTUAL
public class JvActualProductionBean extends ProductionBean {

    private static final Logger log = Logger.getLogger(JvActualProductionBean.class.getName());

    public JvActualProductionBean() {
        super();
        log.info("JvActualProductionBean::constructor activated...");

    }

    @Override
    public Production createInstance() {
        log.info("JvActualProductionBean::createProductionEntity() called...");
        return new ActualJvProduction();
    }
    
    @Override
    public Production computeOpeningStock(Production production) {
        log.info("JvActualProductionBean::computing Opening stock...");
        production.setOpeningStock(0.0);//TODO:compute JV Production opening stock
        return production;
    }

    @Override
    public Production computeEntitlement(Production production) {
        log.info("JvActualProductionBean::computing Entitlement...");
        FiscalArrangement fa;
        JointVenture jv;

        fa = production.getContractStream().getFiscalArrangement();

        assert (fa instanceof JointVenture);

        jv = (JointVenture) fa;
        EquityType et = jv.getEquityType();

        Double ownEntitlement;
        Double partnerEntitlement;

        ownEntitlement = (production.getProductionVolume()
                + production.getOpeningStock())
                * et.getOwnEquity() * 0.01;

        partnerEntitlement = (production.getProductionVolume()
                + production.getOpeningStock())
                * et.getPartnerEquity() * 0.01;

        production.setOwnShareEntitlement(ownEntitlement);
        production.setPartnerShareEntitlement(partnerEntitlement);

        return production;
    }

    @Override
    public Production computeClosingStock(Production production) {
        log.info("JvActualProductionBean::computing Closing stock...");
        return production;
    }

}
