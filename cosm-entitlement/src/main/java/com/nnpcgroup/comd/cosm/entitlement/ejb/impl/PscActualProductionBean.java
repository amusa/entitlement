/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.PscActualProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscActualProduction;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(PscActualProductionServices.class)
public class PscActualProductionBean extends PscProductionServicesImpl<PscActualProduction> implements PscActualProductionServices {

    private static final Logger log = Logger.getLogger(PscActualProductionBean.class.getName());

    public PscActualProductionBean() {
        super(PscActualProduction.class);
        log.info("PscProductionBean::constructor activated...");
    }

    @Override
    public PscActualProduction computeEntitlement(PscActualProduction production) {
        log.info("PscProductionBean::Calculating PSC Entitlement...");
        return production;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public PscActualProduction createInstance() {
        log.info("PscProductionBean::createProductionEntity called...");
        return new PscActualProduction();
    }

    @Override
    public PscActualProduction computeOpeningStock(PscActualProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscActualProduction computeClosingStock(PscActualProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscActualProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PscActualProduction> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscActualProduction computeGrossProduction(PscActualProduction production) {
        Double prodVolume = production.getProductionVolume();
        Double grossProd = prodVolume * 30; //TODO:Calculate days for each month
        production.setGrossProduction(grossProd);
        return production;
    }

}
