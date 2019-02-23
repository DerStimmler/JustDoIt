package justdoit.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;

@Stateless
public class HashGenerator {

    public HashGenerator() {
        // EJB needs this one.
    }

    String algorithm = "SHA-256";

    public String getHashText(String text) {

//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
//            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
//            String encoded = Base64.getEncoder().encodeToString(hash);
//
//            return encoded;
//        } catch (NoSuchAlgorithmException ex) {
//            throw new RuntimeException(ex);
//        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = this.toHexString(hash);

            return encoded;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }
}
