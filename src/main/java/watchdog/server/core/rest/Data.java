package watchdog.server.core.rest;

import watchdog.server.core.service.DataService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by admin on 23.09.2016.
 */
@Path("/data")
public class Data {

    @Context
    SecurityContext securityContext;

    DataService dataService = new DataService();

    @POST
    @Secured
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveData(@Context SecurityContext securityContext, String string) {
        System.out.println("login:"+securityContext.getUserPrincipal().getName());
        //dataService.saveDataJson(securityContext.getUserPrincipal().getName(), string);
        return Response.status(200).build();
    }

    @GET
    @Path("/getByUser")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTrackInJSON() {
        return "Ahoj";
    }
}