/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 18359
 */
public class TerminalBlendPK implements Serializable {

    private static final long serialVersionUID = -5632726719147425922L;
    private int periodYear;
    private int periodMonth;    
    private Contract contract;

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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.periodYear;
        hash = 89 * hash + this.periodMonth;
        hash = 89 * hash + Objects.hashCode(this.contract);
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
        final TerminalBlendPK other = (TerminalBlendPK) obj;
        if (this.periodYear != other.periodYear) {
            return false;
        }
        if (this.periodMonth != other.periodMonth) {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract)) {
            return false;
        }
        return true;
    }

    
    
}
