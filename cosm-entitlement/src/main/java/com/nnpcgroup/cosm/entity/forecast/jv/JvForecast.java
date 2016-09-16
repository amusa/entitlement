/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.*;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvForecast extends Forecast<JvForecastDetail> {

    private static final long serialVersionUID = 3917192116735014964L;

    public JvForecast() {
    }
}
