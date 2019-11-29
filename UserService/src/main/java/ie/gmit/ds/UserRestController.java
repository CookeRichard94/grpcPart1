/*
Richard Cooke
G00331787@gmit.ie
 */

package ie.gmit.ds;

//Imports
import sun.awt.windows.WPrinterJob;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final Validator validator;

    private final String HOST = "localhost";
    private final int PORT = 50551;

    public UserRestController(Validator validator) {
        this.validator = validator;
    }

    // Get Request to return all users in the database
    // works
    @GET
    @Path("/users")
    public Response getUsers() {
        return Response.ok(UserDB.getUsers()).build();
    }

    // Get request to return a user specified by their unique id, which is passed as a parameter
    // works
    @GET
    @Path("users/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        User user = UserDB.getUser(id);
        if (user != null)
            return Response.ok(user).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    // Post request that adds a new user
    // works
    @POST
    @Path("/users")
    public Response createUser(User user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User u = UserDB.getUser(user.getId());


        if (u == null) {
            if (violations.size() > 0) {
                ArrayList<String> validationMessages = new ArrayList<String>();
                for (ConstraintViolation<User> violation : violations) {
                    validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
                }
                return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
            } else {
                UserDB.updateUser(user.getId(), new User(user.getId(),user.getUserName(),user.getPassword(), user.getEmail()));
                return Response.created(new URI("/users/" + user.getId())).build();
            }
        } else
            return Response.status(Status.NOT_FOUND).build();
    }


    // Delete request to delete a single user specified by their id which is passed as a parameter
    // works
    @DELETE
    @Path("users/{id}")
    public Response removeUserById(@PathParam("id") Integer id) {
        User user = UserDB.getUser(id);
        if (user != null) {
            UserDB.deleteUser(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    //Put request to update a specific user
    //Works
    @PUT
    @Path("users/{id}")
    public Response updateUserById(@PathParam("id") Integer id, User user) {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = UserDB.getUser(user.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            user.setId(id);
            UserDB.createUser(user);
            return Response.ok(user).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    // Post request to create a login attempt
    //Works
    @POST
    @Path("/login")
    public Response login(UserLogin login) {
        // new instance of user client class for the login
        UserClient uc = new UserClient("localhost", 50551);
        // validation
        Set<ConstraintViolation<UserLogin>> violations = validator.validate(login);
        User u = UserDB.getUsername(login.getUsername());

        System.out.println(u);

        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserLogin> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }

        if (u != null) {
            boolean isValid = uc.validate(login.getPassword(), uc.decodeHexString(u.getHashedPassword()), uc.decodeHexString(u.getSalt()));
            if (isValid) {
                return Response.status(Status.ACCEPTED).build();
            }
        }
        return Response.status(Status.UNAUTHORIZED).build();

    }
}


