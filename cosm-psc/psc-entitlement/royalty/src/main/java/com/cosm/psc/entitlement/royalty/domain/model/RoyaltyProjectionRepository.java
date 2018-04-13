package com.cosm.psc.entitlement.royalty.domain.model;

import java.util.List;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import java.util.Optional;


public interface RoyaltyProjectionRepository {

	RoyaltyProjection royaltyProjectionOfId(RoyaltyProjectionId royId);
	
	
	Optional<RoyaltyProjection>  royaltyProjectionOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	void store(RoyaltyProjection royProjection);
	
	
	void save(RoyaltyProjection royProjection);
	
	
	void remove(RoyaltyProjectionId royId);
	
	
	void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	List<RoyaltyProjection> royaltyProjectionsBetween(
			FiscalPeriod fpFrom, FiscalPeriod fpTo, ProductionSharingContractId pscId);
	
	
	List<RoyaltyProjection> royaltyProjectionsOfContract(ProductionSharingContractId pscId);
	
	
	List<RoyaltyProjection> allRoyaltyProjections();
	

	RoyaltyProjectionId nextId();
	
	
}
