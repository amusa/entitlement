/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.ActualPscProduction;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
//@PSC
public class PscProductionBean extends ProductionTemplate<ActualPscProduction> {

    private static final Logger log = Logger.getLogger(PscProductionBean.class.getName());

    public PscProductionBean() {
        super(ActualPscProduction.class);
        log.info("PscProductionBean::constructor activated...");
    }

    @Override
    public ActualPscProduction computeEntitlement(ActualPscProduction production) {
        log.info("PscProductionBean::Calculating PSC Entitlement...");
        return production;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public ActualPscProduction createInstance() {
        log.info("PscProductionBean::createProductionEntity called...");
        return new ActualPscProduction();
    }

    @Override
    public ActualPscProduction computeOpeningStock(ActualPscProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActualPscProduction computeClosingStock(ActualPscProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // @Override
    public ActualPscProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ActualPscProduction> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActualPscProduction computeGrossProduction(ActualPscProduction production) {
        Double prodVolume = production.getProductionVolume();
        Double grossProd = prodVolume * 30; //TODO:Calculate days for each month
        production.setGrossProduction(grossProd);
        return production;
    }

}
