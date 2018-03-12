package com.cosm.psc.entitlement.royalty.application;

import java.util.Calendar;
import java.util.Date;

import com.cosm.common.domain.model.Allocation;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyAllocation;

public class RoyaltyBuilder {
	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
	private Allocation allocation;
	
	private double chargeBfw;    
    private double corporationProceed;
    private double cashPayment;
    private double grossProduction;
    private double weightedAveragePrice;
    private double concessionRental;
    private double priorRoyaltyToDate;
   
    RoyaltyBuilder withPeriod(FiscalPeriod fp) {
    	this.fiscalPeriod = fp;
    	return this;
    }
    
    RoyaltyBuilder withContractId(ProductionSharingContractId pscId) {
    	this.pscId = pscId;
    	return this;
    }
    
    RoyaltyBuilder withCorporationProceed(double corpProceed) {
    	this.corporationProceed = corpProceed;
    	return this;
    }
    
    RoyaltyBuilder withPriorRoyaltyToDate(double priorRoyaltyToDate) {
    	this.priorRoyaltyToDate = priorRoyaltyToDate;
    	return this;
    }
   
    RoyaltyBuilder withConcessionRental(double concessionRental) {
    	this.concessionRental = concessionRental;
    	return this;
    }
    
    RoyaltyBuilder withWeightedAveragePrice(double wap) {
    	this.weightedAveragePrice = wap;
    	return this;
    }
    
        
    RoyaltyBuilder withGrossProduction(double grossProd) {
    	this.grossProduction = grossProd;
    	return this;
    }
    
    RoyaltyBuilder withChargeBfw(double chargeBfw) {
    	this.chargeBfw = chargeBfw;
    	return this;
    }
   
  
    RoyaltyBuilder withCashPayment(double cashPayment) {
    	this.cashPayment = cashPayment;
    	return this;
    }
    
    public void build() {
    	double royalty = buildRoyalty();    	
    	this.allocation = buildAllocation(royalty);
    }
    
    private double buildRoyalty() {        
        int days = 30;//genController.daysOfMonth(year, month);
        Double dailyProd = grossProduction / days;
        double royRate = royaltyRate(dailyProd);

//        if (productionRepository.isFirstProductionOfYear(pscId, fiscalPeriod.year(), fiscalPeriod.month())) {
//            consessionRental = psc.omlAnnualConcessionRental();
//            if (yearOfDate(psc.firstOilDate()) == fiscalPeriod.year()) {
//                consessionRental += psc.omlAnnualConcessionRental();
//            }
//        }

        double royalty = (grossProduction * (royRate / 100) * weightedAveragePrice) + concessionRental;

        return royalty;
    }
    
  
    private Allocation buildAllocation(double royalty) { 
//    	RoyaltyAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double chargeBfw,
//    			double monthlyCharge, double liftingProceed, double prevCumMonthlyCharge)
    	
        RoyaltyAllocation allocation = new RoyaltyAllocation(fiscalPeriod, pscId, chargeBfw, royalty, corporationProceed, priorRoyaltyToDate, cashPayment);                
        //handling cash payment
        
        return allocation;
    }
    
    public Allocation allocation() {
    	return this.allocation;
    }
    
    
    private int yearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    
    private double royaltyRate(double dailyProd) {
        if (dailyProd < 2000.0) {
            return 5.0;
        } else if (dailyProd >= 2000.0 && dailyProd < 5000.0) {
            return 7.5;
        } else if (dailyProd >= 5000.0 && dailyProd < 10000.0) {
            return 15.0;
        }
        return 20.0;

    }

}
