/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.FiscalArrangement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 * @author 18359
 * @param <E>
 */
@Entity
@Table(name = "FORECAST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class Forecast<E extends ForecastDetail> implements Serializable {

    private static final long serialVersionUID = -295843614383355072L;

    private static final Logger LOG = Logger.getLogger(Forecast.class.getName());

    private ForecastPK forecastPK;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement fiscalArrangement;
    private String remark;
    private List<E> forecastDetails;

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

    @ManyToOne
    @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    @MapsId("fiscalArrangementId")
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ForecastDetail.class)
    public List<E> getForecastDetails() {
        return forecastDetails;
    }

    public void setForecastDetails(List<E> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public void addForecastDetails(E forecastDetail) {
        if (forecastDetails == null) {
            forecastDetails = new ArrayList<>();

        }
        forecastDetails.add(forecastDetail);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        Forecast forecast = (Forecast) o;
//
//        return forecastPK != null ? forecastPK.equals(forecast.forecastPK) : forecast.forecastPK == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        return forecastPK != null ? forecastPK.hashCode() : 0;
//    }
}
