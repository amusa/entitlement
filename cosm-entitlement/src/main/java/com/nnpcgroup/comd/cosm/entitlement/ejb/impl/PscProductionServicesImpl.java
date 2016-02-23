/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.PscProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscProduction;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class PscProductionServicesImpl<T extends PscProduction> extends ProductionServicesImpl<T> implements PscProductionServices<T> {

    public PscProductionServicesImpl(Class<T>entityClass) {
        super(entityClass);
    }
    
}
