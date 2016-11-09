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

/**
 *
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LIFTING_DATE")
    public Date getLiftingDate() {
        return liftingDate;
    }

    public void setLiftingDate(Date liftingDate) {
        this.liftingDate = liftingDate;
    }

    @Column(name = "OWN_LIFTING")
    public Double getOwnLifting() {
        return ownLifting;
    }

    public void setOwnLifting(Double ownLifting) {
        this.ownLifting = ownLifting;
    }

    @Column(name = "PARTNER_LIFTING")
    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

}