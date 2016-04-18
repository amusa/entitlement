/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.CarryProductionServices;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.production.jv.CarryProduction;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public class CarryProductionServicesImpl extends AlternativeFundingProductionServicesImpl<CarryProduction, CarryContract> implements CarryProductionServices{

    private static final Logger LOG = Logger.getLogger(CarryProductionServicesImpl.class.getName());

    public CarryProductionServicesImpl() {
        super(CarryProduction.class);
    }

    @Override
    public CarryProduction createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

       
}
