/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.util.JV;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@JV
public class JvProductionBean extends ProductionBean {

    private static final Logger log = Logger.getLogger(JvProductionBean.class.getName());

    public JvProductionBean() {
        super();
        log.info("JvProductionBean::constructor activated...");

    }

    @Override
    public Double calculateEntitlement() {
        log.info("JvProductionBean::calculateEntitlement() called...");
        return 2.0;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Production createProductionEntity() {
        log.info("JvProductionBean::createProductionEntity() called...");
        return new JvProduction();
    }

    @Override
    public Production createActualProductionEntity() {
        log.info("JvProductionBean::createActualProductionEntity() called...");
        return new ActualJvProduction();
    }

}
