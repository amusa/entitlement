package com.cosm.common.event;

public class ProfitOilDue extends CosmEvent {

	private double profitOil;
	private double corporationMonthlyCharge;
	private double corporationMonthlyChargeToDate;
	private double corporationProfitOilBroughtForward;
	private double corporationProfitOilReceived;
	private double corporationProfitOilCarriedForward;

	private double contractorMonthlyCharge;
	private double contractorMonthlyChargeToDate;
	private double contractorProfitOilBroughtForward;
	private double contractorProfitOilReceived;
	private double contractorProfitOilCarriedForward;

	private ProfitOilDue(EventPeriod period, String pscId, double profitOil, double corporationMonthlyCharge,
			double corporationMonthlyChargeToDate, double corporationProfitOilBroughtForward,
			double corporationProfitOilReceived, double corporationProfitOilCarriedForward,
			double contractorMonthlyCharge, double contractorMonthlyChargeToDate,
			double contractorProfitOilBroughtForward, double contractorProfitOilReceived,
			double contractorProfitOilCarriedForward) {

		super(period, pscId);

		this.profitOil = profitOil;
		this.corporationMonthlyCharge = corporationMonthlyCharge;
		this.corporationMonthlyChargeToDate = corporationMonthlyChargeToDate;
		this.corporationProfitOilBroughtForward = corporationProfitOilBroughtForward;
		this.corporationProfitOilReceived = corporationProfitOilReceived;
		this.corporationProfitOilCarriedForward = corporationProfitOilCarriedForward;
		this.contractorMonthlyCharge = contractorMonthlyCharge;
		this.contractorMonthlyChargeToDate = contractorMonthlyChargeToDate;
		this.contractorProfitOilBroughtForward = contractorProfitOilBroughtForward;
		this.contractorProfitOilReceived = contractorProfitOilReceived;
		this.contractorProfitOilCarriedForward = contractorProfitOilCarriedForward;
	}

	public double getProfitOil() {
		return profitOil;
	}

	public double getCorporationMonthlyCharge() {
		return corporationMonthlyCharge;
	}

	public double getCorporationMonthlyChargeToDate() {
		return corporationMonthlyChargeToDate;
	}

	public double getCorporationProfitOilBroughtForward() {
		return corporationProfitOilBroughtForward;
	}

	public double getCorporationProfitOilReceived() {
		return corporationProfitOilReceived;
	}

	public double getCorporationProfitOilCarriedForward() {
		return corporationProfitOilCarriedForward;
	}

	public double getContractorMonthlyCharge() {
		return contractorMonthlyCharge;
	}

	public double getContractorMonthlyChargeToDate() {
		return contractorMonthlyChargeToDate;
	}

	public double getContractorProfitOilBroughtForward() {
		return contractorProfitOilBroughtForward;
	}

	public double getContractorProfitOilReceived() {
		return contractorProfitOilReceived;
	}

	public double getContractorProfitOilCarriedForward() {
		return contractorProfitOilCarriedForward;
	}

	public static class Builder {
		private EventPeriod newPeriod;
		private String newPscId;
		private double newProfitOil;
		private double newCorporationMonthlyCharge;
		private double newCorporationMonthlyChargeToDate;
		private double newCorporationProfitOilBroughtForward;
		private double newCorporationProfitOilReceived;
		private double newCorporationProfitOilCarriedForward;

		private double newContractorMonthlyCharge;
		private double newContractorMonthlyChargeToDate;
		private double newContractorProfitOilBroughtForward;
		private double newContractorProfitOilReceived;
		private double newContractorProfitOilCarriedForward;

		public Builder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		public Builder withContract(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		public Builder withProfitOil(double profitOil) {
			this.newProfitOil = profitOil;
			return this;
		}

		public Builder withCorporationCharge(double corpCharge) {
			this.newCorporationMonthlyCharge = corpCharge;
			return this;
		}

		
		public Builder withCorporationChargeToDate(double corpChargeToDate) {
			this.newCorporationMonthlyChargeToDate = corpChargeToDate;
			return this;
		}

		
		public Builder withCorporationBfw(double corpBfw) {
			this.newCorporationProfitOilBroughtForward = corpBfw;
			return this;
		}

		public Builder withCorporationCfw(double corpCfw) {
			this.newCorporationProfitOilCarriedForward = corpCfw;
			return this;
		}

		public Builder withCorporationRecieved(double corpReceived) {
			this.newCorporationProfitOilReceived = corpReceived;
			return this;
		}

		public Builder withContractorCharge(double contCharge) {
			this.newContractorMonthlyCharge = contCharge;
			return this;
		}

		public Builder withContractorChargeToDate(double contChargeToDate) {
			this.newContractorMonthlyChargeToDate = contChargeToDate;
			return this;
		}
		
		public Builder withContractorBfw(double contBfw) {
			this.newContractorProfitOilBroughtForward = contBfw;
			return this;
		}

		public Builder withContractorCfw(double contCfw) {
			this.newContractorProfitOilCarriedForward = contCfw;
			return this;
		}

		public Builder withContractorRecieved(double contReceived) {
			this.newContractorProfitOilReceived = contReceived;
			return this;
		}

		public ProfitOilDue build() {
			return new ProfitOilDue(newPeriod, newPscId, newProfitOil, newCorporationMonthlyCharge, newCorporationMonthlyChargeToDate,
					newCorporationProfitOilBroughtForward, newCorporationProfitOilReceived,
					newCorporationProfitOilCarriedForward,
					newContractorMonthlyCharge, newContractorMonthlyChargeToDate, newContractorProfitOilBroughtForward,
					newContractorProfitOilReceived, newContractorProfitOilCarriedForward);
		}
	}

}
