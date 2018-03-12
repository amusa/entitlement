package com.cosm.psc.entitlement.profitoil.domain.model;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * Created by Ayemi on 28/04/2017.
 */
public class ProfitOilAllocation extends Allocation {

    protected ProfitOilAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw,
			double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge, double deductibles) {
		
    	super(fiscalPeriod, pscId, chargeBfw, monthlyCharge, liftingProceed, prevCumMonthlyCharge);
		
		setDeductibles(deductibles);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	
	private double deductibles;

    public double getDeductibles() {
        return deductibles;
    }

    private void setDeductibles(double deductibles) {
        this.deductibles = deductibles;
    }

    @Override
    public double getReceived() {
        return getLiftingProceed() - getDeductibles();
    }
}
