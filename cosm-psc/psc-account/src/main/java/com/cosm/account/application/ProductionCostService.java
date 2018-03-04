package com.cosm.account.application;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

public interface ProductionCostService {
	
	void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

}
