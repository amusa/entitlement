package com.nnpcgroup.cosm.entity;

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
    private Boolean differentTerrain;
    private String terrain;
    private Double waterDepth;
    private ProductionSharingContract contract;

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

    @Column(name = "DIFF_TERRAIN")
    public Boolean getDifferentTerrain() {
        return differentTerrain;
    }

    public void setDifferentTerrain(Boolean differentTerrain) {
        this.differentTerrain = differentTerrain;
    }

    @Column(name = "TERRAIN")
    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @Column(name = "WATER_DEPTH")
    public Double getWaterDepth() {
        return waterDepth;
    }

    public void setWaterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
    }

    @Transient
    public Double getRoyaltyRate() {
        if (terrain != null) {
            if (terrain.equalsIgnoreCase("OFFSHORE")) {
                if (waterDepth != null) {
                    if (waterDepth <= 100.0) {
                        return 18.5;
                    } else if (waterDepth > 100.0 & waterDepth <= 200.0) {
                        return 16.5;
                    } else if (waterDepth > 200.0 & waterDepth <= 500.00) {
                        return 12.0;
                    } else if (waterDepth > 500.0) {
                        return 8.0;
                    }
                }
            } else if (terrain.equalsIgnoreCase("ONSHORE")) {
                return 20.0;
            } else if (terrain.equalsIgnoreCase("INLAND BASIN")) {
                return 10.0;
            }
        }
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "PSC_ID", referencedColumnName = "ID")
    public ProductionSharingContract getContract() {
        return contract;
    }

    public void setContract(ProductionSharingContract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OilField oilField = (OilField) o;

        if (id != null ? !id.equals(oilField.id) : oilField.id != null) {
            return false;
        }
        return title != null ? title.equals(oilField.title) : oilField.title == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
