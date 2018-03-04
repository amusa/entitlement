/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.shipping.lifting.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ayemi
 */
@Entity
@Table(name = "LIFTING")
public abstract class Lifting implements Serializable {

    private LiftingId liftingId;
    private ProductionSharingContractId pscId;
    private OilFieldId oilFieldId;
    private Date effectiveDate;
    private Date liftingDate;
    private Double ownLifting;
    private Double partnerLifting;
    private Double price;

    private Double cashPayment;


    public Lifting(LiftingId liftingId) {
    	this.liftingId = liftingId;
    }

    @EmbeddedId
    public LiftingId getLiftingId() {
    	return liftingId;
    }
    
    @Embedded
    public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public void setPscId(ProductionSharingContractId pscId) {
		this.pscId = pscId;
	}

	@Embedded
	public OilFieldId getOilFieldId() {
		return oilFieldId;
	}

	public void setOilFieldId(OilFieldId oilFieldId) {
		this.oilFieldId = oilFieldId;
	}

	@NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_DATE")
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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
        return ownLifting != null ? ownLifting : 0;
    }

    public void setOwnLifting(Double ownLifting) {
        this.ownLifting = ownLifting;
    }

    //@NotNull
    @Column(name = "PARTNER_LIFTING")
    public Double getPartnerLifting() {
        return partnerLifting != null ? partnerLifting : 0;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    @Column(name = "CASH_PAYMENT")
    public Double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    @Transient
    public Double getTotalLifting() {
        double own, partner;
        own = getOwnLifting();
        partner = getPartnerLifting();
        return own + partner;
    }

    @NotNull
    @Column(name = "PRICE")
    public Double getPrice() {
        return price != null ? price : 0;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Transient
    public Double getOwnProceed() {
        return getOwnLifting() * getPrice();
    }

    @Transient
    public Double getPartnerProceed() {
        return getPartnerLifting() * getPrice();
    }


    @Transient
    public Double getRevenue() {
        double totalLifting = getTotalLifting() != null ? getTotalLifting() : 0;
        double price = getPrice() != null ? getPrice() : 0;
        return totalLifting * price;
    }
}
