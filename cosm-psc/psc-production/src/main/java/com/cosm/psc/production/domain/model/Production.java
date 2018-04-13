/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.production.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.OilFieldId;
import com.cosm.common.domain.model.ProductionSharingContractId;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author 18359
 */

@Entity(name = "PRODUCTION")
public class Production implements Serializable {

	private static final long serialVersionUID = -295843614383355072L;

	private static final Logger LOG = Logger.getLogger(Production.class.getName());

	
	private ProductionId productionId;	
	private FiscalPeriod fiscalPeriod;	
	private ProductionSharingContractId pscId;	
	private OilFieldId oilFieldId;
	private Double grossProduction;
	private Double dailyProduction;

	// private AuditInfo auditInfo = new AuditInfo();

	public Production() {
	}

	@EmbeddedId
	public ProductionId getProductionId() {
		return productionId;
	}

	
	
	public void setProductionId(ProductionId productionId) {
		this.productionId = productionId;
	}

	

	@Embedded	
	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	
	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	
	@Embedded
	@AttributeOverride(name="id", column=@Column(name="PSC_ID"))
	public ProductionSharingContractId getPscId() {
		return pscId;
	}
	
	public void setPscId(ProductionSharingContractId pscId) {
		this.pscId = pscId;
	}

	

	@Embedded
	@AttributeOverride(name="id", column=@Column(name="OIL_FIELD_ID"))
	public OilFieldId getOilFieldId() {
		return oilFieldId;
	}
	
	public void setOilFieldId(OilFieldId oilFieldId) {
		this.oilFieldId = oilFieldId;
	}

	@NotNull
	@Column(name = "DAILY_PRODUCTION")
	public Double getDailyProduction() {
		return dailyProduction;
	}

	public void setDailyProduction(Double dailyProduction) {
		this.dailyProduction = dailyProduction;
	}

	@Column(name = "GROSS_PRODUCTION")
	public Double getGrossProduction() {
		return grossProduction;
	}

	public void setGrossProduction(Double grossProduction) {
		this.grossProduction = grossProduction;
	}

}
