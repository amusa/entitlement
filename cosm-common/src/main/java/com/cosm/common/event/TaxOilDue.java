package com.cosm.common.event;

public class TaxOilDue extends CosmEvent {

	// cost
	private double amortizedCapex;
	private double currentYearCapex;
	private double currentYearOpex;
	private double educationTax;

	// lifting
	private double grossIncome;
	private double corporationProceed;

	private TaxOilDue(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex,
			double currentYearOpex, double educationTax, double grossIncome, double corporationProceed) {

		super(period, pscId);

		this.amortizedCapex = amortizedCapex;
		this.currentYearCapex = currentYearCapex;
		this.currentYearOpex = currentYearOpex;
		this.educationTax = educationTax;
		this.grossIncome = grossIncome;
		this.corporationProceed = corporationProceed;

	}

	

	public double getAmortizedCapex() {
		return amortizedCapex;
	}



	public double getCurrentYearCapex() {
		return currentYearCapex;
	}



	public double getCurrentYearOpex() {
		return currentYearOpex;
	}



	public double getEducationTax() {
		return educationTax;
	}



	public double getGrossIncome() {
		return grossIncome;
	}



	public double getCorporationProceed() {
		return corporationProceed;
	}



	public static class EventBuilder {
		private EventPeriod newPeriod;
		private String newPscId;

		// cost
		private double newAmortizedCapex;
		private double newCurrentYearCapex;
		private double newCurrentYearOpex;
		private double newEducationTax;

		// lifting
		private double newGrossIncome;
		private double newCorporationProceed;

		public EventBuilder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		public EventBuilder withContract(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		public EventBuilder withCorporationProceed(double corpProceed) {
			this.newCorporationProceed = corpProceed;
			return this;
		}

		public EventBuilder withAmortizedCapex(double aCapex) {
			this.newAmortizedCapex = aCapex;
			return this;
		}

		public EventBuilder withCurrentYearCapex(double currYrCapex) {
			this.newCurrentYearCapex = currYrCapex;
			return this;
		}

		public EventBuilder withGrossIncome(double gIncome) {
			this.newGrossIncome = gIncome;
			return this;
		}
		
		public EventBuilder withCurrentYearOpex(double currYrOpex) {
			this.newCurrentYearOpex = currYrOpex;
			return this;
		}
		
		public EventBuilder withEducationTax(double edTax) {
			this.newEducationTax = edTax;
			return this;
		}

		public TaxOilDue build() {
			return new TaxOilDue(newPeriod, newPscId, newAmortizedCapex, newCurrentYearCapex,
					newCurrentYearOpex, newEducationTax, newGrossIncome, newCorporationProceed);
		}
	}

}
