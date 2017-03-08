/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.lifting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author Ayemi
 */
@Entity
@Table(name = "LIFTING")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LIFTING_TYPE")
public abstract class Lifting implements Serializable {

    private Long id;
    private Date liftingDate;
    private Double ownLifting;
    private Double partnerLifting;
    private Double price;

    public Lifting() {
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
    @Temporal(TemporalType.DATE)
    @Column(name = "LIFTING_DATE")
    public Date getLiftingDate() {
        return liftingDate;
    }

    public void setLiftingDate(Date liftingDate) {
        this.liftingDate = liftingDate;
    }

    //@NotNull
    @Column(name = "OWN_LIFTING")
    public Double getOwnLifting() {
        return ownLifting;
    }

    public void setOwnLifting(Double ownLifting) {
        this.ownLifting = ownLifting;
    }

    //@NotNull
    @Column(name = "PARTNER_LIFTING")
    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    @Transient
    public Double getTotalLifting() {
        double own, partner;
        own = ownLifting != null ? ownLifting : 0;
        partner = partnerLifting != null ? partnerLifting : 0;
        return own + partner;
    }

    @NotNull
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Transient
    public Double getRevenue() {
        double totalLifting = getTotalLifting() != null ? getTotalLifting() : 0;
        double price = getPrice() != null ? getPrice() : 0;
        return totalLifting * price;
    }
}
