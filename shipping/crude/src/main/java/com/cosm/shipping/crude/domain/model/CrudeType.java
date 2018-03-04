/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.shipping.crude.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cosm.common.domain.model.CrudeTypeId;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 18359
 */
@Entity
@Table(name = "CRUDE_TYPE")
public class CrudeType implements Serializable {

    private static final long serialVersionUID = -5758793863890338020L;

    private CrudeTypeId crudeTypeId;
    private String crudeType;
    private Terminal terminal;
    private double apiGravity;
    //private List<Contract> contracts;

    public CrudeType() {
    }

    public CrudeType(CrudeTypeId crudeTypeId) {
        this.crudeTypeId = crudeTypeId;
    }

    @EmbeddedId
    public CrudeTypeId getCrudeTypeId() {
		return crudeTypeId;
	}

	public void setCrudeTypeId(CrudeTypeId crudeTypeId) {
		this.crudeTypeId = crudeTypeId;
	}

	@NotNull
    @Column(name = "CRUDE_TYPE")
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

    @NotNull
    @Column(name = "API_GRAVITY")
    public double getApiGravity() {
        return apiGravity;
    }

    public void setApiGravity(double apiGravity) {
        this.apiGravity = apiGravity;
    }
        
//    @OneToMany(mappedBy = "crudeType", fetch = FetchType.EAGER)
//    public List<Contract> getContracts() {
//        return contracts;
//    }
//
//    public void setContracts(List<Contract> contracts) {
//        this.contracts = contracts;
//    }
//
//    public void addContract(Contract contract){
//        if (contracts==null){
//            contracts=new ArrayList<>();
//
//        }
//        contracts.add(contract);
//    }

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
        return String.format("%s (%s)", getCrudeType(), getCode());
    }

}
