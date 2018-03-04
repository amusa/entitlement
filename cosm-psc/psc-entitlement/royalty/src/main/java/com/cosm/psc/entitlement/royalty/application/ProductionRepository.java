package com.cosm.psc.entitlement.royalty.application;

import com.cosm.common.domain.model.ProductionSharingContractId;

public class ProductionRepository {

	public double grossProduction(ProductionSharingContractId pscId, FiscalPeriod fiscalPeriod) {
		
		return 3500000;
	}

	public boolean isFirstProductionOfYear(ProductionSharingContractId pscId, int year, int month) {
		// TODO Auto-generated method stub
		return false;
	}

}
