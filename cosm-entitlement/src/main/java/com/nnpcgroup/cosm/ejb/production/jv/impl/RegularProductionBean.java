/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.RegularProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.RegularProduction;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(RegularProductionServices.class)
public class RegularProductionBean extends RegularProductionServicesImpl implements RegularProductionServices{

    private static final Logger LOG = Logger.getLogger(RegularProductionBean.class.getName());

    public RegularProductionBean() {
        super(RegularProduction.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public RegularProduction createInstance() {
        LOG.info("JvActualProductionBean::Creating new JvActualProduction Instance...");
        return new RegularProduction();
    }
}