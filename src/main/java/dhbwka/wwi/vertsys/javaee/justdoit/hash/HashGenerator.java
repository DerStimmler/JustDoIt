/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.ejb.Stateless;

/**
 *
 * @author Licht
 */
@Stateless
public class HashGenerator {
   public String algorithmName = "SHA-256";
   
   public HashGenerator() {
       //EJB needs this one
   }
   
   public String getHashText(String text) {
       try{
          MessageDigest messageDigest = MessageDigest.getInstance(this.algorithmName);
          byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
          String encoded = Base64.getEncoder().encodeToString(hash);
          
          return encoded;
       } catch (NoSuchAlgorithmException ex) {
           throw new RuntimeException(ex);
       }
   }
}
