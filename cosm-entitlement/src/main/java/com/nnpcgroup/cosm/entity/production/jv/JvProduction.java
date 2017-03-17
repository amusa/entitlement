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
@Table(name = "JV_PRODUCTION")
public class JvProduction extends Production {

    private static final long serialVersionUID = 4181837273878907334L;

    private List<JvProductionDetail> productionDetails;
    private List<JvProductionEntitlement> entitlements;

    public JvProduction() {
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<JvProductionDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<JvProductionDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public void addProductionDetail(JvProductionDetail productionDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();

        }
        productionDetails.add(productionDetail);
    }

    public void addEntitlement(JvProductionEntitlement entitlement) {
        if (entitlements == null) {
            entitlements = new ArrayList<>();

        }
        entitlements.add(entitlement);
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<JvProductionEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<JvProductionEntitlement> entitlements) {
        this.entitlements = entitlements;
    }
}
