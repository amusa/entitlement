/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.CarryContractServices;
import com.nnpcgroup.cosm.entity.contract.CarryContract;


/**
 *
 * @author 18359
 */
public class CarryContractServicesImpl extends AlternativeFundingContractServicesImpl<CarryContract> implements CarryContractServices {
    public CarryContractServicesImpl(Class<CarryContract> entityClass) {
        super(entityClass);
    }
}