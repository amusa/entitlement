/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProduction;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
public abstract class ModifiedCarryProductionServicesImpl extends AlternativeFundingProductionServicesImpl<ModifiedCarryProduction> implements ModifiedCarryProductionServices{

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionServicesImpl.class.getName());

    public ModifiedCarryProductionServicesImpl(Class<ModifiedCarryProduction> entityClass) {
        super(entityClass);
    }

    
}
