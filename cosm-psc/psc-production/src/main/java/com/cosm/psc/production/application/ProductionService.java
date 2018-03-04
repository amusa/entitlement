package com.cosm.psc.production.application;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

public interface ProductionService {
	
	void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

}
