/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.forecast.Forecast;
import com.nnpcgroup.cosm.entity.forecast.ForecastCustomizer;
import com.nnpcgroup.cosm.entity.AuditListener;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.persistence.annotations.Customizer;

import java.util.logging.Logger;
import javax.persistence.*;

/**
 * @author 18359
 */
@Customizer(ForecastCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "JV_FORECAST")
public class JvForecast extends Forecast {

    private static final long serialVersionUID = -295843614383355072L;

    private static final Logger LOG = Logger.getLogger(Forecast.class.getName());

    private List<JvForecastDetail> forecastDetails;
    private List<JvForecastEntitlement> entitlements;
    public JvForecast() {
    }

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<JvForecastDetail> getForecastDetails() {
        return forecastDetails;
    }

    public void setForecastDetails(List<JvForecastDetail> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public void addForecastDetail(JvForecastDetail forecastDetail) {
        if (forecastDetails == null) {
            forecastDetails = new ArrayList<>();

        }
        forecastDetails.add(forecastDetail);
    }

    public void addEntitlement(JvForecastEntitlement entitlement) {
        if (entitlements == null) {
            entitlements = new ArrayList<>();

        }
        entitlements.add(entitlement);
    }

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<JvForecastEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<JvForecastEntitlement> entitlements) {
        this.entitlements = entitlements;
    }
}
