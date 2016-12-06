/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.lifting;

import com.nnpcgroup.cosm.entity.contract.Contract;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ayemi
 */
@Entity
@DiscriminatorValue("JV")
public class JvLifting extends Lifting {

    private Contract contract;

    public JvLifting() {
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "JV_ID", referencedColumnName = "FISCALARRANGEMENTID"),
        @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE")
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

}
