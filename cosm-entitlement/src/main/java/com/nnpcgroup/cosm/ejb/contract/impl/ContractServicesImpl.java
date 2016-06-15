/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract.impl;

import com.nnpcgroup.cosm.ejb.contract.ContractBaseServices;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ContractServices.class)
@Dependent
public class ContractServicesImpl extends ContractBaseServicesImpl<Contract> implements ContractServices, Serializable {

    private static final Logger LOG = Logger.getLogger(ContractServicesImpl.class.getName());

    public ContractServicesImpl() {
        super(Contract.class);
    }

}
