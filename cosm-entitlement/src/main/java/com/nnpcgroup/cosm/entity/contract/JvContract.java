/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;

import javax.persistence.*;
import java.util.List;

/**
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvContract extends Contract {

    private static final long serialVersionUID = -6307338449430627486L;

    private List<AlternativeFundingContract> alternativeFundingContracts;

    public JvContract() {
    }

    public JvContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
    }

    @OneToMany(mappedBy = "jvContract")
    public List<AlternativeFundingContract> getAlternativeFundingContracts() {
        return alternativeFundingContracts;
    }

    public void setAlternativeFundingContracts(List<AlternativeFundingContract> alternativeFundingContracts) {
        this.alternativeFundingContracts = alternativeFundingContracts;
    }

    @Override
    public String discriminatorValue() {
        DiscriminatorValue discriminatorValue = JvContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }

}
