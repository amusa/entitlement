/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report;

/**
 *
 * @author Ayemi
 * @since 06.12.2016
 */
public class EntitlementDetail {

    private String company;
    private String crudeType;
    private Double ownLift;
    private Double partnerLift;
    private String remark;

    public EntitlementDetail(String company, String crudeType, Double ownLift, Double partnerLift, String remark) {
        this.company = company;
        this.crudeType = crudeType;
        this.ownLift = ownLift;
        this.partnerLift = partnerLift;
        this.remark = remark;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCrudeType() {
        return crudeType;
    }

    public void setCrudeType(String crudeType) {
        this.crudeType = crudeType;
    }

    public Double getOwnLift() {
        return ownLift;
    }

    public void setOwnLift(Double ownLift) {
        this.ownLift = ownLift;
    }

    public Double getPartnerLift() {
        return partnerLift;
    }

    public void setPartnerLift(Double partnerLift) {
        this.partnerLift = partnerLift;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
