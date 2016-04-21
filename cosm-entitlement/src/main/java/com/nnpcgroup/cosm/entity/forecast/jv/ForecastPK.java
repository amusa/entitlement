/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class ForecastPK implements Serializable {

    private static final long serialVersionUID = -5632726719147425922L;
    private int periodYear;
    private int periodMonth;    
    private ContractPK contractPK;

    public ForecastPK() {
    }

    public ForecastPK(int periodYear, int periodMonth, ContractPK contractPK) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.contractPK = contractPK;
    }
       
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

    public ContractPK getContractPK() {
        return contractPK;
    }

    public void setContractPK(ContractPK contractPK) {
        this.contractPK = contractPK;
    }

    
}
