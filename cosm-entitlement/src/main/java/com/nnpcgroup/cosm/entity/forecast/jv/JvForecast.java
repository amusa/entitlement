/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvForecast extends Forecast {

    private static final long serialVersionUID = 2917192116735019964L;

    public JvForecast() {
    }

}
