/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionServices;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProduction;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class AlternativeFundingProductionServicesImpl<T extends AlternativeFundingProduction> extends JvProductionServicesImpl<T> implements AlternativeFundingProductionServices<T>{

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionServicesImpl.class.getName());

    public AlternativeFundingProductionServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
   
}
