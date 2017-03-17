/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionEntitlement;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvProductionEntitlementServices.class)
public class JvProductionEntitlementServicesBean extends JvProductionEntitlementServicesImpl<JvProductionEntitlement, JvContract> implements JvProductionEntitlementServices<JvProductionEntitlement, JvContract>, Serializable {

    private static final Logger LOG = Logger.getLogger(JvProductionEntitlementServicesBean.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public JvProductionEntitlementServicesBean() {
        super(JvProductionEntitlement.class);
    }

}
