/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "TERMINAL_BLEND")
public class TerminalBlend implements Serializable {

    private static final long serialVersionUID = -8220877789250153033L;

    private TerminalBlendPK terminalBlendPK;
    private Contract contract;

    private Double volume;

    public TerminalBlend() {
    }

    @EmbeddedId
    public TerminalBlendPK getTerminalBlendPK() {
        return terminalBlendPK;
    }

    public void setTerminalBlendPK(TerminalBlendPK terminalBlendPK) {
        this.terminalBlendPK = terminalBlendPK;
    }

    @NotNull
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @ManyToOne
    @NotNull
    @MapsId("contractPK")
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
