/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "LIFTING")
public class Lifting implements Serializable {

    private static final long serialVersionUID = -5594726430705947415L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int periodYear;
    private int periodMonth;
    
    @ManyToOne
    private Contract contract;
    
    @ManyToOne
    private Company company;
    
    @ManyToOne
    private Terminal terminal;
   
//    @ManyToOne
//    private CrudeType crudeType;
    
    private Double quantity;

    public Lifting() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

//    public CrudeType getCrudeType() {
//        return crudeType;
//    }
//
//    public void setCrudeType(CrudeType crudeType) {
//        this.crudeType = crudeType;
//    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.periodYear;
        hash = 89 * hash + this.periodMonth;
        hash = 89 * hash + Objects.hashCode(this.contract);
        hash = 89 * hash + Objects.hashCode(this.company);
        hash = 89 * hash + Objects.hashCode(this.terminal);
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
        final Lifting other = (Lifting) obj;
        if (this.periodYear != other.periodYear) {
            return false;
        }
        if (this.periodMonth != other.periodMonth) {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        if (!Objects.equals(this.terminal, other.terminal)) {
            return false;
        }
        return true;
    }

    
    

    
}
