/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class PricePK implements Serializable {
    private int periodYear;
    private int periodMonth;

    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }
    
    
}
