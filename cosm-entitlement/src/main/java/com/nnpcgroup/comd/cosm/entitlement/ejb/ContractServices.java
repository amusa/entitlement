/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Contract;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import java.util.List;

/**
 *
 * @author 18359
 */
public interface ContractServices extends AbstractCrudServices<Contract> {
    
    public List<Contract> findFiscalArrangementContracts(FiscalArrangement fa);
        
   
}
