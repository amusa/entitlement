package com.cosm.psc.entitlement.profitoil.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CONTRACTOR")
public class ContractorAllocation extends Allocation{
		
	public ContractorAllocation(AllocationId allocationId, double monthlyCharge, double monthlyChargeToDate,
			double profitOilBroughtForward, double profitOilReceived, double profitOilCarriedForward,
			ProfitOilProjection profitOilProjection) {
		
		super(allocationId, monthlyCharge, monthlyChargeToDate, profitOilBroughtForward, profitOilReceived,
				profitOilCarriedForward, profitOilProjection);
		// TODO Auto-generated constructor stub
	}

}
