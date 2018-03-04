package com.cosm.psc.entitlement.royalty.application;

import com.cosm.common.domain.model.ProductionSharingContractId;

public interface ProductionSharingContractClientService {
	double concessionRental(ProductionSharingContractId pscId, int year, int month);

}
