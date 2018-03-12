package com.cosm.common.event;

public class ProfitOilReady extends CosmEvent {

	// cost
	private double costToDate;

	// lifting
	private double monthlyIncome;
	private double corporationProceed;
	private double contractorProceed;

	// royalty
	private double royaltyMonthlyCharge;
	private double royaltyReceived;
	private double royaltyMontlyChargeToDate;

	// costoil
	private double costOilMonthlyCharge;
	private double costOilMonthlyChargeToDate;
	private double costOilReceived;

	// taxoil
	private double taxOilMonthlyCharge;
	private double taxOilReceived;
	private double taxOilMonthlyChargeToDate;

	

	public double getCostToDate() {
		return costToDate;
	}



	public double getMonthlyIncome() {
		return monthlyIncome;
	}



	public double getCorporationProceed() {
		return corporationProceed;
	}



	public double getContractorProceed() {
		return contractorProceed;
	}



	public double getRoyaltyMonthlyCharge() {
		return royaltyMonthlyCharge;
	}



	public double getRoyaltyReceived() {
		return royaltyReceived;
	}



	public double getRoyaltyMontlyChargeToDate() {
		return royaltyMontlyChargeToDate;
	}



	public double getCostOilMonthlyCharge() {
		return costOilMonthlyCharge;
	}



	public double getCostOilMonthlyChargeToDate() {
		return costOilMonthlyChargeToDate;
	}



	public double getCostOilReceived() {
		return costOilReceived;
	}



	public double getTaxOilMonthlyCharge() {
		return taxOilMonthlyCharge;
	}



	public double getTaxOilReceived() {
		return taxOilReceived;
	}



	public double getTaxOilMonthlyChargeToDate() {
		return taxOilMonthlyChargeToDate;
	}



	private ProfitOilReady(EventPeriod period, String pscId, double costToDate, double monthlyIncome,
			double corporationProceed, double contractorProceed, double royaltyMonthlyCharge, double royaltyReceived,
			double royaltyMontlyChargeToDate, double costOilMonthlyCharge, double costOilMonthlyChargeToDate,
			double costOilReceived, double taxOilMonthlyCharge, double taxOilReceived,
			double taxOilMonthlyChargeToDate) {
		
		super(period, pscId);
		
		this.costToDate = costToDate;
		this.monthlyIncome = monthlyIncome;
		this.corporationProceed = corporationProceed;
		this.contractorProceed = contractorProceed;
		this.royaltyMonthlyCharge = royaltyMonthlyCharge;
		this.royaltyReceived = royaltyReceived;
		this.royaltyMontlyChargeToDate = royaltyMontlyChargeToDate;
		this.costOilMonthlyCharge = costOilMonthlyCharge;
		this.costOilMonthlyChargeToDate = costOilMonthlyChargeToDate;
		this.costOilReceived = costOilReceived;
		this.taxOilMonthlyCharge = taxOilMonthlyCharge;
		this.taxOilReceived = taxOilReceived;
		this.taxOilMonthlyChargeToDate = taxOilMonthlyChargeToDate;
	}

	

	public static class Builder {
		private EventPeriod newPeriod;
//		private int newPeriodYear;
//		private int newPeriodMonth;
		private String newPscId;

		// cost
		private double newCostToDate;

		// lifting
		private double newMonthlyIncome;
		private double newCorporationProceed;
		private double newContractorProceed;

		// royalty
		private double newRoyaltyMonthlyCharge;
		private double newRoyaltyReceived;
		private double newRoyaltyMontlyChargeToDate;

		// costoil
		private double newCostOilMonthlyCharge;
		private double newCostOilMonthlyChargeToDate;
		private double newCostOilReceived;

		// taxoil
		private double newTaxOilMonthlyCharge;
		private double newTaxOilReceived;
		private double newTaxOilMonthlyChargeToDate;
		

		public Builder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		
//		public Builder withPeriodYear(int year) {
//			this.newPeriodYear = year;
//			return this;
//		}
//		
//		public Builder withPeriodMonth(int month) {
//			this.newPeriodMonth = month;
//			return this;
//		}
		
		public Builder withContractId(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		public Builder withCorporationProceed(double corpProceed) {
			this.newCorporationProceed = corpProceed;
			return this;
		}

		

		public Builder withCostToDate(double costToDate) {
			this.newCostToDate = costToDate;
			return this;
		}

		public Builder withMonthlyIncome(double monthlyIncome) {
			this.newMonthlyIncome = monthlyIncome;
			return this;
		}

		public Builder withContractorProceed(double contractorProceed) {
			this.newContractorProceed = contractorProceed;
			return this;
		}

		public Builder withRoyaltyMonthlyCharge(double royaltyMonthlyCharge) {
			this.newRoyaltyMonthlyCharge = royaltyMonthlyCharge;
			return this;
		}

		public Builder withRoyaltyReceived(double royaltyReceived) {
			this.newRoyaltyReceived = royaltyReceived;
			return this;
		}

		public Builder withRoyaltyMonthlyChargeToDate(double royaltyMontlyChargeToDate) {
			this.newRoyaltyMontlyChargeToDate = royaltyMontlyChargeToDate;
			return this;
		}

		public Builder withCostOilMonthlyCharge(double costOilMonthlyCharge) {
			this.newCostOilMonthlyCharge = costOilMonthlyCharge;
			return this;
		}

		public Builder withCostOilMonthlyChargeToDate(double costOilMonthlyChargeToDate) {
			this.newCostOilMonthlyChargeToDate = costOilMonthlyChargeToDate;
			return this;
		}

		public Builder withCostOilReceived(double costOilReceived) {
			this.newCostOilReceived = costOilReceived;
			return this;
		}

		public Builder withTaxOilMonthlyCharge(double taxOilMonthlyCharge) {
			this.newTaxOilMonthlyCharge = taxOilMonthlyCharge;
			return this;
		}

		public Builder withTaxOilReceived(double newTaxOilReceived) {
			this.newTaxOilReceived = newTaxOilReceived;
			return this;
		}

		public Builder withTaxOilMonthlyChargeToDate(double taxOilMonthlyChargeToDate) {
			this.newTaxOilMonthlyChargeToDate = taxOilMonthlyChargeToDate;
			return this;
		}

		public ProfitOilReady build() {
			return new ProfitOilReady(newPeriod, newPscId, newCostToDate, newMonthlyIncome, newCorporationProceed,  
					newContractorProceed,  newRoyaltyMonthlyCharge,  newRoyaltyReceived, newRoyaltyMontlyChargeToDate,  
					newCostOilMonthlyCharge,  newCostOilMonthlyChargeToDate, newCostOilReceived,  newTaxOilMonthlyCharge,  
					newTaxOilReceived, newTaxOilMonthlyChargeToDate);
		}
	}

}
