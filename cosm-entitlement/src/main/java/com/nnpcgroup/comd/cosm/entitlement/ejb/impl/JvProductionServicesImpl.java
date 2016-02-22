/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.JvProductionServices;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class JvProductionServicesImpl<T> extends ProductionServicesImpl<T> implements JvProductionServices<T> {

    public JvProductionServicesImpl(Class<T>entityClass) {
        super(entityClass);
    }
    
}
