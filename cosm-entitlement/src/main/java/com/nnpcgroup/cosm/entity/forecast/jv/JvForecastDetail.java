/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author 18359
 */
@Entity(name = "JV_FORECAST_DETAIL")
//@Table(uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"PERIOD_YEAR", "PERIOD_MONTH", "FISCALARRANGEMENT_ID", "CONTRACT_ID", "CONTRACT_FISCAL_ID", "CRUDETYPE_CODE"})
//})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class JvForecastDetail extends ForecastDetail {

    private static final long serialVersionUID = 2917191116735019064L;
    
    private JvForecastDetailPK forecastDetailPK;

    private Contract contract;
    private JvForecast forecast;

    public JvForecastDetail() {
    }

    @EmbeddedId
    public JvForecastDetailPK getForecastDetailPK() {
        return forecastDetailPK;
    }

    public void setForecastDetailPK(JvForecastDetailPK forecastDetailPK) {
        this.forecastDetailPK = forecastDetailPK;
    }

    
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID", insertable = false, updatable = false),
        @JoinColumn(name = "CONTRACT_FISCAL_ID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
        @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", insertable = false, updatable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", insertable = false, updatable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public JvForecast getForecast() {
        return forecast;
    }

//    @ManyToOne
//    @JoinColumn(name = "FORECAST_ID", referencedColumnName = "ID")
//    public JvForecast getForecast() {
//        return forecast;
//    }
    public void setForecast(JvForecast forecast) {
        this.forecast = forecast;
    }

    public ProductionDetailPK makeProductionDetailPK() {
        ProductionDetailPK pPK = new ProductionDetailPK(
                new ProductionPK(this.getPeriodYear(), this.getPeriodMonth(), this.getForecast().getFiscalArrangement().getId()),
                this.getContract().getContractPK()
        );

        return pPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.forecastDetailPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JvForecastDetail other = (JvForecastDetail) obj;
        if (!Objects.equals(this.forecastDetailPK, other.forecastDetailPK)) {
            return false;
        }
        return true;
    }
    
    

}
