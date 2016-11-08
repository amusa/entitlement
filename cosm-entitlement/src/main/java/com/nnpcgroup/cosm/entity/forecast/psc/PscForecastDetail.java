/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.psc;

import com.nnpcgroup.cosm.entity.contract.OilField;
import com.nnpcgroup.cosm.entity.forecast.ForecastDetail;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;
import javax.persistence.*;

/**
 *
 * @author 18359
 */
@Entity(name = "PSC_FORECAST_DETAIL")
public class PscForecastDetail extends ForecastDetail {

    private static final long serialVersionUID = 2917191116735019064L;

    private PscForecastDetailPK forecasstDetailPK;
    private PscForecast forecast;
    private OilField oilField;

    public PscForecastDetail() {
    }

    @EmbeddedId
    public PscForecastDetailPK getForecasstDetailPK() {
        return forecasstDetailPK;
    }

    public void setForecasstDetailPK(PscForecastDetailPK forecasstDetailPK) {
        this.forecasstDetailPK = forecasstDetailPK;
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

    @Override
    public ProductionDetailPK makeProductionDetailPK() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
