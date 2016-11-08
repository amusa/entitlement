/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;

import java.io.Serializable;
import com.nnpcgroup.cosm.ejb.forecast.impl.JvCommonServices;

/**
 * @param <T>
 * @author 18359
 */
public abstract class JvCommonServicesImpl<T> extends CommonServicesImpl<T> implements JvCommonServices<T>, Serializable {

    public JvCommonServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    

}
