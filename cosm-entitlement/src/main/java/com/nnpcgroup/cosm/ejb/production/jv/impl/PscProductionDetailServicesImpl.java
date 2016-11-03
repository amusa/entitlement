/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.production.jv.PscProductionDetailServices;
import java.util.logging.Logger;
import javax.ejb.EJB;
import com.nnpcgroup.cosm.entity.production.jv.PscProductionDetail;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class PscProductionDetailServicesImpl<T extends PscProductionDetail> extends ProductionDetailServicesImpl<T> implements PscProductionDetailServices<T> {

    private static final Logger LOG = Logger.getLogger(PscProductionDetailServicesImpl.class.getName());

    @EJB
    FiscalArrangementBean fiscalBean;

    public PscProductionDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    

    
}
