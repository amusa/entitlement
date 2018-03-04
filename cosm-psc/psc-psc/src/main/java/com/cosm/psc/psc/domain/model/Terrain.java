package com.cosm.psc.psc.domain.model;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Terrain {
	private String terrain;
	private double waterDepth;

	public Terrain(String terrain, double waterDepth) {
		this.terrain = terrain;
		this.waterDepth = waterDepth;
	}

	public String getTerrain() {
		return terrain;
	}

	public double getWaterDepth() {
		return waterDepth;
	}

	@Transient
	public double royaltyRate() {
		if (terrain != null) {
			if (terrain.equalsIgnoreCase("OFFSHORE")) {
				if (waterDepth <= 100.0) {
					return 18.5;
				} else if (waterDepth > 100.0 & waterDepth <= 200.0) {
					return 16.5;
				} else if (waterDepth > 200.0 & waterDepth <= 500.00) {
					return 12.0;
				} else if (waterDepth > 500.0) {
					return 8.0;
				}

			} else if (terrain.equalsIgnoreCase("ONSHORE")) {
				return 20.0;
			} else if (terrain.equalsIgnoreCase("INLAND BASIN")) {
				return 10.0;
			}
		}
		return 0;
	}

	@Transient
	public double getInvestmentTaxAllowanceCredit() {
		if (terrain != null) {
			if (terrain.equalsIgnoreCase("OFFSHORE")) {
				if (waterDepth <= 100.0) {
					return 10.0;
				} else if (waterDepth > 100.0 & waterDepth <= 200.0) {
					return 15.0;
				} else if (waterDepth > 200.0) {
					return 50.0;
				}
			} else if (terrain.equalsIgnoreCase("ONSHORE")) {
				return 5.0;
			} else if (terrain.equalsIgnoreCase("INLAND BASIN")) {
				return 50.0;
			}
		}
		return 0;
	}
	
	
	@Transient
    public double getPetroleumProfitTaxRate(Date refDate, ProductionSharingContract psc) {
        if (terrain != null) {
            if (terrain.equalsIgnoreCase("OFFSHORE")) {
                if (waterDepth >= 201.0) {
                    return 50.0;
                }
            }
            int dateDiff = psc.getFirstOilDuration(refDate);
            if (dateDiff <= 5) {
                return 65.75;//TODO:USE ENUM
            }
            return 85.0;
        }

        return 0;
    }

}
