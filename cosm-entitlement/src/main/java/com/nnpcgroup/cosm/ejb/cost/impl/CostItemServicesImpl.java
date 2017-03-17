/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.cost.impl;

import com.nnpcgroup.cosm.ejb.cost.CostItemServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.ejb.lifting.LiftingServices;
import com.nnpcgroup.cosm.entity.cost.CostItem;
import com.nnpcgroup.cosm.util.COSMPersistence;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author 18359
 */
@Stateless
@Local(CostItemServices.class)
public class CostItemServicesImpl extends AbstractCrudServicesImpl<CostItem> implements CostItemServices {

    @Inject
    @COSMPersistence
    private EntityManager em;

    public CostItemServicesImpl() {
        super(CostItem.class);
    }

    public CostItemServicesImpl(Class<CostItem> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
