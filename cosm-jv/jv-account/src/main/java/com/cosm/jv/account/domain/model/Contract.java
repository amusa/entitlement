/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;


import javax.persistence.*;

import com.cosm.common.util.AuditInfo;

import java.io.Serializable;

/**
 * @author 18359
 */
@Entity
@Table(name = "CONTRACT")
@DiscriminatorColumn(name = "DTYPE")
public abstract class Contract implements Serializable {

    private static final long serialVersionUID = 4374185291370537475L;

    private ContractPK contractPK;
    private JointVenture jointVenture;
    private CrudeType crudeType;
    private AuditInfo auditInfo = new AuditInfo();

    private String title;


    public Contract() {
    }

    public Contract(JointVenture jointVenture, CrudeType crudeType) {
        this.jointVenture = jointVenture;
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
    @JoinColumn(name = "JV_ID")
    @MapsId("jvId")
    public JointVenture getJointVenture() {
        return jointVenture;
    }

    public void setJointVenture(JointVenture jointVenture) {
        this.jointVenture = jointVenture;
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


    public abstract String discriminatorValue();

    public void setCurrentUser(String user) {
        auditInfo.setCurrentUser(user);
        auditInfo.setLastModifiedBy(user);
    }

    @Embedded
    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public String toString() {
        return String.format("%s/%s(%s)", jointVenture.getTitle(), crudeType.getCode(), discriminatorValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contract contract = (Contract) o;

        return contractPK != null ? contractPK.equals(contract.contractPK) : contract.contractPK == null;

    }

    @Override
    public int hashCode() {
        return contractPK != null ? contractPK.hashCode() : 0;
    }
}
