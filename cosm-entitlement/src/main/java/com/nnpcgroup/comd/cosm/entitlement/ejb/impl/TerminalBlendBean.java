/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.PscActualProductionServices;
import com.nnpcgroup.comd.cosm.entitlement.ejb.TerminalBlendServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.PscActualProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.TerminalBlend;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
@Stateless
@Local(TerminalBlendServices.class)
public class TerminalBlendBean extends AbstractCrudServicesImpl<TerminalBlend> implements TerminalBlendServices {

    private static final Logger log = Logger.getLogger(TerminalBlendBean.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    public TerminalBlendBean() {
        super(TerminalBlend.class);
        log.info("PscProductionBean::constructor activated...");
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}