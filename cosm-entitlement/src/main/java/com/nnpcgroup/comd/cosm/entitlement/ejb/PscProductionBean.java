/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscProduction;
import com.nnpcgroup.comd.cosm.entitlement.util.PSC;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */

@Stateless
//@PSC
public class PscProductionBean extends ProductionTemplate<PscProduction> {

    private static final Logger log = Logger.getLogger(PscProductionBean.class.getName());

    public PscProductionBean() {
        super(PscProduction.class);
        log.info("PscProductionBean::constructor activated...");
    }

    
    
   @Override
    public PscProduction computeEntitlement(PscProduction production) {
        log.info("PscProductionBean::Calculating PSC Entitlement...");
        return production;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

   @Override
    public PscProduction createInstance() {
        log.info("PscProductionBean::createProductionEntity called...");
        return new PscProduction();
    }

   
    @Override
    public PscProduction computeOpeningStock(PscProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscProduction computeClosingStock(PscProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   // @Override
    public PscProduction findByContractStreamPeriod(int year, int month, ContractStream cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   // @Override
    public List<PscProduction> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
