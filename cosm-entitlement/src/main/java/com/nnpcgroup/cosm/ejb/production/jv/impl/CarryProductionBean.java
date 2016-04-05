/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.CarryProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.CarryProduction;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(CarryProductionServices.class)
public class CarryProductionBean extends CarryProductionServicesImpl implements CarryProductionServices {

    private static final Logger LOG = Logger.getLogger(CarryProductionBean.class.getName());

    @Override
    public CarryProduction createInstance() {
        return new CarryProduction();
    }

    
}
