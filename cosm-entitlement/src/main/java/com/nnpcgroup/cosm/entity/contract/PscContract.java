/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("PSC")
public class PscContract extends Contract{

    private static final long serialVersionUID = -6307338449430627486L;

    private Date contractExecutionDate;
    private Date firstOilDate;
    private Date costRecoveryStartDate;
    private Double costRecoveryLimit;
    private Double costUplift;
    private Double pscRoyaltyRate;
    @Embedded
    private TaxAndAllowance taxAndAllowance;
    private String profitOilSplitOption;
    @Embedded
    private AreaSize areaSize;
    private List<OilField> oilFields;


    private String dType;
    public PscContract() {
    }

    public PscContract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        super(fiscalArrangement, crudeType);
    }


    @Column(name = "CONTRACT_EXECUTION_DATE")
    public Date getContractExecutionDate() {
        return contractExecutionDate;
    }

    public void setContractExecutionDate(Date contractExecutionDate) {
        this.contractExecutionDate = contractExecutionDate;
    }

    @Column(name = "FIRST_OIL_DATE")
    public Date getFirstOilDate() {
        return firstOilDate;
    }

    public void setFirstOilDate(Date firstOilDate) {
        this.firstOilDate = firstOilDate;
    }

    @Column(name = "COST_RECOVERY_START_DATE")
    public Date getCostRecoveryStartDate() {
        return costRecoveryStartDate;
    }

    public void setCostRecoveryStartDate(Date costRecoveryStartDate) {
        this.costRecoveryStartDate = costRecoveryStartDate;
    }

    @Column(name = "COST_RECOVERY_LIMIT")
    public Double getCostRecoveryLimit() {
        return costRecoveryLimit;
    }

    public void setCostRecoveryLimit(Double costRecoveryLimit) {
        this.costRecoveryLimit = costRecoveryLimit;
    }

    @Column(name = "COST_UPLIFT")
    public Double getCostUplift() {
        return costUplift;
    }

    public void setCostUplift(Double costUplift) {
        this.costUplift = costUplift;
    }

    @Column(name = "PSC_ROYALTY_RATE")
    public Double getPscRoyaltyRate() {
        return pscRoyaltyRate;
    }

    public void setPscRoyaltyRate(Double pscRoyaltyRate) {
        this.pscRoyaltyRate = pscRoyaltyRate;
    }

    public TaxAndAllowance getTaxAndAllowance() {
        return taxAndAllowance;
    }

    public void setTaxAndAllowance(TaxAndAllowance taxAndAllowance) {
        this.taxAndAllowance = taxAndAllowance;
    }

    @Column(name = "PROFIT_OIL_SPLIT_OPTION")
    public String getProfitOilSplitOption() {
        return profitOilSplitOption;
    }

    public void setProfitOilSplitOption(String profitOilSplitOption) {
        this.profitOilSplitOption = profitOilSplitOption;
    }

    public AreaSize getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(AreaSize areaSize) {
        this.areaSize = areaSize;
    }

    @OneToMany(mappedBy = "contract")
    public List<OilField> getOilFields() {
        return oilFields;
    }

    public void setOilFields(List<OilField> oilFields) {
        this.oilFields = oilFields;
    }

    @Override
    public String discriminatorValue() {
        DiscriminatorValue discriminatorValue = PscContract.class.getAnnotation(DiscriminatorValue.class);
        return discriminatorValue.value();
    }
}
