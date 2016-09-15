/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetail;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionDetailServices;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public abstract class ProductionDetailServicesImpl<T extends ProductionDetail, E extends Contract> extends CommonServicesImpl<T> implements ProductionDetailServices<T, E> {

    private static final Logger LOG = Logger.getLogger(ProductionDetailServicesImpl.class.getName());

    public ProductionDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
