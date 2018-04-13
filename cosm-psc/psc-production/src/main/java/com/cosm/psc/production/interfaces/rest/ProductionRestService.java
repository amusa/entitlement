package com.cosm.psc.production.interfaces.rest;

import com.cosm.common.domain.model.FiscalPeriod;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.psc.production.application.ProductionService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Stateless
@Path("/")
public class ProductionRestService {

    @Inject
    private ProductionService productionService;

    @GET  
    @Path("/post")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response postProduction(@QueryParam("id") String id,
            @QueryParam("year") Integer year,
            @QueryParam("month") Integer month) {

        ProductionSharingContractId pscId = new ProductionSharingContractId(id);
        FiscalPeriod fiscalPeriod = new FiscalPeriod(year, month);
        
        productionService.post(fiscalPeriod, pscId);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET  
    @Path("/first")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response firstProduction(@QueryParam("id") String id,
            @QueryParam("year") Integer year,
            @QueryParam("month") Integer month) {

        ProductionSharingContractId pscId = new ProductionSharingContractId(id);
        FiscalPeriod fiscalPeriod = new FiscalPeriod(year, month);
        
        //TODO:implement call to productionService
               
        ResponseBuilder builder = Response.ok(true);      

       return builder.build();
    }
}
