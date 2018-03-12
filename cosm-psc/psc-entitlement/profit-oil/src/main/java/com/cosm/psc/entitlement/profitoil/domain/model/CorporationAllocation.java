package com.cosm.psc.entitlement.profitoil.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CORPORATION")
public class CorporationAllocation extends Allocation{

	public CorporationAllocation(AllocationId allocationId, double monthlyCharge, double monthlyChargeToDate,
			double profitOilBroughtForward, double profitOilReceived, double profitOilCarriedForward,
			ProfitOilProjection profitOilProjection) {
		super(allocationId, monthlyCharge, monthlyChargeToDate, profitOilBroughtForward, profitOilReceived,
				profitOilCarriedForward, profitOilProjection);
		// TODO Auto-generated constructor stub
	}

}
