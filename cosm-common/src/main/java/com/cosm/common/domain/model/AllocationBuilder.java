package com.cosm.common.domain.model;

public abstract class AllocationBuilder{
	
	private FiscalPeriod _fiscalPeriod;
	private ProductionSharingContractId _pscId;
	private Double _chargeBfw;
    private Double _monthlyCharge;
    private Double _liftingProceed;
    private double _prevCumMonthlyCharge;
    
    
    public AllocationBuilder withFiscalPeriod(FiscalPeriod _fiscalPeriod) {
    	this._fiscalPeriod = _fiscalPeriod;
    	return this;
    }
    
    public AllocationBuilder withContractId(ProductionSharingContractId _pscId) {
    	this._pscId = _pscId;
    	return this;
    }
    
    
    public AllocationBuilder withChargeBfw(double _chargeBfw) {
    	this._chargeBfw = _chargeBfw;
    	return this;
    }
    
    public AllocationBuilder withMonthlyCharge(double _monthlyCharge) {
    	this._monthlyCharge = _monthlyCharge;
    	return this;
    }
    
    public AllocationBuilder withLiftingProceed(double _liftingProceed) {
    	this._liftingProceed = _liftingProceed;
    	return this;
    }
    
    public AllocationBuilder withPreviouseCumMontlyCharge(double _prevCumMonthlyCharge) {
    	this._prevCumMonthlyCharge = _prevCumMonthlyCharge;
    	return this;
    }
    
    public abstract Allocation build();
 
    
	protected FiscalPeriod _fiscalPeriod() {
		return _fiscalPeriod;
	}

	protected ProductionSharingContractId _pscId() {
		return _pscId;
	}

	protected Double _chargeBfw() {
		return _chargeBfw;
	}

	protected Double _monthlyCharge() {
		return _monthlyCharge;
	}

	protected Double _liftingProceed() {
		return _liftingProceed;
	}

	protected double _prevCumMonthlyCharge() {
		return _prevCumMonthlyCharge;
	}
   
         
    
	
}