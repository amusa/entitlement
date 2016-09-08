/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionServices;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProduction;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public  class ModifiedCarryProductionServicesImpl extends AlternativeFundingProductionServicesImpl<ModifiedCarryProduction, ModifiedCarryContract> implements ModifiedCarryProductionServices{

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionServicesImpl.class.getName());

    public ModifiedCarryProductionServicesImpl(Class<ModifiedCarryProduction> entityClass) {
        super(entityClass);
    }

    //@Override
    public ModifiedCarryProduction createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
