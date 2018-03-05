package com.cosm.common.event;

public class TaxOilDue extends CosmEvent {

	private double taxOilMonthlyCharge;
	private double taxOilMontlyChargeToDate;	
	private double taxOilReceived;


	
	private TaxOilDue(EventPeriod period, String pscId, double toMonthlyCharge, double toMontlyChargeToDate, double toReceived) {
		
		super(period, pscId);
				
		this.taxOilMonthlyCharge = toMonthlyCharge;
		this.taxOilMontlyChargeToDate = toMontlyChargeToDate;
		this.taxOilReceived = toReceived;	
		
	}
	
	




	public double getTaxOilMonthlyCharge() {
		return taxOilMonthlyCharge;
	}







	public double getTaxOilMontlyChargeToDate() {
		return taxOilMontlyChargeToDate;
	}







	public double getTaxOilReceived() {
		return taxOilReceived;
	}







	public static class Builder {
		private EventPeriod newPeriod;
		private String newPscId;
		private double newTaxOilMonthlyCharge;
		private double newTaxOilMontlyChargeToDate;	
		private double newTaxOilReceived;

		

		public Builder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		public Builder withContract(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		
		public Builder withMonthlyCharge(double mCharge) {
			this.newTaxOilMonthlyCharge = mCharge;
			return this;
		}

		public Builder withMonthlyChargeToDate(double mChargeToDate) {
			this.newTaxOilMontlyChargeToDate = mChargeToDate;
			return this;
		}

		public Builder withRecieved(double received) {
			this.newTaxOilReceived = received;
			return this;
		}

		
		
		public TaxOilDue build() {
			return new TaxOilDue(newPeriod, newPscId,  newTaxOilMonthlyCharge, newTaxOilMontlyChargeToDate, newTaxOilReceived);
		}
	}

}
