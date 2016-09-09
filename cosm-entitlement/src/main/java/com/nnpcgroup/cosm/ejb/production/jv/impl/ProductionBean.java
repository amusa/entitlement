/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProduction;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.RegularProduction;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvProduction.class)
public class ProductionBean extends JvProductionServicesImpl<Production, Contract> implements JvProduction {

    private static final Logger LOG = Logger.getLogger(ProductionBean.class.getName());

    public ProductionBean() {
        super(Production.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    //@Override
    public Production createInstance() {
        LOG.info("JvActualProductionBean::Creating new JvActualProduction Instance...");
        return new RegularProduction();
    }

    
}
