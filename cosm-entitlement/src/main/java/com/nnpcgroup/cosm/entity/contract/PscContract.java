/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("PSC")
public class PscContract extends Contract{

    private static final long serialVersionUID = -6307338449430627486L;
    private String dType;
    public PscContract() {
    }

    public PscContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
    }

    @Override
    public String discriminatorValue() {
        DiscriminatorValue discriminatorValue = PscContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }
}
