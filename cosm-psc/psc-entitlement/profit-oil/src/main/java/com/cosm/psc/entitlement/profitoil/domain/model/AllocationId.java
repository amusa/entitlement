package com.cosm.psc.entitlement.profitoil.domain.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class AllocationId implements Serializable{

    private String id;

    public AllocationId() {
    }

    public AllocationId(String id) {
        setId(id);
    }

    @NotNull
    @Column(name = "ALLOCATION_ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
