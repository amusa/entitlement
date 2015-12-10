/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.ActualJvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
public class JvProductionBean extends ProductionBean {

    @Override
    public Double calculateEntitlement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Production createProductionEntity() {
        return new JvProduction();
    }

    @Override
    public Production createActualProductionEntity() {
        return new ActualJvProduction();
    }

}
