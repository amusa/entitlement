package com.cosm.psc.entitlement.royalty.application;

public class RoyaltyDetail {
	
	private double chargeBfw;
    private double monthlyCharge;
    private double liftingProceed;
    private double prevCumMonthlyCharge;
    private double cashPayment;

    

    public RoyaltyDetail(double chargeBfw, double royalty, double royaltyToDate, double corpProceed, double cashPayment) {
        this.chargeBfw = chargeBfw;
        this.monthlyCharge = royalty;
        this.liftingProceed = corpProceed;
        this.prevCumMonthlyCharge = royaltyToDate;
        this.cashPayment = cashPayment;
    }

    public double getChargeBfw() {
        return chargeBfw;
    }

    public void setChargeBfw(double chargeBfw) {
        this.chargeBfw = chargeBfw;
    }

    public double getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public double getLiftingProceed() {
        return liftingProceed;
    }

    public void setLiftingProceed(double liftingProceed) {
        this.liftingProceed = liftingProceed;
    }

    public double getPrevCumMonthlyCharge() {
        return prevCumMonthlyCharge;
    }

    public void setPrevCumMonthlyCharge(double prevCumMonthlyCharge) {
        this.prevCumMonthlyCharge = prevCumMonthlyCharge;
    }

    public double getCumMonthlyCharge() {
        return getPrevCumMonthlyCharge() + getMonthlyCharge();
    }

    public double getRecoverable() {
        return chargeBfw + monthlyCharge;
    }

    public double getReceivedDefault() {
        if (getRecoverable() <= 0) {
            return 0.0;
        } else if (getRecoverable() <= getLiftingProceed()) {
            return getRecoverable();
        } else {
            return getLiftingProceed();
        }
    }

    public double getChargeCfw() {
        return getRecoverable() - getReceived();
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }

  
    public double getReceived() {
        if (getCashPayment() > 0) {
            return getCashPayment();
        }

        return getReceivedDefault();
    }

    public double getRoyaltyReceived() {
        return getReceivedDefault();
    }

}
