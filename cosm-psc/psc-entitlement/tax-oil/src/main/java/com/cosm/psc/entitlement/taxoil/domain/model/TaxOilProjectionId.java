package com.cosm.psc.entitlement.taxoil.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class TaxOilProjectionId {

	private UUID id;

	public TaxOilProjectionId(String id) {
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
