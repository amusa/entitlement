package com.cosm.psc.entitlement.costoil.domain.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class CostOilProjectionId implements Serializable {

    private String id;

    public CostOilProjectionId() {
    }

    public CostOilProjectionId(String id) {
        setId(id);
    }

    @NotNull
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final CostOilProjectionId other = (CostOilProjectionId) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
