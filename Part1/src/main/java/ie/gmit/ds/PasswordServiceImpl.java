package ie.gmit.ds;

//Imports
import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {

    //Retrieves the class name using a logger
    private static final Logger logger =
            Logger.getLogger(PasswordServiceImpl.class.getName());

    @Override
    //Method to hash the passwords and add unique salt
    public void hash(HashInput request, StreamObserver<HashOutput> responseObserver) {
        //converts the password to a character array
        logger.info(request.toString());
        char[] password = request.getPassword().toCharArray();
        byte[] salt = Passwords.getNextSalt();
        // stores hashed password made from password and salt
        byte[] hashedPassword = Passwords.hash(password, salt);

        //creates a response
        HashOutput response = HashOutput.newBuilder().setUserID(request.getUserID())
                .setHashedPassword(ByteString.copyFrom(hashedPassword))
                .setSalt(ByteString.copyFrom(salt))
                .build();

        logger.info(response.toString());
        //sends a response to the client
        responseObserver.onNext(response);
        //completes the request
        responseObserver.onCompleted();
    }

    @Override
    //Method to validate the password has been hashed
    public void validate(ValidateInput request, StreamObserver<BoolValue> responseObserver){
        BoolValue response = BoolValue.of(Passwords.isExpectedPassword(
                request.getPassword().toCharArray(),
                request.getSalt().toByteArray(),
                request.getHashedPassword().toByteArray()));

        //Sends a response to the client
        responseObserver.onNext(response);
        //Completes the request
        responseObserver.onCompleted();
    }
}
