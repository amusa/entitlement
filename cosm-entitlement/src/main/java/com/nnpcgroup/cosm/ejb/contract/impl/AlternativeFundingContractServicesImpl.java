/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.AlternativeFundingContractServices;
import com.nnpcgroup.cosm.ejb.contract.RegularContractServices;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.contract.RegularContract;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
public abstract class AlternativeFundingContractServicesImpl<T extends AlternativeFundingContract> extends ContractBaseServicesImpl<T> implements AlternativeFundingContractServices<T> {
    public AlternativeFundingContractServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
}
