package com.cosm.psc.entitlement.royalty.infrastructure.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.entitlement.royalty.application.ProductionSharingContractClientService;

public class RestProductionSharingContractClientService implements ProductionSharingContractClientService{

	private Client client;
	private WebTarget target;

	@Override
	public double concessionRental(ProductionSharingContractId pscId, int year, int month) {		
		client = ClientBuilder.newClient();	   
	    target = client.target("http://localhost/psc/concession")
	       .queryParam("id", pscId)
	       .queryParam("year", year)
	       .queryParam("month", month);
	    
	    return target.request(MediaType.TEXT_PLAIN)
	            .get(Double.class);
	}
	

}
