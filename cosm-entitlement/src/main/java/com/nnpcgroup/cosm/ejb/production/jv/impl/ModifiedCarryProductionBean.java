/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProduction;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryProductionServices.class)
public class ModifiedCarryProductionBean extends ModifiedCarryProductionServicesImpl implements ModifiedCarryProductionServices{

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionBean.class.getName());

    public ModifiedCarryProductionBean() {
        super(ModifiedCarryProduction.class);
        LOG.info("ProductionBean::constructor activated...");

    }

   
   
   
    
}
