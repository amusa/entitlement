package com.cosm.psc.entitlement.royalty.domain.model;

import java.util.List;
import java.util.UUID;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;


public interface RoyaltyViewRepository {

	RoyaltyView royaltyViewOfId(RoyaltyViewId royId);
	
	
	RoyaltyView royaltyViewOfFiscalPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	void store(RoyaltyView royView);
	
	
	void save(RoyaltyView royView);
	
	
	void remove(RoyaltyViewId royId);
	
	
	void remove(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
	
	
	List<RoyaltyView> royaltyViewsBetween(
			FiscalPeriod fpFrom, FiscalPeriod fpTo, ProductionSharingContractId pscId);
	
	
	List<RoyaltyView> royaltyViewsOfContract(ProductionSharingContractId pscId);
	
	
	List<RoyaltyView> allRoyaltyViews();
	

	RoyaltyViewId nextRoyaltyViewId();
	
	
}
