package com.cosm.shipping.lifting.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by maliska on 12/16/17.
 */
public interface LiftingRepository {
		
	void store(Lifting lifting);
	
	void save(Lifting lifting);
	
	void remove(Lifting lifting);
	
	
    Lifting liftingOfId(Object id);

  
    List<Lifting> allLiftings();

    List<Lifting> liftingsBetween(Date from, Date to);

    List<Lifting> liftingsOfContractBetween(Date from, Date to, ProductionSharingContractId pscId);

    List<Lifting> liftingsOfPeriod(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double weightedAveragePrice(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double corporationProceed(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double contractorProceed(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double monthlyIncome(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double proceedToDate(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double grossIncome(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);

    double cashPayment(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId);
    
    LiftingId nextLiftingId();
    
}
