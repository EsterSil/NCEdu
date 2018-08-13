package jettytest.datautils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoWrapper {

    private static MessageDigest messageDigest;



    public static String wrape(String password){
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());

        return new String(messageDigest.digest());
    }

}
