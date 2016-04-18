/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import com.nnpcgroup.cosm.ejb.production.jv.impl.CarryProductionServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.impl.ModifiedCarryProductionServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.impl.RegularProductionServicesImpl;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.Production;

/**
 *
 * @author 18359
 */
public enum ProductionServiceName {    
    REGULAR(RegularProductionServicesImpl.class),
    CARRY(CarryProductionServicesImpl.class),
    MODIFIEDCARRY(ModifiedCarryProductionServicesImpl.class);
        

    private Class<? extends JvProductionServices<? extends Production, ? extends Contract>> productionServiceType;

    private ProductionServiceName(Class<? extends JvProductionServices<? extends Production, ? extends Contract>> productionServiceType) {
        this.productionServiceType = productionServiceType;
    }

    public Class<? extends JvProductionServices<? extends Production, ? extends Contract>> getProductionServiceType() {
        return productionServiceType;
    }
}
