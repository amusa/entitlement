package com.cosm.account.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductionCostId {

    private String id;

    public ProductionCostId(){
        
    }
    public ProductionCostId(String id) {
        this.id = id;
    }

    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
