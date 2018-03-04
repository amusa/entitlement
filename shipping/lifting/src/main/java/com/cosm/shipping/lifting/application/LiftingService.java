package com.cosm.shipping.lifting.application;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

public interface LiftingService {
	void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

}
