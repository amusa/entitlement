package com.cosm.psc.entitlement.taxoil.domain.model;

import java.util.List;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import java.util.Optional;


public interface TaxOilProjectionRepository {

	TaxOilProjection taxOilProjectionOfId(TaxOilProjectionId toId);
	
	
	Optional<TaxOilProjection> taxOilProjectionOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	void store(TaxOilProjection toProj);
	
	
	void save(TaxOilProjection toProj);
	
	
	void remove(TaxOilProjectionId toId);
	
	
	void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	List<TaxOilProjection> taxOilProjectionsBetween(
			FiscalPeriod fpFrom, FiscalPeriod fpTo, ProductionSharingContractId pscId);
	
	
	List<TaxOilProjection> taxOilProjectionsOfContract(ProductionSharingContractId pscId);
	
	
	List<TaxOilProjection> allTaxOilProjections();
	

	TaxOilProjectionId nextTaxOilProjectionId();
	
	
}
