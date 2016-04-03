/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
//@Entity
//@Table(name = "PRODUCTION")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "PROD_TYPE")
public abstract class Production_BAK implements Serializable {

//    private static final long serialVersionUID = -795843614381155072L;
//
//    private Long id;
//    private int periodYear;
//    private int periodMonth;
//    private Contract contractStream;
//    private Double productionVolume;
//    private Double grossProduction;
//    private List<ProductionEquity> productionEquities;
//
//    public Production_BAK() {
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @NotNull
//    public int getPeriodYear() {
//        return periodYear;
//    }
//
//    public void setPeriodYear(int periodYear) {
//        this.periodYear = periodYear;
//    }
//
//    @NotNull
//    public int getPeriodMonth() {
//        return periodMonth;
//    }
//
//    public void setPeriodMonth(int periodMonth) {
//        this.periodMonth = periodMonth;
//    }
//
//    @ManyToOne
//    @NotNull
//    public Contract getContractStream() {
//        return contractStream;
//    }
//
//    public void setContractStream(Contract contractStream) {
//        this.contractStream = contractStream;
//    }
//
//    @NotNull
//    public Double getProductionVolume() {
//        return productionVolume;
//    }
//
//    public void setProductionVolume(Double productionVolume) {
//        this.productionVolume = productionVolume;
//    }
//
//    public Double getGrossProduction() {
//        return grossProduction;
//    }
//
//    public void setGrossProduction(Double grossProduction) {
//        this.grossProduction = grossProduction;
//    }
//
//    @OneToMany(mappedBy = "production")
//    public List<ProductionEquity> getProductionEquities() {
//        return productionEquities;
//    }
//
//    public void setProductionEquities(List<ProductionEquity> productionEquities) {
//        this.productionEquities = productionEquities;
//    }

}
