package ie.gmit.ds;

//Imports
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class passwordServer {
    //Creating grpcServer
    private Server grpcServer;
    //Creating logger to retrieve the name of the class
    private static final Logger logger = Logger.getLogger(passwordServer.class.getName());
    //Opening a port on 50551
    private static final int PORT = 50551;

    //Function that starts the server
    private void start() throws IOException {
        grpcServer = ServerBuilder.forPort(PORT)
                .addService(new PasswordServiceImpl())
                .build()
                .start();
        //Sending message to the console displaying the port number in use
        logger.info("Server started, listening on " + PORT);

    }

    //Function that stops the server
    private void stop() {
        if (grpcServer != null) {
            grpcServer.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }

    //Main Method
    public static void main(String[] args) throws IOException, InterruptedException {
        final passwordServer passwordServer = new passwordServer();

        //Call for the server to be initiated
        passwordServer.start();
        passwordServer.blockUntilShutdown();
    }
}
