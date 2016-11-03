/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionEntitlementServices;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProductionEntitlement;

/**
 *
 * @author 18359
 */
@Stateless
@Local(AlternativeFundingProductionEntitlementServices.class)
public class AlternativeFundingProductionEntitlementBean extends AlternativeFundingProductionEntitlementServicesImpl<AlternativeFundingProductionEntitlement, AlternativeFundingContract> implements AlternativeFundingProductionEntitlementServices<AlternativeFundingProductionEntitlement, AlternativeFundingContract> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionEntitlementBean.class.getName());

    public AlternativeFundingProductionEntitlementBean() {
        super(AlternativeFundingProductionEntitlement.class);
    }

    public AlternativeFundingProductionEntitlementBean(Class<AlternativeFundingProductionEntitlement> entityClass) {
        super(entityClass);
    }

}
