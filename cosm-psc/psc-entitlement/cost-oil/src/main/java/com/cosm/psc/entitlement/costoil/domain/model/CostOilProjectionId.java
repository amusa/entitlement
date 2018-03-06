package com.cosm.psc.entitlement.costoil.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class CostOilProjectionId {
	private UUID id;

	public CostOilProjectionId(String id) {
		setId(UUID.fromString(id));
	}

	@NotNull
	@Column(name = "ID")
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}
}
