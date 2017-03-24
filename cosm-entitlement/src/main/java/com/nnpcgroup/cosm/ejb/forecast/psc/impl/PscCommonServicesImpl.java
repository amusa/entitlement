/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc.impl;

import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;

import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import com.nnpcgroup.cosm.ejb.forecast.psc.PscCommonServices;
import com.nnpcgroup.cosm.entity.OilField;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetail;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @param <T>
 * @author 18359
 */
public abstract class PscCommonServicesImpl<T> extends CommonServicesImpl<T> implements PscCommonServices<T>, Serializable {

    public PscCommonServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    
}
