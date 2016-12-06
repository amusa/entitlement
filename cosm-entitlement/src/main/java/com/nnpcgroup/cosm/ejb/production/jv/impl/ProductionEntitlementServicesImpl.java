/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.production.jv.ProductionEntitlement;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionEntitlementServicesImpl<T extends ProductionEntitlement> extends CommonServicesImpl<T> implements ProductionEntitlementServices<T> {

    private static final Logger LOG = Logger.getLogger(ProductionEntitlementServicesImpl.class.getName());

    public ProductionEntitlementServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
