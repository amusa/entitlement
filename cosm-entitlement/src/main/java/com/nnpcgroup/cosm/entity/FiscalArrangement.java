/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.contract.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author 18359
 */
@Entity
//@IdClass(FiscalArrangementPK.class)
@Table(name = "FISCAL_ARRANGEMENT")
public abstract class FiscalArrangement implements Serializable {

    private static final long serialVersionUID = -5266137042066972524L;
    private Long id;
    private String title;
    private Company operator;
    private List<Contract> contracts;

    public FiscalArrangement() {
    }

    public FiscalArrangement(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "OPERATOR_ID")
    @NotNull
    public Company getOperator() {
        return operator;
    }

    public void setOperator(Company operator) {
        this.operator = operator;
    }

    @OneToMany(mappedBy = "fiscalArrangement", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        if (contracts == null) {
            contracts = new ArrayList<>();
        }
        contracts.add(contract);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.title);
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
        final FiscalArrangement other = (FiscalArrangement) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


}
