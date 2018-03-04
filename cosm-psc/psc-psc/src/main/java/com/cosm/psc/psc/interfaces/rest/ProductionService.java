package com.cosm.psc.psc.interfaces.rest;

import com.cosm.common.domain.model.ProductionSharingContractId;

public interface ProductionService {
	boolean isFirstProductionOfYear(ProductionSharingContractId pscId, int year, int month);
}
