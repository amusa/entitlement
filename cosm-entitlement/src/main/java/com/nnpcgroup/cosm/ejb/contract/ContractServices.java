/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.contract;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.ejb.AbstractCrudServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import java.util.List;

/**
 *
 * @author 18359
 */
public interface ContractServices extends AbstractCrudServices<Contract> {
    
    public List<Contract> findFiscalArrangementContracts(FiscalArrangement fa);
        
   
}
