package com.cosm.psc.entitlement.profitoil.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class ProfitOilProjectionId {
	private UUID id;

	public ProfitOilProjectionId(String id) {
		setId(UUID.fromString(id));
	}

	@NotNull
	@Column(name = "PROFIT_OIL_ID")
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}
}
