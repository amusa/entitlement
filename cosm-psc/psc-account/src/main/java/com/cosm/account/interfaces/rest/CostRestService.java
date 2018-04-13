package com.cosm.account.interfaces.rest;

import com.cosm.account.application.ProductionCostService;
import com.cosm.common.domain.model.FiscalPeriod;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cosm.common.domain.model.ProductionSharingContractId;
import javax.ws.rs.core.Response;

@Stateless
@Path("/post")
public class CostRestService {

    @Inject
    private ProductionCostService costService;

    @GET  
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response postProductionCost(@QueryParam("id") String id,
            @QueryParam("year") Integer year,
            @QueryParam("month") Integer month) {

        ProductionSharingContractId pscId = new ProductionSharingContractId(id);
        FiscalPeriod fiscalPeriod = new FiscalPeriod(year, month);
        
        costService.post(fiscalPeriod, pscId);

        return Response.ok().build();
    }

}
