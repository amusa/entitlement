/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualPscProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscProduction;
import com.nnpcgroup.comd.cosm.entitlement.util.PSC;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */

@Stateless
@PSC
public class PscProductionBean extends ProductionBean {

    private static final Logger log = Logger.getLogger(PscProductionBean.class.getName());

    public PscProductionBean() {
        super();
        log.info("PscProductionBean::constructor activated...");
    }

    
    
   @Override
    public Double calculateEntitlement() {
        log.info("PscProductionBean::calculateEntitlement() called...");
        return 1.0;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

   @Override
    public Production createProductionEntity() {
        log.info("PscProductionBean::createProductionEntity called...");
        return new PscProduction();
    }

   @Override
    public Production createActualProductionEntity() {
        log.info("PscProductionBean::createActualProductionEntity called...");
        return new ActualPscProduction();
    }
}
