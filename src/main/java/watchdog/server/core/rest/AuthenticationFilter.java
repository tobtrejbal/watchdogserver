package watchdog.server.core.rest;

import watchdog.server.core.application.Core;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Random;

/**
 * Created by admin on 28.09.2016.
 */

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    Random random = new Random();

    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println(authorizationHeader);
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            validateToken(token, requestContext);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token, ContainerRequestContext requestContext) throws Exception {

        Core core = Core.getsInstance();

        final String username = core.getUser(token) ;

        System.out.println(token);

        requestContext.setSecurityContext(new SecurityContext() {

            public Principal getUserPrincipal() {

                return new Principal() {

                    public String getName() {
                        return username;
                    }
                };
            }

            public boolean isUserInRole(String role) {
                return true;
            }

            public boolean isSecure() {
                return false;
            }

            public String getAuthenticationScheme() {
                return null;
            }
        });
    }
}
