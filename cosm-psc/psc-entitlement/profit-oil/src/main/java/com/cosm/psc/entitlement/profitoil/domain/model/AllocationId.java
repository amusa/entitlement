package com.cosm.psc.entitlement.profitoil.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class AllocationId {
	private UUID id;

	public AllocationId(String id) {
		setId(UUID.fromString(id));
	}

	@NotNull
	@Column(name = "ALLOCATION_ID")
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}
}
