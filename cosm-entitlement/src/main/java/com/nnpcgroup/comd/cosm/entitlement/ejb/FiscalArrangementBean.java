/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
@Stateless
public class FiscalArrangementBean extends AbstractCrudServicesImpl<FiscalArrangement> {

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FiscalArrangementBean() {
        super(FiscalArrangement.class);
    }
    
}
