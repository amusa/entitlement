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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */

@Entity
@Table(name = "FISCAL_ARRANGEMENT")
public abstract class FiscalArrangement implements Serializable  {    

    private static final long serialVersionUID = -5266137042066972524L;
    protected long id;
    protected String title;
    protected Company operator;
    protected Collection<ContractStream> contractStreams;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    @ManyToOne
    @NotNull
    public Company getOperator() {
        return operator;
    }
 
    public void setOperator(Company operator) {
        this.operator = operator;
    }
    
 
    @OneToMany(mappedBy = "fiscalArrangement")
    public Collection<ContractStream> getContractStreams() {
        return contractStreams;
    } 

    public void setContractStreams(Collection<ContractStream> contractStreams) {
        this.contractStreams = contractStreams;
    }
    
    
    
}
