/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.AlternativeFundingContractServices;
import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;

/**
 *
 * @author 18359
 * @param <T>
 */
public abstract class AlternativeFundingContractServicesImpl<T extends AlternativeFundingContract> extends ContractBaseServicesImpl<T> implements AlternativeFundingContractServices<T> {
    public AlternativeFundingContractServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
}
