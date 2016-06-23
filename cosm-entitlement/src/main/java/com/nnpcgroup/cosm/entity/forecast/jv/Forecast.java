/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.Contract;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;
//import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author 18359
 */
@Entity
//@IdClass(ForecastPK.class)
@Table(name = "FORECAST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class Forecast implements Serializable {

    private static final long serialVersionUID = -795843614381155072L;

    private static final Logger LOG = Logger.getLogger(Forecast.class.getName());

    private ForecastPK forecastPK;
    private Integer periodYear;
    private Integer periodMonth;
    //    private Long fiscalArrangementId;
//    private String crudeTypeCode;
    private Contract contract;
//    private FiscalArrangement fiscalArrangement;
//    private CrudeType crudeType;

    private Double openingStock;
    private Double partnerOpeningStock;
    private Double closingStock;
    private Double partnerClosingStock;
    private Double grossProduction;
    private Double productionVolume;
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
    private Double lifting;
    private Double partnerLifting;
    private Integer cargos;
    private Integer partnerCargos;
    private Double availability;
    private Double partnerAvailability;
    private List<ForecastEntitlement> forecastEntitlements;

    public Forecast() {
    }

    @EmbeddedId
    public ForecastPK getForecastPK() {
        return forecastPK;
    }

    public void setForecastPK(ForecastPK forecastPK) {
        this.forecastPK = forecastPK;
        this.periodYear = forecastPK.getPeriodYear();
        this.periodMonth = forecastPK.getPeriodMonth();
    }

    @Column(name = "PERIOD_YEAR", updatable = false, insertable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH", updatable = false, insertable = false)
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

//    @Id
//    @Column(name = "FISCALARRANGEMENT_ID")
//    public Long getFiscalArrangementId() {
//        return fiscalArrangementId;
//    }
//
//    public void setFiscalArrangementId(Long fiscalArrangementId) {
//        this.fiscalArrangementId = fiscalArrangementId;
//    }
//
//    @Id
//    @Column(name = "CRUDETYPE_CODE")
//    public String getCrudeTypeCode() {
//        return crudeTypeCode;
//    }
//
//    public void setCrudeTypeCode(String crudeTypeCode) {
//        this.crudeTypeCode = crudeTypeCode;
//    }


    @ManyToOne
    @MapsId("contract")
    @JoinColumns({
            @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
            @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

//    @ManyToOne
//    @JoinColumn(name = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
//    @MapsId("fiscalArrangementId")
//    public FiscalArrangement getFiscalArrangement() {
//        return fiscalArrangement;
//    }
//
//    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
//        this.fiscalArrangement = fiscalArrangement;
//    }
//
//
//    @ManyToOne
//    @MapsId("crudeTypeCode")
//    @JoinColumn(name = "CRUDETYPE_CODE", insertable = false, updatable = false)
//    public CrudeType getCrudeType() {
//        return crudeType;
//    }
//
//    public void setCrudeType(CrudeType crudeType) {
//        this.crudeType = crudeType;
//    }


    @NotNull
    @Column(name = "OPENING_STOCK")
    public Double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    @Column(name = "CLOSING_STOCK")
    public Double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }

    @NotNull
    @Column(name = "PRODUCTION_VOLUME")
    public Double getProductionVolume() {
        return productionVolume;
    }

    public void setProductionVolume(Double productionVolume) {
        this.productionVolume = productionVolume;
    }

    @NotNull
    @Column(name = "OWN_SHARE_ENTITLEMENT")
    public Double getOwnShareEntitlement() {
        return ownShareEntitlement;
    }

    public void setOwnShareEntitlement(Double ownShareEntitlement) {
        this.ownShareEntitlement = ownShareEntitlement;
    }

    @Column(name = "PARTNER_SHARE_ENTITLEMENT")
    public Double getPartnerShareEntitlement() {
        return partnerShareEntitlement;
    }

    public void setPartnerShareEntitlement(Double partnerShareEntitlement) {
        this.partnerShareEntitlement = partnerShareEntitlement;
    }

    @Column(name = "GROSS_PRODUCTION")
    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
    }

    @Column(name = "LIFTING")
    public Double getLifting() {
        return lifting;
    }

    public void setLifting(Double lifting) {
        this.lifting = lifting;
    }

    @Column(name = "CARGOES")
    public Integer getCargos() {
        return cargos;
    }

    public void setCargos(Integer cargos) {
        this.cargos = cargos;
    }

    @Column(name = "AVAILABILITY")
    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    @Column(name = "PARTNER_OPENING_STOCK")
    public Double getPartnerOpeningStock() {
        return partnerOpeningStock;
    }

    public void setPartnerOpeningStock(Double partnerOpeningStock) {
        this.partnerOpeningStock = partnerOpeningStock;
    }

    @Column(name = "PARTNER_CLOSING_STOCK")
    public Double getPartnerClosingStock() {
        return partnerClosingStock;
    }

    public void setPartnerClosingStock(Double partnerClosingStock) {
        this.partnerClosingStock = partnerClosingStock;
    }

    @Column(name = "PARTNER_AVAILABILITY")
    public Double getPartnerAvailability() {
        return partnerAvailability;
    }

    public void setPartnerAvailability(Double partnerAvailability) {
        this.partnerAvailability = partnerAvailability;
    }

    @Column(name = "PARTNER_LIFTING")
    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    @Column(name = "PARTNER_CARGOES")
    public Integer getPartnerCargos() {
        return partnerCargos;
    }

    public void setPartnerCargos(Integer partnerCargos) {
        this.partnerCargos = partnerCargos;
    }

    @OneToMany(mappedBy = "forecast", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    public List<ForecastEntitlement> getForecastEntitlements() {
        return forecastEntitlements;
    }

    public void setForecastEntitlements(List<ForecastEntitlement> forecastEntitlements) {
        this.forecastEntitlements = forecastEntitlements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Forecast forecast = (Forecast) o;

        if (!periodYear.equals(forecast.periodYear)) return false;
        if (!periodMonth.equals(forecast.periodMonth)) return false;
        return contract.equals(forecast.contract);

    }

    @Override
    public int hashCode() {
        int result = periodYear.hashCode();
        result = 31 * result + periodMonth.hashCode();
        result = 31 * result + contract.hashCode();
        return result;
    }
}
