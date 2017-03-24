/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProductionDetail;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionDetailServices;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public abstract class AlternativeFundingProductionDetailServicesImpl<T extends AlternativeFundingProductionDetail, E extends AlternativeFundingContract> extends JvProductionDetailServicesImpl<T, E> implements AlternativeFundingProductionDetailServices<T, E> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionDetailServicesImpl.class.getName());

    public AlternativeFundingProductionDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
