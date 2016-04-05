/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProduction;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(AlternativeFundingProductionServices.class)
public class AlternativeFundingProductionBean extends AlternativeFundingProductionServicesImpl<AlternativeFundingProduction> implements AlternativeFundingProductionServices<AlternativeFundingProduction> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionBean.class.getName());

    public AlternativeFundingProductionBean() {
        super(AlternativeFundingProduction.class);
    }

    public AlternativeFundingProductionBean(Class<AlternativeFundingProduction> entityClass) {
        super(entityClass);
    }

    @Override
    public AlternativeFundingProduction createInstance() {
        return new AlternativeFundingProduction();
    }

}
