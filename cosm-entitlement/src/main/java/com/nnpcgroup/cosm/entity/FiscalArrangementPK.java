package com.nnpcgroup.cosm.entity;

import java.io.Serializable;

/**
 * Created by maliska on 6/21/16.
 */
public class FiscalArrangementPK implements Serializable {

    public FiscalArrangementPK() {
    }

    public FiscalArrangementPK(Long id) {
        this.id = id;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FiscalArrangementPK that = (FiscalArrangementPK) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
