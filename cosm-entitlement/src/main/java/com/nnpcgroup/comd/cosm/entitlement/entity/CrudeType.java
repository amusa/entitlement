/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import java.util.Collection;
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
    private Collection<FiscalPartnership> fiscalAssets;

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

  
    @NotNull    
    @OneToOne
    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
        
    @OneToMany(mappedBy = "crudeType")
    public Collection<FiscalPartnership> getFiscalAssets() {
        return fiscalAssets;
    }

    public void setFiscalAssets(Collection<FiscalPartnership> fiscalAssets) {
        this.fiscalAssets = fiscalAssets;
    }
    
    
    
    
    
    
}
