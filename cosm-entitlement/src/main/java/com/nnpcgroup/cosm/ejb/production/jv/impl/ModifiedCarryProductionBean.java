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
public class ModifiedCarryProductionBean extends ModifiedCarryProductionServicesImpl implements ModifiedCarryProductionServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionBean.class.getName());

    public ModifiedCarryProductionBean() {
        super(ModifiedCarryProduction.class);
    }

    @Override
    public ModifiedCarryProduction computeEntitlement(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction computeClosingStock(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction computeAvailability(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction liftingChanged(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction grossProductionChanged(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction computeStockAdjustment(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiedCarryProduction enrich(ModifiedCarryProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
