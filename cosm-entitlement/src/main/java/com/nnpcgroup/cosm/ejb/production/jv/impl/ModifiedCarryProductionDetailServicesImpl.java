/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProductionDetail;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionDetailServices;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryProductionDetailServices.class)
public class ModifiedCarryProductionDetailServicesImpl extends AlternativeFundingProductionDetailServicesImpl<ModifiedCarryProductionDetail, ModifiedCarryContract> implements ModifiedCarryProductionDetailServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionDetailServicesImpl.class.getName());

    public ModifiedCarryProductionDetailServicesImpl() {
        super(ModifiedCarryProductionDetail.class);
    }

    @Override
    public ModifiedCarryProductionDetail openingStockChanged(ModifiedCarryProductionDetail production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
