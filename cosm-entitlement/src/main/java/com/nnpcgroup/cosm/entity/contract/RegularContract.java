/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.contract;

import com.nnpcgroup.cosm.entity.CrudeType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("REG")
public class RegularContract extends Contract{

    private static final long serialVersionUID = -6307338449430627486L;

    public RegularContract() {
    }

    public RegularContract(Long fiscalArrangementId, String crudeTypeCode) {
        super(fiscalArrangementId, crudeTypeCode);
    }
}
