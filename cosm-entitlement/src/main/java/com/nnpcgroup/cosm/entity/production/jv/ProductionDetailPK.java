/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 18359
 */
@Embeddable
public class ProductionDetailPK implements Serializable {

    private static final long serialVersionUID = 2983325339937581443L;

    private ProductionPK production;
    private ContractPK contract;

    public ProductionDetailPK() {
    }

    public ProductionDetailPK(ProductionPK production, ContractPK contract) {
        this.production = production;
        this.contract = contract;
    }

    public ProductionPK getProduction() {
        return production;
    }

    public void setProduction(ProductionPK production) {
        this.production = production;
    }

    public ContractPK getContract() {
        return contract;
    }

    public void setContract(ContractPK contract) {
        this.contract = contract;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.production);
        hash = 11 * hash + Objects.hashCode(this.contract);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductionDetailPK other = (ProductionDetailPK) obj;
        if (!Objects.equals(this.production, other.production)) {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract)) {
            return false;
        }
        return true;
    }

}
