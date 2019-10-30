package ie.gmit.ds;

//Imports
import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class passwordClient {
    private static final Logger logger =
            Logger.getLogger(passwordClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    public passwordClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        passwordClient client = new passwordClient("localhost", 50551);

        try {
            client.hash(1234, "Richard Cooke");
            client.hash(5678, "Hello User");
            client.hash(3456, "GMIT");
            client.hash(7890, "xzzx");

            byte[] salt = Passwords.getNextSalt();
            client.validate("mark Gill", Passwords.hash("mark Gill".toCharArray(),salt), salt);
            client.validate("quanemious st brow", Passwords.hash("quanemious st brown".toCharArray(),salt), salt);
        }finally {
            // Don't stop process keep alive to receive async response
            Thread.currentThread().join();
        }

    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void hash(int userId, String password){
        logger.info("Hash request detail:\nUser ID: " + userId + "\nPassword: " + password);
        //Create a request to send to PasswordServiceImpl hash method
        HashInput request =  HashInput.newBuilder().setUserID(userId)
              .setPassword(password).build();

        //Create a response to read response from server and log it
        HashOutput response = null;

        try{
            response = syncPasswordService.hash(request);
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }

        logger.info(String.format("Response from server: %s ", response));
    }

    public void validate(String password, byte[] hashedPassword, byte[] salt){
        ValidateInput request=ValidateInput.newBuilder()
                .setPassword(password)
                .setHashedPassword(ByteString.copyFrom(hashedPassword))
                .setSalt(ByteString.copyFrom(salt))
                .build();

        BoolValue response;

        try{
            response = syncPasswordService.validate(request);
            logger.info(String.format("Response from server: %s ", response));
            System.out.println(response.getValue());
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());

            return;
        }
    }

}
