package com.nnpcgroup.cosm.entity.contract;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by maliska on 8/24/16.
 */

@Entity
@Table(name = "OIL_FIELD")
public class OilField implements Serializable {
    private Long id;
    private String title;
    private PscContract contract;

    public OilField() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "CONTRACT_FISCAL_ID", referencedColumnName = "FISCALARRANGEMENTID"),
        @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE")
    })
    public PscContract getContract() {
        return contract;
    }

    public void setContract(PscContract contract) {
        this.contract = contract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OilField oilField = (OilField) o;

        if (id != null ? !id.equals(oilField.id) : oilField.id != null) return false;
        return title != null ? title.equals(oilField.title) : oilField.title == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
