/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "COMPANY")
public class Company implements Serializable {

    private static final long serialVersionUID = 2643548471978688966L;
    
    private int id;
    private String name;
    private Collection<FiscalArrangement>fiscalArrangements;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public Collection<FiscalArrangement> getFiscalArrangements() {
        return fiscalArrangements;
    }

    public void setFiscalArrangements(Collection<FiscalArrangement> fiscalArrangements) {
        this.fiscalArrangements = fiscalArrangements;
    }
    
}
