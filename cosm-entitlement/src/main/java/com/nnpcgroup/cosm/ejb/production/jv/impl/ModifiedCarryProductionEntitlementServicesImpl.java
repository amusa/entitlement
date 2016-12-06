/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ModifiedCarryProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.production.jv.ModifiedCarryProductionEntitlement;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryProductionEntitlementServices.class)
public class ModifiedCarryProductionEntitlementServicesImpl extends AlternativeFundingProductionEntitlementServicesImpl<ModifiedCarryProductionEntitlement, ModifiedCarryContract> implements ModifiedCarryProductionEntitlementServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryProductionEntitlementServicesImpl.class.getName());

    public ModifiedCarryProductionEntitlementServicesImpl() {
        super(ModifiedCarryProductionEntitlement.class);
    }

    @Override
    public ModifiedCarryProductionEntitlement openingStockChanged(ModifiedCarryProductionEntitlement production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
