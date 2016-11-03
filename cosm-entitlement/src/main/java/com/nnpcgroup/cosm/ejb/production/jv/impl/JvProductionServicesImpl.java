/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionServices;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.entity.production.jv.JvProduction;
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
@Local(JvProductionServices.class)
public class JvProductionServicesImpl extends ProductionServicesImpl<JvProduction> implements JvProductionServices {

    private static final Logger LOG = Logger.getLogger(JvProductionServicesImpl.class.getName());

    @Inject
    @COSMPersistence
    private EntityManager em;

    public JvProductionServicesImpl() {
        super(JvProduction.class);
    }

    public JvProductionServicesImpl(Class<JvProduction> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
