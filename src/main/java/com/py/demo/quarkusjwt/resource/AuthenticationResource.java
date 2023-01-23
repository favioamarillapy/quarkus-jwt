package com.py.demo.quarkusjwt.resource;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.py.demo.quarkusjwt.model.AuthRequest;
import com.py.demo.quarkusjwt.model.AuthResponse;
import com.py.demo.quarkusjwt.model.User;
import com.py.demo.quarkusjwt.util.PBKDF2Encoder;
import com.py.demo.quarkusjwt.util.TokenUtils;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author favio.amarilla
 */
@Path("/auth")
public class AuthenticationResource {

    @Inject
    PBKDF2Encoder passwordEncoder;

    @ConfigProperty(name = "quarkusjwt.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {

        User user = User.findByUsername(authRequest.username);

        if (user != null && user.password.equals(passwordEncoder.encode(authRequest.password))) {
            try {
                return Response.ok(new AuthResponse(user, TokenUtils.generateToken(user.username, user.role, duration, issuer))).build();
            } catch (Exception e) {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @PermitAll
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response register(AuthRequest authRequest) {

        User user = new User(authRequest.username, passwordEncoder.encode(authRequest.password), authRequest.role);
        user.persist();

        return Response.ok(user).build();
    }
}
