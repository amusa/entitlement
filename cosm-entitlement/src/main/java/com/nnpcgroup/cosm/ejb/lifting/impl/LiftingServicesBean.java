/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting.impl;

import com.nnpcgroup.cosm.ejb.lifting.LiftingServices;
import com.nnpcgroup.cosm.entity.lifting.Lifting;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * @author 18359
 */
@Stateless
@Local(LiftingServices.class)
public class LiftingServicesBean extends LiftingServicesImpl<Lifting> implements LiftingServices<Lifting> {

    public LiftingServicesBean() {
        super(Lifting.class);
    }

    public LiftingServicesBean(Class<Lifting> entityClass) {
        super(entityClass);
    }

}
