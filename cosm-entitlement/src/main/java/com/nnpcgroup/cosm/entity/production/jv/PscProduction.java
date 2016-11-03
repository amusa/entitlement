/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.AuditListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author 18359
 */
@Customizer(ProductionCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "PSC_PRODUCTION")
public class PscProduction extends Production {

    private List<PscProductionDetail> productionDetails;
    private List<PscProductionEntitlement> entitlements;

    public PscProduction() {
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<PscProductionDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<PscProductionDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public void addProductionDetail(PscProductionDetail productionDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();

        }
        productionDetails.add(productionDetail);
    }

    public void addEntitlement(PscProductionEntitlement entitlement) {
        if (entitlements == null) {
            entitlements = new ArrayList<>();

        }
        entitlements.add(entitlement);
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<PscProductionEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<PscProductionEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

}
