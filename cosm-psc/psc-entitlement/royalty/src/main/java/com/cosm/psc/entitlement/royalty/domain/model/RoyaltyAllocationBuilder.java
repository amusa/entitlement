package com.cosm.psc.entitlement.royalty.domain.model;

import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyAllocation;
import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.AllocationBuilder;

public class RoyaltyAllocationBuilder extends AllocationBuilder {
	
	private double _cashPayment;
	
	public RoyaltyAllocationBuilder withCashPayment(double _cashPayment) {
		this._cashPayment = _cashPayment;
		return this;
	}
	

	@Override
	public Allocation build() {
		
		return new RoyaltyAllocation(_fiscalPeriod(), _pscId(), _chargeBfw(),  _monthlyCharge(),  _liftingProceed(),
				 _prevCumMonthlyCharge(),  _cashPayment());
		
	}
	
	protected double _cashPayment() {
		return _cashPayment;
	}

}
