/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.PscProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.PscProduction;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.util.COSMPersistence;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author 18359
 */
@Stateless
@Local(PscProductionServices.class)
public class PscProductionServicesImpl extends ProductionServicesImpl<PscProduction> implements PscProductionServices {

    private static final Logger LOG = Logger.getLogger(PscProductionServicesImpl.class.getName());

    @Inject
    @COSMPersistence
    private EntityManager em;

    public PscProductionServicesImpl() {
        super(PscProduction.class);
    }

    public PscProductionServicesImpl(Class<PscProduction> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
