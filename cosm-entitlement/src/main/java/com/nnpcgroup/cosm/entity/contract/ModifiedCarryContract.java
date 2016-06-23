/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("MCA")
public class ModifiedCarryContract extends AlternativeFundingContract{
    
    private static final long serialVersionUID = -308376402305136004L;

    public ModifiedCarryContract() {
    }

    public ModifiedCarryContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
    }
}
