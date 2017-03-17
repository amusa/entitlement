/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetail;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.ProductionDetailServices;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class ProductionDetailServicesImpl<T extends ProductionDetail> extends CommonServicesImpl<T> implements ProductionDetailServices<T> {

    private static final Logger LOG = Logger.getLogger(ProductionDetailServicesImpl.class.getName());

    
    @Inject
    GeneralController genController;

    @EJB
    FiscalArrangementBean fiscalBean;

    public ProductionDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
