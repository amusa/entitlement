/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc.impl;

import com.nnpcgroup.cosm.ejb.forecast.psc.OilFieldServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;

import java.io.Serializable;
import com.nnpcgroup.cosm.entity.OilField;
import com.nnpcgroup.cosm.util.COSMPersistence;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author 18359
 */
@Stateless
@Local(OilFieldServices.class)
public class OilFieldServicesImpl extends AbstractCrudServicesImpl<OilField> implements OilFieldServices, Serializable {

    @Inject
    @COSMPersistence
    private EntityManager em;

    public OilFieldServicesImpl() {
        super(OilField.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
