package com.cosm.psc.entitlement.royalty.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Allocation {

    private double royaltyBroughtForward;
    private double royaltyReceived;
    private double royaltyCarriedForward;

    public Allocation() {

    }

    public Allocation(double royaltyBroughtForward, double royaltyReceived, double royaltyCarriedForward) {
        super();
        setRoyaltyBroughtForward(royaltyBroughtForward);
        setRoyaltyReceived(royaltyReceived);
        setRoyaltyCarriedForward(royaltyCarriedForward);
    }

    @NotNull
    @Column(name = "ROYALTY_BFW")
    public double getRoyaltyBroughtForward() {
        return royaltyBroughtForward;
    }

    @NotNull
    @Column(name = "ROYALTY_RECEIVED")
    public double getRoyaltyReceived() {
        return royaltyReceived;
    }

    @NotNull
    @Column(name = "ROYALTY_CFW")
    public double getRoyaltyCarriedForward() {
        return royaltyCarriedForward;
    }

    public void setRoyaltyBroughtForward(double royaltyBroughtForward) {
        this.royaltyBroughtForward = royaltyBroughtForward;
    }

    public void setRoyaltyReceived(double royaltyReceived) {
        this.royaltyReceived = royaltyReceived;
    }

    public void setRoyaltyCarriedForward(double royaltyCarriedForward) {
        this.royaltyCarriedForward = royaltyCarriedForward;
    }

}
