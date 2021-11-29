package watchdog.server.core.rest;

import com.google.gson.Gson;
import watchdog.server.core.application.Core;
import watchdog.server.core.model.Token;
import watchdog.server.core.model.User;
import watchdog.server.core.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by admin on 28.09.2016.
 */
@Path("/authentication")
public class Authentication {

    UserService userService = new UserService();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(String userJson) {

        try {

            System.out.println("request for authentication");

            Gson gson = new Gson();

            User user = gson.fromJson(userJson, User.class);
            // Authenticate the user using the credentials provided
            authenticate(user.getLogin(), user.getPassword());

            // Issue a token for the user
            String token = issueToken(user.getLogin());

            // Return the token on the response
            return Response.ok(gson.toJson(new Token(token), Token.class),MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        System.out.println("login: "+username+" password"+password);
        User user = userService.findUser(username, password);
        if(user == null) {
            throw new Exception();
        }
    }

    private String issueToken(String username) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        Core core = Core.getsInstance();
        core.getUsers().put(token, username);
        return token;
    }

}