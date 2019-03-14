package justdoit.hash;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HashGeneratorTest {

    HashGenerator hashGenerator;

    public HashGeneratorTest() {
        this.hashGenerator = new HashGenerator();
    }

    @Test
    public void testHashPasswordwithSHA256toHex() throws Exception {
        String password = "Test123";
        String expResult = "D9B5F58F0B38198293971865A14074F59EBA3E82595BECBE86AE51F1D9F1F65E";

        String result = hashGenerator.getHashText(password);
        assertEquals(expResult, result);

        // Wenn das Passwort noch mal gehasht muss der selbe Hash raus kommen.
        String newResult = hashGenerator.getHashText(password);
        assertEquals("Both generated Hashes are not equal!", result, newResult);
        assertEquals(expResult, newResult);
    }
}
