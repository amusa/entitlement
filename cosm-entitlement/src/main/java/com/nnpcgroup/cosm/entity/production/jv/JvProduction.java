/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvProduction extends Production<JvProductionDetail> {

    private static final long serialVersionUID = 4181837273878907334L;

    public JvProduction() {
    }

}
