package com.cosm.psc.psc.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ContractTitle {

	private String title;
	
	public ContractTitle(String title) {
		this.title = title;
	}
	
	@NotNull
    @Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
}
