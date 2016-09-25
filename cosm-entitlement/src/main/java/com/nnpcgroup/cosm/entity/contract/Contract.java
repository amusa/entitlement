/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.*;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * @author 18359
 */
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "CONTRACT")
@DiscriminatorColumn(name = "DTYPE")
public abstract class Contract   implements Auditable, Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private ContractPK contractPK;
    private FiscalArrangement fiscalArrangement;
    private CrudeType crudeType;
    private AuditInfo auditInfo = new AuditInfo();

    private String title;
    private List<ForecastDetail> forecastDetails;

    public Contract() {
    }

    public Contract(FiscalArrangement fiscalArrangement, CrudeType crudeType) {
        this.fiscalArrangement = fiscalArrangement;
        this.crudeType = crudeType;
    }

    @EmbeddedId
    public ContractPK getContractPK() {
        return contractPK;
    }

    public void setContractPK(ContractPK contractPK) {
        this.contractPK = contractPK;
    }

    @ManyToOne
    @JoinColumn(name = "FISCALARRANGEMENTID")
    @MapsId("fiscalArrangementId")
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }


    @ManyToOne
    @MapsId("crudeTypeCode")
    @JoinColumn(name = "CRUDETYPECODE")
    public CrudeType getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(CrudeType crudeType) {
        this.crudeType = crudeType;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "contract")
    public List<ForecastDetail> getForecastDetails() {
        return forecastDetails;
    }

    public void setForecastDetails(List<ForecastDetail> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public abstract String discriminatorValue();

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        auditInfo.setLastModifiedBy(user);
    }


    @Embedded
    public AuditInfo getAuditInfo() { return auditInfo; }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public String toString() {
        return String.format("%s/%s(%s)", fiscalArrangement.getTitle(), crudeType.getCode(), discriminatorValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        return contractPK != null ? contractPK.equals(contract.contractPK) : contract.contractPK == null;

    }

    @Override
    public int hashCode() {
        return contractPK != null ? contractPK.hashCode() : 0;
    }
}
