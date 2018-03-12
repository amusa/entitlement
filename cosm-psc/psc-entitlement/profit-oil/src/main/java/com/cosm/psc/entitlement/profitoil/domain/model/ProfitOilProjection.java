/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.profitoil.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;

/**
 * @author Ayemi
 */
@Entity(name = "PROFIT_OIL_PROJECTION")
public class ProfitOilProjection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	ProfitOilProjectionRepository poRepository;

	private ProfitOilProjectionId profitOilProjectionId;
	private FiscalPeriod fiscalPeriod;
	private ProductionSharingContractId pscId;
	private double costToDate;
	private double monthlyIncome;
	private double contractorProceed;
	private double corporationProceed;
	private double royaltyMonthlyCharge;
	private double royaltyMonthlyChargeToDate;
	private double royaltyReceived;
	private double costOilMonthlyCharge;
	private double costOilMonthlyChargeToDate;
	private double costOilReceived;
	private double taxOilMonthlyCharge;
	private double taxOilMonthlyChargeToDate;
	private double taxOilReceived;
	// private double profitOilToDate;
	private Optional<ProfitOilProjection> priorProfitOilProjection;
	private List<Allocation> allocations;

	private ProfitOilProjection(ProfitOilProjectionId profitOilProjectionId, FiscalPeriod fiscalPeriod,
			ProductionSharingContractId pscId, double costToDate, double monthlyIncome, double contractorProceed,
			double corporationProceed, double royaltyMonthlyCharge, double royaltyMonthlyChargeToDate,
			double royaltyReceived, double costOilMonthlyCharge, double costOilMonthlyChargeToDate,
			double costOilReceived, double taxOilMonthlyCharge, double taxOilMonthlyChargeToDate, double taxOilReceived,
			Optional<ProfitOilProjection> priorProfitOilProjection) {

		super();

		this.profitOilProjectionId = profitOilProjectionId;
		this.fiscalPeriod = fiscalPeriod;
		this.pscId = pscId;
		this.costToDate = costToDate;
		this.monthlyIncome = monthlyIncome;
		this.contractorProceed = contractorProceed;
		this.corporationProceed = corporationProceed;
		this.royaltyMonthlyCharge = royaltyMonthlyCharge;
		this.royaltyMonthlyChargeToDate = royaltyMonthlyChargeToDate;
		this.royaltyReceived = royaltyReceived;
		this.costOilMonthlyCharge = costOilMonthlyCharge;
		this.costOilMonthlyChargeToDate = costOilMonthlyChargeToDate;
		this.costOilReceived = costOilReceived;
		this.taxOilMonthlyCharge = taxOilMonthlyCharge;
		this.taxOilMonthlyChargeToDate = taxOilMonthlyChargeToDate;
		this.taxOilReceived = taxOilReceived;
		this.priorProfitOilProjection = priorProfitOilProjection;

		processAllocations();
	}

	private void processAllocations() {
		allocations = new ArrayList<>();
		allocations.add(corporationProfitOilAllocation());
		allocations.add(contractorProfitOilAllocation());
	}

	private double currentProfitOil() {
		double proceed = getMonthlyIncome();
		double royalty = getRoyaltyMonthlyCharge();
		double costOil = getCostOilMonthlyCharge();
		double taxOil = getTaxOilMonthlyCharge();

		return proceed - royalty - costOil - taxOil;
	}

	private double priorAggregateProfitOil() {
		double prevProceedCum = priorMonthlyIncome();
		double prevRoyaltyCum = priorRoyaltyChargeToDate();
		double prevCostOilCum = priorCostOilChargeToDate();
		double prevTaxOilCum = priorTaxOilChargeToDate();
		double prevProfitOilCum = priorProfitOilToDate();

		return Math.max(0, prevProfitOilCum + prevTaxOilCum + prevCostOilCum + prevRoyaltyCum - prevProceedCum);

	}

	public double profitOil() {
		double currentProfitOil = currentProfitOil();
		double priorAggregateProfitOil = priorAggregateProfitOil();
		double profitOil = Math.max(0, currentProfitOil - priorAggregateProfitOil);

		return profitOil;
	}

	private double profitOilToDate() {
		return profitOil() + priorProfitOilToDate();
	}

	private double priorProfitOilToDate() {
		return getPriorProfitOilProjection().isPresent() ? getPriorProfitOilProjection().get().profitOilToDate() : 0;
	}

	private double priorMonthlyIncome() {
		return getPriorProfitOilProjection().isPresent() ? getPriorProfitOilProjection().get().getMonthlyIncome() : 0;
	}

	private double priorRoyaltyChargeToDate() {
		return getPriorProfitOilProjection().isPresent()
				? getPriorProfitOilProjection().get().getRoyaltyMonthlyChargeToDate()
				: 0;
	}

	private double priorCostOilChargeToDate() {
		return getPriorProfitOilProjection().isPresent()
				? getPriorProfitOilProjection().get().getCostOilMonthlyChargeToDate()
				: 0;
	}

	private double priorTaxOilChargeToDate() {
		return getPriorProfitOilProjection().isPresent()
				? getPriorProfitOilProjection().get().getTaxOilMonthlyChargeToDate()
				: 0;
	}

	public double rFactor() {
		if (getPriorProfitOilProjection().isPresent()) {
			double priorCostOilToDate = priorCostOilChargeToDate();
			double priorContractorProfitOilToDate = priorContractorChargeToDate();
			double priorCostToDate = priorCostToDate();

			double rfactor = (priorCostToDate != 0)
					? (priorCostOilToDate + priorContractorProfitOilToDate) / priorCostToDate
					: 0;

			return rfactor;
		}

		return 0;
	}

	private double profitRateOfFactor(double rfactor) {
		if (rfactor < 1.2) {
			return 0.7;
		} else if (rfactor > 2.5) {
			return 0.25;
		} else if (rfactor >= 1.2 && rfactor <= 2.5) {
			return 0.25 + ((2.5 - rfactor) / (2.5 - 1.2)) * 0.45;
		}
		return 0;
	}

	private double priorCostToDate() {
		return getPriorProfitOilProjection().isPresent() ? getPriorProfitOilProjection().get().getCostToDate() : 0;
	}

	private double priorContractorChargeToDate() {
		if (getPriorProfitOilProjection().isPresent()) {

			List<Allocation> priorAllocations = getPriorProfitOilProjection().get().getAllocations();

			for (Allocation alloc : priorAllocations) {
				if (alloc instanceof ContractorAllocation) {
					return alloc.getMonthlyChargeToDate();
				}
			}
		}

		return 0; // TODO:should not happen!
	}

	public Allocation corporationProfitOilAllocation() {

		double profitOil = profitOil();
		double rfactor = rFactor();
		double profitRate = profitRateOfFactor(rfactor);
		double corporationLiftProceed = getCorporationProceed();
		double profitOilCharge = profitOil * (1 - profitRate);
		double deductibles = getRoyaltyReceived() + getTaxOilReceived();

		ProfitOilAllocation allocationCalculator = new ProfitOilAllocation(getFiscalPeriod(), getPscId(),
				priorCorporationProfitOilChargeCfw(), profitOilCharge, corporationLiftProceed,
				priorCorporationProfitOilChargeToDate(), deductibles);

		CorporationAllocation allocation = new CorporationAllocation(poRepository.nextAllocationId(),
				allocationCalculator.getMonthlyCharge(), allocationCalculator.getCumMonthlyCharge(),
				allocationCalculator.getChargeBfw(), allocationCalculator.getReceived(),
				allocationCalculator.getChargeCfw(), this);

		return allocation;
	}

	public Allocation contractorProfitOilAllocation() {

		double profitOil = profitOil();
		double rfactor = rFactor();
		double profitRate = profitRateOfFactor(rfactor);
		double contractorLiftProceed = getContractorProceed();
		double profitOilCharge = profitOil * profitRate;
		double deductibles = getCostOilReceived();

		ProfitOilAllocation allocationCalculator = new ProfitOilAllocation(getFiscalPeriod(), getPscId(),
				priorContractorProfitOilChargeCfw(), profitOilCharge, contractorLiftProceed,
				priorContractorProfitOilChargeToDate(), deductibles);

		ContractorAllocation allocation = new ContractorAllocation(poRepository.nextAllocationId(),
				allocationCalculator.getMonthlyCharge(), allocationCalculator.getCumMonthlyCharge(),
				allocationCalculator.getChargeBfw(), allocationCalculator.getReceived(),
				allocationCalculator.getChargeCfw(), this);

		return allocation;
	}

	private Optional<Allocation> priorContractorAllocation() {
		if (getPriorProfitOilProjection().isPresent()) {
			List<Allocation> priorAllocations = getPriorProfitOilProjection().get().getAllocations();

			for (Allocation alloc : priorAllocations) {
				if (alloc instanceof ContractorAllocation) {
					return Optional.of(alloc);
				}
			}
		}

		return Optional.empty();
	}

	private Optional<Allocation> priorCorporationAllocation() {
		if (getPriorProfitOilProjection().isPresent()) {
			List<Allocation> priorAllocations = getPriorProfitOilProjection().get().getAllocations();

			for (Allocation alloc : priorAllocations) {
				if (alloc instanceof CorporationAllocation) {
					return Optional.of(alloc);
				}
			}
		}

		return Optional.empty();
	}

	public Optional<Allocation> contractorAllocation() {
		for (Allocation alloc : allocations) {
			if (alloc instanceof ContractorAllocation) {
				return Optional.of(alloc);
			}
		}

		return Optional.empty();
	}

	public Optional<Allocation> corporationAllocation() {
		for (Allocation alloc : allocations) {
			if (alloc instanceof CorporationAllocation) {
				return Optional.of(alloc);
			}
		}

		return Optional.empty();
	}

	private double priorContractorProfitOilChargeCfw() {
		Optional<Allocation> priorPoAllocation = priorContractorAllocation();

		if (priorPoAllocation.isPresent()) {
			return priorPoAllocation.get().getProfitOilCarriedForward();
		}

		return 0;
	}

	private double priorContractorProfitOilChargeToDate() {
		Optional<Allocation> priorPoAllocation = priorContractorAllocation();

		if (priorPoAllocation.isPresent()) {
			return priorPoAllocation.get().getMonthlyChargeToDate();
		}

		return 0;
	}

	private double priorCorporationProfitOilChargeCfw() {
		Optional<Allocation> priorPoAllocation = priorCorporationAllocation();

		if (priorPoAllocation.isPresent()) {
			return priorPoAllocation.get().getProfitOilCarriedForward();
		}

		return 0;
	}

	private double priorCorporationProfitOilChargeToDate() {
		Optional<Allocation> priorPoAllocation = priorCorporationAllocation();

		if (priorPoAllocation.isPresent()) {
			return priorPoAllocation.get().getMonthlyChargeToDate();
		}

		return 0;
	}

	public ProfitOilProjectionId getProfitOilProjectionId() {
		return profitOilProjectionId;
	}

	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public ProductionSharingContractId getPscId() {
		return pscId;
	}

	public double getCostToDate() {
		return costToDate;
	}

	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public double getContractorProceed() {
		return contractorProceed;
	}

	public double getCorporationProceed() {
		return corporationProceed;
	}

	public double getRoyaltyMonthlyCharge() {
		return royaltyMonthlyCharge;
	}

	public double getRoyaltyMonthlyChargeToDate() {
		return royaltyMonthlyChargeToDate;
	}

	public double getRoyaltyReceived() {
		return royaltyReceived;
	}

	public double getCostOilMonthlyCharge() {
		return costOilMonthlyCharge;
	}

	public double getCostOilMonthlyChargeToDate() {
		return costOilMonthlyChargeToDate;
	}

	public double getCostOilReceived() {
		return costOilReceived;
	}

	public double getTaxOilMonthlyCharge() {
		return taxOilMonthlyCharge;
	}

	public double getTaxOilMonthlyChargeToDate() {
		return taxOilMonthlyChargeToDate;
	}

	public double getTaxOilReceived() {
		return taxOilReceived;
	}

	// public double getProfitOilToDate() {
	// return profitOilToDate;
	// }

	public Optional<ProfitOilProjection> getPriorProfitOilProjection() {
		return priorProfitOilProjection;
	}

	public List<Allocation> getAllocations() {
		return allocations;
	}

	public static class Builder {
		private ProfitOilProjectionId newProfitOilProjectionId;
		private FiscalPeriod newFiscalPeriod;
		private ProductionSharingContractId newPscId;
		private double newCostToDate;
		private double newMonthlyIncome;
		private double newContractorProceed;
		private double newCorporationProceed;
		private double newRoyaltyMonthlyCharge;
		private double newRoyaltyMonthlyChargeToDate;
		private double newRoyaltyReceived;
		private double newCostOilMonthlyCharge;
		private double newCostOilMonthlyChargeToDate;
		private double newCostOilReceived;
		private double newTaxOilMonthlyCharge;
		private double newTaxOilMonthlyChargeToDate;
		private double newTaxOilReceived;
		private Optional<ProfitOilProjection> newPriorProfitOilProjection;

		public Builder withId(ProfitOilProjectionId id) {
			this.newProfitOilProjectionId = id;
			return this;
		}

		public Builder withFiscalPeriod(FiscalPeriod fp) {
			this.newFiscalPeriod = fp;
			return this;
		}

		public Builder withContractId(ProductionSharingContractId pscId) {
			this.newPscId = pscId;
			return this;
		}

		public Builder withCostToDate(double ctd) {
			this.newCostToDate = ctd;
			return this;
		}

		public Builder withMonthlyIncome(double monthlyIncome) {
			this.newMonthlyIncome = monthlyIncome;
			return this;
		}

		public Builder withContractorProceed(double contProceed) {
			this.newContractorProceed = contProceed;
			return this;
		}

		public Builder withCorporationProceed(double corpProceed) {
			this.newCorporationProceed = corpProceed;
			return this;
		}

		public Builder withRoyaltyCharge(double royaltyCharge) {
			this.newRoyaltyMonthlyCharge = royaltyCharge;
			return this;
		}

		public Builder withRoyaltyChargeToDate(double royaltyChargeToDate) {
			this.newRoyaltyMonthlyChargeToDate = royaltyChargeToDate;
			return this;
		}

		public Builder withRoyaltyReceived(double royaltyReceived) {
			this.newRoyaltyReceived = royaltyReceived;
			return this;
		}

		public Builder withCostOilCharge(double coCharge) {
			this.newCostOilMonthlyCharge = coCharge;
			return this;
		}

		public Builder withCostOilChargeToDate(double coChargeToDate) {
			this.newCostOilMonthlyChargeToDate = coChargeToDate;
			return this;
		}

		public Builder withCostOilReceived(double coReceived) {
			this.newCostOilReceived = coReceived;
			return this;
		}

		public Builder withTaxOilCharge(double toCharge) {
			this.newTaxOilMonthlyCharge = toCharge;
			return this;
		}

		public Builder withTaxOilChargeToDate(double toChargeToDate) {
			this.newTaxOilMonthlyChargeToDate = toChargeToDate;
			return this;
		}

		public Builder withTaxOilReceived(double toReceived) {
			this.newTaxOilReceived = toReceived;
			return this;
		}

		public Builder withPriorProjection(Optional<ProfitOilProjection> priorProjection) {
			this.newPriorProfitOilProjection = priorProjection;
			return this;
		}

		public ProfitOilProjection build() {
			return new ProfitOilProjection(newProfitOilProjectionId, newFiscalPeriod, newPscId, newCostToDate,
					newMonthlyIncome, newContractorProceed, newCorporationProceed, newRoyaltyMonthlyCharge,
					newRoyaltyMonthlyChargeToDate, newRoyaltyReceived, newCostOilMonthlyCharge,
					newCostOilMonthlyChargeToDate, newCostOilReceived, newTaxOilMonthlyCharge,
					newTaxOilMonthlyChargeToDate, newTaxOilReceived, newPriorProfitOilProjection);
		}

	}

}
