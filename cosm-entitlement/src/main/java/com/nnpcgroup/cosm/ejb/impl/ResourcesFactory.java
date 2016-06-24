package com.nnpcgroup.cosm.ejb.impl;

import com.nnpcgroup.cosm.util.COSMPersistence;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by maliska on 6/21/16.
 */

@Dependent
public class ResourcesFactory{
    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @COSMPersistence
    @Produces
    public EntityManager produceForecastBean() {
        return em;
    }

}
