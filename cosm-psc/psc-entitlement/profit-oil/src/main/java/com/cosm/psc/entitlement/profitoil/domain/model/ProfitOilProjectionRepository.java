package com.cosm.psc.entitlement.profitoil.domain.model;

import java.util.List;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;


public interface ProfitOilProjectionRepository {

	
	ProfitOilProjection profitOilProjectionOfId(ProfitOilProjectionId poId);
	

	ProfitOilProjection profitOilProjectionOfFiscalPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	

	void store(ProfitOilProjection poProj);
	

	void save(ProfitOilProjection poProj);
	

	void remove(ProfitOilProjectionId poId);
	

	void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	

	List<ProfitOilProjection> profitOilProjectionsBetween(FiscalPeriod fpFrom, FiscalPeriod fpTo,
			ProductionSharingContractId pscId);
	

	List<ProfitOilProjection> profitOilProjectionsOfContract(ProductionSharingContractId pscId);
	

	List<ProfitOilProjection> allProfitOilProjections();
	

	ProfitOilProjectionId nextId();
	
	AllocationId nextAllocationId();

}
