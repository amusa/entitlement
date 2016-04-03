/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb.impl;

import com.nnpcgroup.comd.cosm.entitlement.ejb.ContractServices;
import com.nnpcgroup.comd.cosm.entitlement.entity.Contract;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ContractServices.class)
public class ContractBean extends ContractServicesImpl implements ContractServices {
    
    public ContractBean() {
        super(Contract.class);
    }

}
