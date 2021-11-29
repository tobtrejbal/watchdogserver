package watchdog.server.core.rest;

import watchdog.server.core.model.User;
import watchdog.server.core.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 28.09.2016.
 */
@Path("/user")
public class UserRest {

    UserService userService = new UserService();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveData(String body) {
        System.out.print(body);
        userService.createUser(body);
        return Response.status(201).entity("haha").build();
    }

    @GET
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response test() {
        User user = new User();
        user.setPassword("pokus");
        user.setLogin("pokus");
        userService.createUser(user);
        return Response.status(201).entity("haha").build();
    }
}