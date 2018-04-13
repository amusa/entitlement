/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.psc.domain.model;

import com.cosm.common.domain.model.CrudeTypeId;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.util.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 18359
 */
@Entity(name="PRODUCTION_SHARING_CONTRACT")
public class ProductionSharingContract implements Serializable{

    private static final long serialVersionUID = -165902073936311783L;
    private ProductionSharingContractId productionSharingContractId;
    private ContractTitle contractTitle;
    private Operator operator;
    private CrudeTypeId crudeTypeId;
    private Date contractExecutionDate;
    private Date firstOilDate;
    private Date omlConversionDate;
    private Date costRecoveryStartDate;
    private Double costRecoveryLimit;
    private Double costUplift;
    private Terrain terrain;
    private CapitalAllowanceRate capitalAllowanceRate;
    private String profitOilSplitOption;

    //private AreaSize areaSize;
    private Double oplContractArea;
    private Double omlContractArea;
    private Double oplRentalRate;
    private Double omlRentalRate;

    private List<OilField> oilFields;

    protected ProductionSharingContract() {
    	capitalAllowanceRate = new CapitalAllowanceRate();
        //areaSize = new AreaSize();
    }

    /*@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/
    
    @EmbeddedId
    public ProductionSharingContractId getProductionSharingContractId() {
		return productionSharingContractId;
	}

	public void setProductionSharingContractId(ProductionSharingContractId pscId) {
		this.productionSharingContractId = pscId;
	}
   	
	@Embedded
	public ContractTitle getContractTitle() {
		return contractTitle;
	}
	
	   
    public void setContractTitle(ContractTitle contractTitle) {
		this.contractTitle = contractTitle;
	}

	@Embedded
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @NotNull   
    @Column(name = "CRUDE_TYPE_CODE")
    public CrudeTypeId getCrudeTypeId() {
        return crudeTypeId;
    }

    public void setCrudeTypeId(CrudeTypeId crudeTypeId) {
        this.crudeTypeId = crudeTypeId;
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

    @NotNull
    @Column(name = "OML_CONVERSION_DATE")
    @Temporal(TemporalType.DATE)
    public Date getOmlConversionDate() {
        return omlConversionDate;
    }

    public void setOmlConversionDate(Date omlConversionDate) {
        this.omlConversionDate = omlConversionDate;
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

    
    @Embedded
    public CapitalAllowanceRate getCapitalAllowanceRate() {
        return capitalAllowanceRate;
    }

    public void setCapitalAllowanceRate(CapitalAllowanceRate capitalAllowanceRate) {
        this.capitalAllowanceRate = capitalAllowanceRate;
    }

    @NotNull
    @Column(name = "PROFIT_OIL_SPLIT_OPTION")
    public String getProfitOilSplitOption() {
        return profitOilSplitOption;
    }

    public void setProfitOilSplitOption(String profitOilSplitOption) {
        this.profitOilSplitOption = profitOilSplitOption;
    }

    
    @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<OilField> getOilFields() {    	
        return Collections.unmodifiableList(oilFields);
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

    

    @Column(name = "OPL_CONTRACT_AREA")
    public Double getOplContractArea() {
        return oplContractArea != null ? oplContractArea : 0;
    }

    public void setOplContractArea(Double oplContractArea) {
        this.oplContractArea = oplContractArea;
    }

    @Column(name = "OML_CONTRACT_AREA")
    public Double getOmlContractArea() {
        return omlContractArea != null ? omlContractArea : 0;
    }

    public void setOmlContractArea(Double omlContractArea) {
        this.omlContractArea = omlContractArea;
    }

    @Column(name = "OPL_RENTAL_RATE")
    public Double getOplRentalRate() {
        return oplRentalRate != null ? oplRentalRate : 0;
    }

    public void setOplRentalRate(Double oplRentalRate) {
        this.oplRentalRate = oplRentalRate;
    }

    @Column(name = "OML_RENTAL_RATE")
    public Double getOmlRentalRate() {
        return omlRentalRate != null ? omlRentalRate : 0;
    }

    public void setOmlRentalRate(Double omlRentalRate) {
        this.omlRentalRate = omlRentalRate;
    }
    
    @Transient
    public Double getOplTotalConcessionRental() {
        return (oplContractArea * oplRentalRate * getOplDuration())
                + (omlContractArea * omlRentalRate * getOmlDuration());
    }

    @Transient
    public double getOmlAnnualConcessionRental() {
        return omlContractArea * omlRentalRate;
    }
    

    @Transient
    public double getPetroleumProfitTaxRate() {
        return getPetroleumProfitTaxRate(new Date());
    }

    //@Transient
    public double getPetroleumProfitTaxRate(Date refDate) {
        return terrain.getPetroleumProfitTaxRate(refDate, this);
    }
    

    //@Transient
    private int getContractDuration(Date refDate) {
        if (contractExecutionDate != null) {
            return DateUtil.yearsDiff(contractExecutionDate, refDate);
        }

        return 0;
    }

    //@Transient
    protected int getFirstOilDuration(Date refDate) {
        if (firstOilDate != null) {
            return DateUtil.yearsDiff(firstOilDate, refDate);
        }
        return 0;
    }


    @Transient
    private int getOplDuration() {
        int contractExecutionYear = DateUtil.getLocalDate(contractExecutionDate).getYear();
        int omlConversionYear = DateUtil.getLocalDate(omlConversionDate).getYear();

        return omlConversionYear - contractExecutionYear + 1;
//        int yearDiff = DateUtil.yearsDiff(contractExecutionDate, omlConversionDate);
//        return yearDiff;
    }

    @Transient
    private int getOmlDuration() {

        int omlConversionYear = DateUtil.getLocalDate(omlConversionDate).getYear();
        int firstOilYear = DateUtil.getLocalDate(firstOilDate).getYear();

        return firstOilYear - omlConversionYear;

//        int yearDiff = DateUtil.yearsDiff(omlConversionDate, firstOilDate);
//
//        return yearDiff;
    }
}
