/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.ejb.production.jv.impl.CarryProductionDetailServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.impl.ModifiedCarryProductionDetailServicesImpl;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetail;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionDetailServices;
import com.nnpcgroup.cosm.ejb.production.jv.impl.JvProductionDetailServicesImpl;
import com.nnpcgroup.cosm.ejb.production.jv.impl.ProductionDetailServicesImpl;

/**
 *
 * @author 18359
 */
public enum ProductionServiceName {    
    //REGULAR(ProductionDetailServicesImpl.class),
    CARRY(CarryProductionDetailServicesImpl.class),
    MODIFIEDCARRY(ModifiedCarryProductionDetailServicesImpl.class);
        

    private Class<? extends ProductionDetailServices<? extends ProductionDetail, ? extends Contract>> productionServiceType;

    private ProductionServiceName(Class<? extends ProductionDetailServices<? extends ProductionDetail, ? extends Contract>> productionServiceType) {
        this.productionServiceType = productionServiceType;
    }

    public Class<? extends ProductionDetailServices<? extends ProductionDetail, ? extends Contract>> getProductionServiceType() {
        return productionServiceType;
    }
}
