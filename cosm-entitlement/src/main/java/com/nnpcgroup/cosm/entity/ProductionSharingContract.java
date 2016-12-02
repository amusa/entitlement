/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.crude.CrudeType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"OPERATOR_ID"}),
    @UniqueConstraint(columnNames = {"TITLE"})
})
@DiscriminatorValue("PSC")
public class ProductionSharingContract extends FiscalArrangement {

    private static final long serialVersionUID = -165902073936311783L;

    private CrudeType crudeType;
    private Date contractExecutionDate;
    private Date firstOilDate;
    private Date costRecoveryStartDate;
    private Double costRecoveryLimit;
    private Double costUplift;
//    private Double royaltyRate;
    private String terrain;
    private Double waterDepth;

    private TaxAndAllowance taxAndAllowance;
    private String profitOilSplitOption;

    private AreaSize areaSize;
    private List<OilField> oilFields;

    public ProductionSharingContract() {
        taxAndAllowance = new TaxAndAllowance();
        areaSize = new AreaSize();
    }

    public ProductionSharingContract(Long id) {
        super(id);
    }

    @NotNull
    @OneToOne
    @JoinColumn(name = "CRUDE_TYPE_CODE", referencedColumnName = "CODE")
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    @NotNull
    @Column(name = "CONTRACT_EXECUTION_DATE")
    @Temporal(TemporalType.DATE)
    public Date getContractExecutionDate() {
        return contractExecutionDate;
    }

    public void setContractExecutionDate(Date contractExecutionDate) {
        this.contractExecutionDate = contractExecutionDate;
    }

    @Column(name = "FIRST_OIL_DATE")
    @Temporal(TemporalType.DATE)
    public Date getFirstOilDate() {
        return firstOilDate;
    }

    public void setFirstOilDate(Date firstOilDate) {
        this.firstOilDate = firstOilDate;
    }

    @Column(name = "COST_RECOVERY_START_DATE")
    @Temporal(TemporalType.DATE)
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

//    @Column(name = "ROYALTY_RATE")
    @Transient
    public Double getRoyaltyRate() {
        if (terrain != null) {
            if (terrain.equalsIgnoreCase("OFFSHORE")) {
                if (waterDepth != null) {
                    if (waterDepth <= 100.0) {
                        return 18.5;
                    } else if (waterDepth > 100.0 & waterDepth <= 200.0) {
                        return 16.5;
                    } else if (waterDepth > 200.0 & waterDepth <= 500.00) {
                        return 12.0;
                    } else if (waterDepth > 500.0) {
                        return 8.0;
                    }
                }
            } else if (terrain.equalsIgnoreCase("ONSHORE")) {
                return 20.0;
            } else if (terrain.equalsIgnoreCase("INLAND BASIN")) {
                return 10.0;
            }
        }
        return null;
    }

//    public void setRoyaltyRate(Double royaltyRate) {
//        this.royaltyRate = royaltyRate;
//    }
    @Embedded
    public TaxAndAllowance getTaxAndAllowance() {
        return taxAndAllowance;
    }

    public void setTaxAndAllowance(TaxAndAllowance taxAndAllowance) {
        this.taxAndAllowance = taxAndAllowance;
    }

    @NotNull
    @Column(name = "PROFIT_OIL_SPLIT_OPTION")
    public String getProfitOilSplitOption() {
        return profitOilSplitOption;
    }

    public void setProfitOilSplitOption(String profitOilSplitOption) {
        this.profitOilSplitOption = profitOilSplitOption;
    }

    @Embedded
    public AreaSize getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(AreaSize areaSize) {
        this.areaSize = areaSize;
    }

    @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<OilField> getOilFields() {
        return oilFields;
    }

    @NotNull
    @Column(name = "TERRAIN")
    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @Column(name = "WATER_DEPTH")
    public Double getWaterDepth() {
        return waterDepth;
    }

    public void setWaterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
    }

    public void setOilFields(List<OilField> oilFields) {
        this.oilFields = oilFields;
    }

    public void addOilField(OilField oilField) {
        if (oilFields == null) {
            oilFields = new ArrayList<>();
        }
        oilFields.add(oilField);
    }

    @Transient
    public Double getInvestmentTaxAllowanceCredit() {
        if (terrain != null) {
            if (terrain.equalsIgnoreCase("OFFSHORE")) {
                if (waterDepth != null) {
                    if (waterDepth <= 100.0) {
                        return 10.0;
                    } else if (waterDepth > 100.0 & waterDepth <= 200.0) {
                        return 15.0;
                    } else if (waterDepth > 200.0) {
                        return 50.0;
                    }
                }
            } else if (terrain.equalsIgnoreCase("ONSHORE")) {
                return 5.0;
            } else if (terrain.equalsIgnoreCase("INLAND BASIN")) {
                return 50.0;
            }
        }
        return null;
    }

    @Transient
    public double getPetroleumProfitTaxRate() {

        if (terrain != null) {
            if (terrain.equalsIgnoreCase("OFFSHORE")) {
                if (waterDepth != null && waterDepth >= 201.0) {
                    return 50.0;
                }
            }
            int dateDiff = getContractDuration();
            if (dateDiff <= 5) {
                return 65.75;//TODO:USE ENUM
            }
            return 85.0;
        }

        return 0;
    }

    @Transient
    private int getContractDuration() {
        if (contractExecutionDate != null) {
            Calendar execDate = GregorianCalendar.getInstance();
            Calendar today = GregorianCalendar.getInstance();

            execDate.setTime(contractExecutionDate);
            today.add(Calendar.DAY_OF_YEAR, -execDate.get(Calendar.DAY_OF_YEAR));

            return today.get(Calendar.YEAR) - execDate.get(Calendar.YEAR);
        }

        return 0;
    }
}
