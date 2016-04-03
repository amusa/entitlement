/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "CRUDE_TYPE")
public class CrudeType implements Serializable {

    private static final long serialVersionUID = -5758793863890338020L;
    
    private String code;
    private String crudeType;
    private Terminal terminal;
    private Collection<Contract> contractStreams;

    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    public String getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(String crudeType) {
        this.crudeType = crudeType;
    }
  
    @OneToOne(mappedBy = "crudeType")
     public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
        
    @OneToMany(mappedBy = "crudeType")
    public Collection<Contract> getContractStreams() {
        return contractStreams;
    }

    public void setContractStreams(Collection<Contract> contractStreams) {
        this.contractStreams = contractStreams;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.code);
        hash = 67 * hash + Objects.hashCode(this.crudeType);
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
        final CrudeType other = (CrudeType) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.crudeType, other.crudeType)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getCrudeType(),getCode());                
    }
    
    
    
}
