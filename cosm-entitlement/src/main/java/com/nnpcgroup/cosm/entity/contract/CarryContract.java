/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.crude.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("CA")
public class CarryContract extends AlternativeFundingContract {

    private static final long serialVersionUID = 7743736261443373185L;

    public CarryContract() {
    }

    public CarryContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
    }

    @Override
    public String discriminatorValue() {
        DiscriminatorValue discriminatorValue = CarryContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }
}
