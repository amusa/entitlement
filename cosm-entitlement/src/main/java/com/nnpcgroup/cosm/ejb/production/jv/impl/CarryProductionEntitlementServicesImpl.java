/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.CarryContract;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.CarryProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.production.jv.CarryProductionEntitlement;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(CarryProductionEntitlementServices.class)
public class CarryProductionEntitlementServicesImpl extends AlternativeFundingProductionEntitlementServicesImpl<CarryProductionEntitlement, CarryContract> implements CarryProductionEntitlementServices {

    private static final Logger LOG = Logger.getLogger(CarryProductionEntitlementServicesImpl.class.getName());

    public CarryProductionEntitlementServicesImpl() {
        super(CarryProductionEntitlement.class);
    }

    @Override
    public CarryProductionEntitlement openingStockChanged(CarryProductionEntitlement production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
