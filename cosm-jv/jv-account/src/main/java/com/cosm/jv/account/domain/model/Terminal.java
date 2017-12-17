/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import com.nnpcgroup.cosm.entity.crude.CrudeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "TERMINAL")
public class Terminal implements Serializable {

   private static final long serialVersionUID = 1L;
    
    private String code;
    private String name;
    private Double lineFillVolume;
    private Double deadStockVolume;
    private CrudeType crudeType;
    private Double openingBalance;
    private Double stockVolume;
    private Double capacity;
    private Company operator;
    

    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Double getLineFillVolume() {
        return lineFillVolume;
    }

    public void setLineFillVolume(Double lineFillVolume) {
        this.lineFillVolume = lineFillVolume;
    }
    
    public Double getDeadStockVolume() {
        return deadStockVolume;
    }

    public void setDeadStockVolume(Double deadStockVolume) {
        this.deadStockVolume = deadStockVolume;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getStockVolume() {
        return stockVolume;
    }

    public void setStockVolume(Double stockVolume) {
        this.stockVolume = stockVolume;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }
        
    @OneToOne
    @NotNull
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    
    @OneToOne
    public Company getOperator() {
        return operator;
    }

    public void setOperator(Company operator) {
        this.operator = operator;
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
        if (!(object instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

    
    
}
