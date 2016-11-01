/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.production.jv.CarryProductionDetail;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.CarryProductionDetailServices;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(CarryProductionDetailServices.class)
public class CarryProductionDetailServicesImpl extends AlternativeFundingProductionDetailServicesImpl<CarryProductionDetail, CarryContract> implements CarryProductionDetailServices {

    private static final Logger LOG = Logger.getLogger(CarryProductionDetailServicesImpl.class.getName());

    public CarryProductionDetailServicesImpl() {
        super(CarryProductionDetail.class);
    }

    @Override
    public CarryProductionDetail openingStockChanged(CarryProductionDetail production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
