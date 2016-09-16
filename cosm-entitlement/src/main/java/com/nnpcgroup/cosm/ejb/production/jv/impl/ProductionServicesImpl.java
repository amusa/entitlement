/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.Production;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionServicesImpl<T extends Production> extends AbstractCrudServicesImpl<T> implements ProductionServices<T> {

    private static final Logger LOG = Logger.getLogger(ProductionServicesImpl.class.getName());

    public ProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
