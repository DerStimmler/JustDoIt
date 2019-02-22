package justdoit.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.ejb.Stateless;


@Stateless
public class HashGenerator {

    public HashGenerator() {
        // EJB need this one.
    }

    String algorithm = "SHA-256";
    
    public String getHashText(String text) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);

            return encoded;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}