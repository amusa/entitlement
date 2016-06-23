/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@IdClass(TerminalBlendPK.class)
@Table(name = "TERMINAL_BLEND")
public class TerminalBlend implements Serializable {

    private static final long serialVersionUID = -8220877789250153033L;

    private int periodYear;
    private int periodMonth;
    private Contract contract;

    @Id
    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    @Id
    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Id
    @ManyToOne
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    private Double volume;

    public TerminalBlend() {
    }

    @NotNull
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.periodYear;
        hash = 61 * hash + this.periodMonth;
        hash = 61 * hash + Objects.hashCode(this.contract);
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
        final TerminalBlend other = (TerminalBlend) obj;
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
