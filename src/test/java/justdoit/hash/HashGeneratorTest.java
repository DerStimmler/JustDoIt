/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.hash;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tim.schneider
 */
public class HashGeneratorTest {

    HashGenerator hashGenerator;

    public HashGeneratorTest() {
        this.hashGenerator = new HashGenerator();
    }

    @org.junit.jupiter.api.Test
    public void testShouldHashPwWithSHA265() throws Exception {
        String password = "Test12345";
        String expResult = "EGrDBK45vEAp2w+vDRc0vVodwkdDMejhcDk2WEdTbXM=";
        String result = hashGenerator.getHashText(password);
        assertEquals(expResult, result);
    }
}
