/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.costoil.domain.model;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author Ayemi
 */
public class CostOilAllocation extends Allocation {

	protected CostOilAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw,
			double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge) {
		super(fiscalPeriod, pscId, chargeBfw, monthlyCharge, liftingProceed, prevCumMonthlyCharge);
		// TODO Auto-generated constructor stub
	}
}
