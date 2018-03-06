package com.cosm.psc.entitlement.costoil.domain.model;

import java.util.List;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;


public interface CostOilProjectionRepository {

	
	CostOilProjection costOilProjectionOfId(CostOilProjectionId coId);
	

	CostOilProjection costOilProjectionOfFiscalPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	

	void store(CostOilProjection coProj);
	

	void save(CostOilProjection coProj);
	

	void remove(CostOilProjectionId toId);
	

	void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	

	List<CostOilProjection> costOilProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo,
			ProductionSharingContractId pscId);
	

	List<CostOilProjection> costOilProjectionsOfContract(ProductionSharingContractId pscId);
	

	List<CostOilProjection> allCostOilProjections();
	

	CostOilProjectionId nextCostOilProjectionId();

}
