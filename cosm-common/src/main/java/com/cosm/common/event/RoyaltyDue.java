package com.cosm.common.event;

public class RoyaltyDue extends CosmEvent {

	private double royaltyMonthlyCharge;
	private double royaltyMontlyChargeToDate;	
	private double royaltyReceived;


	
	private RoyaltyDue(EventPeriod period, String pscId, double royMonthlyCharge, double royMontlyChargeToDate, double royReceived) {
		
		super(period, pscId);
				
		this.royaltyMonthlyCharge = royMonthlyCharge;
		this.royaltyMontlyChargeToDate = royMontlyChargeToDate;
		this.royaltyReceived = royReceived;	
		
	}
	
	


	public double getRoyaltyMonthlyCharge() {
		return royaltyMonthlyCharge;
	}




	public double getRoyaltyMontlyChargeToDate() {
		return royaltyMontlyChargeToDate;
	}




	public double getRoyaltyReceived() {
		return royaltyReceived;
	}




	public static class Builder {
		private EventPeriod newPeriod;
		private String newPscId;
		private double newRoyaltyMonthlyCharge;
		private double newRoyaltyMontlyChargeToDate;	
		private double newRoyaltyReceived;

		

		public Builder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		public Builder withContract(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		
		public Builder withMonthlyCharge(double mCharge) {
			this.newRoyaltyMonthlyCharge = mCharge;
			return this;
		}

		public Builder withMonthlyChargeToDate(double mChargeToDate) {
			this.newRoyaltyMontlyChargeToDate = mChargeToDate;
			return this;
		}

		public Builder withRecieved(double received) {
			this.newRoyaltyReceived = received;
			return this;
		}

		
		
		public RoyaltyDue build() {
			return new RoyaltyDue(newPeriod, newPscId,  newRoyaltyMonthlyCharge, newRoyaltyMontlyChargeToDate, newRoyaltyReceived);
		}
	}

}
