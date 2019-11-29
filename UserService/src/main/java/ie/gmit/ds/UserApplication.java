/*
Richard Cooke
G00331787@gmit.ie
 */

package ie.gmit.ds;

import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import ie.gmit.ds.UserRestController;
import org.slf4j.Logger;

public class UserApplication extends Application<UserServiceConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args) throws Exception {
        new UserApplication().run(args);
    }

    public void run(UserServiceConfig userServiceConfig, Environment e) throws Exception{
        LOGGER.info("Registering REST resources");
        e.jersey().register(new UserRestController(e.getValidator()));

        final UserHealthCheck healthCheck = new UserHealthCheck();
        e.healthChecks().register("User", healthCheck);
    }
}
