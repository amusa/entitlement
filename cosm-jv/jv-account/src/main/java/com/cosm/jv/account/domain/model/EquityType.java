/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import java.io.Serializable;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Double ownEquity;

    @NotNull
    private Double partnerEquity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquityType that = (EquityType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
