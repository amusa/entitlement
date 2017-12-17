/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class RegularContract extends Contract {

    private static final long serialVersionUID = -6307338449430627486L;

    private List<AlternativeFundingContract> alternativeFundingContracts;

    public RegularContract() {
    }

    public RegularContract(JointVenture jointVenture, CrudeType crudeType) {
        super(jointVenture, crudeType);
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
        DiscriminatorValue discriminatorValue = RegularContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }

}
