/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name="EQUITY_TYPE")
public class EquityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    @NotNull
    private String description;
    @NotNull
    private Double ownEquity;
    @NotNull
    private Double partnerEquity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
    public Double getOwnEquity() {
        return ownEquity;
    }

    public void setOwnEquity(Double ownEquity) {
        this.ownEquity = ownEquity;
    }

    public Double getPartnerEquity() {
        return partnerEquity;
    }

    public void setPartnerEquity(Double partnerEquity) {
        this.partnerEquity = partnerEquity;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquityType)) {
            return false;
        }
        EquityType other = (EquityType) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nnpcgroup.comd.cosm.entitlement.entity.EquityType[ code=" + code + " ]";
    }
    
}
