/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.CarryContractServices;
import com.nnpcgroup.cosm.ejb.contract.ModifiedCarryContractServices;
import com.nnpcgroup.cosm.entity.contract.CarryContract;
import com.nnpcgroup.cosm.entity.contract.ModifiedCarryContract;

/**
 *
 * @author 18359
 */
public class ModifiedCarryContractServicesImpl extends AlternativeFundingContractServicesImpl<ModifiedCarryContract> implements ModifiedCarryContractServices {
    public ModifiedCarryContractServicesImpl(Class<ModifiedCarryContract> entityClass) {
        super(entityClass);
    }
}
