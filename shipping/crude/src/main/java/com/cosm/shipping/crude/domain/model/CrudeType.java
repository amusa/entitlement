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

	@Override
	public String toString() {
		return  crudeType;
	}
        

}
