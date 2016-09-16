/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvProduction extends Production {

    private static final long serialVersionUID = 4881837273578907336L;

    private List<JvProductionDetail> productionDetails;

    public JvProduction() {
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<JvProductionDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<JvProductionDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public void addProductionDetails(JvProductionDetail productionDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();

        }
        productionDetails.add(productionDetail);
    }

}
