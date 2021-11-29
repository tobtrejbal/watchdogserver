package watchdog.server.core.rest;

import watchdog.server.core.service.DataService;
import watchdog.server.core.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by admin on 23.09.2016.
 */

@Path("/")
public class Hello {

    @GET
    @Secured
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "aaaahoj bubaku";
    }
}

