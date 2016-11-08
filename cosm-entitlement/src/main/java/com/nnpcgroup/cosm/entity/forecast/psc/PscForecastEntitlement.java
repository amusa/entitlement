/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.psc;

import com.nnpcgroup.cosm.entity.contract.OilField;
import com.nnpcgroup.cosm.entity.forecast.ForecastEntitlement;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;
import javax.persistence.*;

/**
 *
 * @author 18359
 */
@Entity(name = "PSC_FORECAST_ENTITLEMENT")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"PERIOD_YEAR", "PERIOD_MONTH", "FISCALARRANGEMENT_ID", "OIL_FIELD_ID"})
})
//@DiscriminatorValue("PSC")
public class PscForecastEntitlement extends ForecastEntitlement {

    private static final long serialVersionUID = 2917191116735019064L;
    private PscForecastEntitlementPK entitlementPK;
    private PscForecast forecast;
    private OilField oilField;
    private Double royaltyOil;
    private Double costOil;
    private Double ownProfitOil;
    private Double partnerProfitOil;

    private Double royaltyOilBf;
    private Double costOilBf;
    private Double ownProfitOilBf;
    private Double partnerProfitOilBf;

    private Double royaltyOilCf;
    private Double costOilCf;
    private Double ownProfitOilCf;
    private Double partnerProfitOilCf;

    private Double royaltyOilCum;
    private Double costOilCum;
    private Double ownProfitOilCum;
    private Double partnerProfitOilCum;

    private Double ownProceed;
    private Double partnerProceed;

    private Double ownProceedCum;
    private Double partnerProceedCum;

    public PscForecastEntitlement() {
    }

    @EmbeddedId
    public PscForecastEntitlementPK getEntitlementPK() {
        return entitlementPK;
    }

    public void setEntitlementPK(PscForecastEntitlementPK entitlementPK) {
        this.entitlementPK = entitlementPK;
    }
        
    @Override
    public ProductionDetailPK makeProductionDetailPK() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public PscForecast getForecast() {
        return forecast;
    }

    public void setForecast(PscForecast forecast) {
        this.forecast = forecast;
    }

    @ManyToOne
    @MapsId("oilField")
    @JoinColumn(name = "OIL_FIELD_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    public OilField getOilField() {
        return oilField;
    }

    public void setOilField(OilField oilField) {
        this.oilField = oilField;
    }

    @Column(name = "ROYALTY_OIL")
    public Double getRoyaltyOil() {
        return royaltyOil;
    }

    public void setRoyaltyOil(Double royaltyOil) {
        this.royaltyOil = royaltyOil;
    }

    @Column(name = "COST_OIL")
    public Double getCostOil() {
        return costOil;
    }

    public void setCostOil(Double costOil) {
        this.costOil = costOil;
    }

    @Column(name = "OWN_PROFIT_OIL")
    public Double getOwnProfitOil() {
        return ownProfitOil;
    }

    public void setOwnProfitOil(Double ownProfitOil) {
        this.ownProfitOil = ownProfitOil;
    }

    @Column(name = "PARTNER_PROFIT_OIL")
    public Double getPartnerProfitOil() {
        return partnerProfitOil;
    }

    public void setPartnerProfitOil(Double partnerProfitOil) {
        this.partnerProfitOil = partnerProfitOil;
    }

    @Column(name = "ROYALTY_OIL_BF")
    public Double getRoyaltyOilBf() {
        return royaltyOilBf;
    }

    public void setRoyaltyOilBf(Double royaltyOilBf) {
        this.royaltyOilBf = royaltyOilBf;
    }

    @Column(name = "COST_OIL_BF")
    public Double getCostOilBf() {
        return costOilBf;
    }

    public void setCostOilBf(Double costOilBf) {
        this.costOilBf = costOilBf;
    }

    @Column(name = "OWN_PROFIT_OIL_BF")
    public Double getOwnProfitOilBf() {
        return ownProfitOilBf;
    }

    public void setOwnProfitOilBf(Double ownProfitOilBf) {
        this.ownProfitOilBf = ownProfitOilBf;
    }

    @Column(name = "PARTNER_PROFIT_OIL_BF")
    public Double getPartnerProfitOilBf() {
        return partnerProfitOilBf;
    }

    public void setPartnerProfitOilBf(Double partnerProfitOilBf) {
        this.partnerProfitOilBf = partnerProfitOilBf;
    }

    @Column(name = "ROYALTY_OIL_CF")
    public Double getRoyaltyOilCf() {
        return royaltyOilCf;
    }

    public void setRoyaltyOilCf(Double royaltyOilCf) {
        this.royaltyOilCf = royaltyOilCf;
    }

    @Column(name = "COST_OIL_CF")
    public Double getCostOilCf() {
        return costOilCf;
    }

    public void setCostOilCf(Double costOilCf) {
        this.costOilCf = costOilCf;
    }

    @Column(name = "OWN_PROFIT_OIL_CF")
    public Double getOwnProfitOilCf() {
        return ownProfitOilCf;
    }

    public void setOwnProfitOilCf(Double ownProfitOilCf) {
        this.ownProfitOilCf = ownProfitOilCf;
    }

    @Column(name = "PARTNER_PROFIT_OIL_CF")
    public Double getPartnerProfitOilCf() {
        return partnerProfitOilCf;
    }

    public void setPartnerProfitOilCf(Double partnerProfitOilCf) {
        this.partnerProfitOilCf = partnerProfitOilCf;
    }

    @Column(name = "ROYALTY_OIL_CUM")
    public Double getRoyaltyOilCum() {
        return royaltyOilCum;
    }

    public void setRoyaltyOilCum(Double royaltyOilCum) {
        this.royaltyOilCum = royaltyOilCum;
    }

    @Column(name = "COST_OIL_CUM")
    public Double getCostOilCum() {
        return costOilCum;
    }

    public void setCostOilCum(Double costOilCum) {
        this.costOilCum = costOilCum;
    }

    @Column(name = "OWN_PROFIT_OIL_CUM")
    public Double getOwnProfitOilCum() {
        return ownProfitOilCum;
    }

    public void setOwnProfitOilCum(Double ownProfitOilCum) {
        this.ownProfitOilCum = ownProfitOilCum;
    }

    @Column(name = "PARTNER_PROFIT_OIL_CUM")
    public Double getPartnerProfitOilCum() {
        return partnerProfitOilCum;
    }

    public void setPartnerProfitOilCum(Double partnerProfitOilCum) {
        this.partnerProfitOilCum = partnerProfitOilCum;
    }

    @Column(name = "OWN_PROCEED")
    public Double getOwnProceed() {
        return ownProceed;
    }

    public void setOwnProceed(Double ownProceed) {
        this.ownProceed = ownProceed;
    }

    @Column(name = "PARTNER_PROCEED")
    public Double getPartnerProceed() {
        return partnerProceed;
    }

    public void setPartnerProceed(Double partnerProceed) {
        this.partnerProceed = partnerProceed;
    }

    @Column(name = "OWN_PROCEED_CUM")
    public Double getOwnProceedCum() {
        return ownProceedCum;
    }

    public void setOwnProceedCum(Double ownProceedCum) {
        this.ownProceedCum = ownProceedCum;
    }

    @Column(name = "PARTNER_PROCEED_CUM")
    public Double getPartnerProceedCum() {
        return partnerProceedCum;
    }

    public void setPartnerProceedCum(Double partnerProceedCum) {
        this.partnerProceedCum = partnerProceedCum;
    }

}
