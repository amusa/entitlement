package com.cosm.common.event;

import javax.json.JsonObject;


public class CostOilReady extends CosmEvent {

	
	// cost
	private double amortizedCapex;	
	private double currentMonthOpex;
	
	// lifting
	private double monthlyIncome;
	private double contractorProceed;
	
	//TaxOil
	private double educationTax;
	

	private CostOilReady(EventPeriod period, String pscId, double amortizedCapex, double currentMonthOpex, double educationTax, double monthlyIncome, double contractorProceed) {
		super(period, pscId);

		this.amortizedCapex = amortizedCapex;		
		this.currentMonthOpex = currentMonthOpex;
		this.educationTax = educationTax;
		this.monthlyIncome = monthlyIncome;
		this.contractorProceed = contractorProceed;	
	}

	public CostOilReady(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                jsonObject.getJsonNumber("amortizedCapex").doubleValue(),
                jsonObject.getJsonNumber("currentMonthOpex").doubleValue(),
                jsonObject.getJsonNumber("educationTax").doubleValue(),   
                jsonObject.getJsonNumber("monthlyIncome").doubleValue(),
                jsonObject.getJsonNumber("contractorProceed").doubleValue()
        );
    }

	public double getAmortizedCapex() {
		return amortizedCapex;
	}




	public double getCurrentMonthOpex() {
		return currentMonthOpex;
	}



	public double getEducationTax() {
		return educationTax;
	}



	public double getMonthlyIncome() {
		return monthlyIncome;
	}



	public double getContractorProceed() {
		return contractorProceed;
	}


	
	public static class Builder {
		private EventPeriod newPeriod;
		private String newPscId;

		// cost
		private double newAmortizedCapex;		
		private double newCurrentMonthOpex;
		
		// lifting
		private double newMonthlyIncome;
		private double newContractorProceed;
		
		//TaxOil
		private double newEducationTax;
	

		public Builder withPeriod(EventPeriod period) {
			this.newPeriod = period;
			return this;
		}

		public Builder withContract(String pscId) {
			this.newPscId = pscId;
			return this;
		}

		public Builder withContractorProceed(double contProceed) {
			this.newContractorProceed = contProceed;
			return this;
		}

		public Builder withAmortizedCapex(double aCapex) {
			this.newAmortizedCapex = aCapex;
			return this;
		}


		public Builder withMonthlyIncome(double mIncome) {
			this.newMonthlyIncome = mIncome;
			return this;
		}
		
		public Builder withCurrentMonthOpex(double currMonthOpex) {
			this.newCurrentMonthOpex = currMonthOpex;
			return this;
		}
		
		public Builder withEducationTax(double edTax) {
			this.newEducationTax = edTax;
			return this;
		}

	
		
		public CostOilReady build() {
			return new CostOilReady(newPeriod, newPscId, newAmortizedCapex,
					newCurrentMonthOpex, newEducationTax, newMonthlyIncome, newContractorProceed);
		}
	}


}
