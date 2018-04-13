package com.cosm.psc.psc.infrastructure.rest;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.psc.interfaces.rest.ProductionService;
import javax.ws.rs.core.Response;

@Stateless
public class RestProductionService implements ProductionService {

    private Client client;
    private WebTarget target;

    public boolean isFirstProductionOfYear(ProductionSharingContractId pscId, int year, int month) {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/production/api/first").queryParam("pscId", pscId).queryParam("year", year)
                .queryParam("month", month);

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        boolean isFirst = false;
        try {
            if (response.getStatus() == 200) {
                isFirst = response.readEntity(Boolean.class);
            }
        } finally {
            response.close();
        }

        return isFirst;
    }
}