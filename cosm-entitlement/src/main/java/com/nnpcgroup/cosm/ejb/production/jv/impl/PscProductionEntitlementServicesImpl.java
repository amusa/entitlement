/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.production.jv.PscProductionEntitlementServices;
import java.util.logging.Logger;
import javax.ejb.EJB;
import com.nnpcgroup.cosm.entity.production.jv.PscProductionEntitlement;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class PscProductionEntitlementServicesImpl<T extends PscProductionEntitlement> extends ProductionEntitlementServicesImpl<T> implements PscProductionEntitlementServices<T> {

    private static final Logger LOG = Logger.getLogger(PscProductionEntitlementServicesImpl.class.getName());

    @EJB
    FiscalArrangementBean fiscalBean;

    public PscProductionEntitlementServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
