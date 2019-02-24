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
    public void testHashPasswordwithSHA256toHex() throws Exception {
        String password = "Test123";
        String expResult = "D9B5F58F0B38198293971865A14074F59EBA3E82595BECBE86AE51F1D9F1F65E";
        String result = hashGenerator.getHashText(password);
        assertEquals(expResult, result);
    }
}
