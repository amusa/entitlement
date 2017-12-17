package com.cosm.psc.account.domain.model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "COMPANY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COMPANY_TYPE")
public class Company implements Serializable {

    private static final long serialVersionUID = 2643548471978688966L;
    
    private Integer id;
    private String name;
    private Collection<ProductionSharingContract>productionSharingContracts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    @OneToMany(mappedBy = "operator")
    public Collection<ProductionSharingContract> getProductionSharingContracts() {
        return productionSharingContracts;
    }

    public void setProductionSharingContracts(Collection<ProductionSharingContract> productionSharingContracts) {
        this.productionSharingContracts = productionSharingContracts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Company other = (Company) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
