/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualPscProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscProduction;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
public class PscProductionBean extends ProductionBean {

    @Override
    public Double calculateEntitlement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Production createProductionEntity() {
        return new PscProduction();
    }

    @Override
    public Production createActualProductionEntity() {
        return new ActualPscProduction();
    }
}
