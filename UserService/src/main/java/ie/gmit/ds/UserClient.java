/*
Richard Cooke
G00331787@gmit.ie
 */

// Adapted from PasswordClient file in grpcPart1 and including methods from https://www.baeldung.com/java-byte-arrays-hex-strings

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

public class UserClient {
    private static final Logger logger =
            Logger.getLogger(UserClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    public UserClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        UserClient client = new UserClient("localhost", 50551);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    // Method to convert byte array to a hex string
    private String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    // Generates hex pair for each byte
    private String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    // Method to convert hex to binary equivalent
    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    // Concats hex to byte equivalent
    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    // Method converts each hexadecimal pair to a byte
    public byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }



    public String[] hash(int userId, String password){
        logger.info("Hash request detail:\nUser ID: " + userId + "\nPassword: " + password);
        //Create a request to send to PasswordServiceImpl hash method
        HashInput request =  HashInput.newBuilder().setUserID(userId)
              .setPassword(password).build();

        try{

            // Hashing password and salt and then adding them to a string aray to be utilized in the User constructor
            HashOutput response = syncPasswordService.hash(request);
            String HashPassword = encodeHexString(response.getHashedPassword().toByteArray());
            String salt = encodeHexString(response.getSalt().toByteArray());

            return new String[]{HashPassword, salt};
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());

        }

        return null;
    }

    public boolean validate(String password, byte[] hashedPassword, byte[] salt){
        ValidateInput request=ValidateInput.newBuilder()
                .setPassword(password)
                .setHashedPassword(ByteString.copyFrom(hashedPassword))
                .setSalt(ByteString.copyFrom(salt))
                .build();

        BoolValue response;

        try{
            response = syncPasswordService.validate(request);
            logger.info(String.format("Response from server: %s ", response));
            return response.getValue();
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());

            return false;
        }
    }

}
