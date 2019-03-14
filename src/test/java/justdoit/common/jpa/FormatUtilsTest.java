package justdoit.common.jpa;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.junit.Test;
import static org.junit.Assert.*;

public class FormatUtilsTest {

    @Test
    public void testFormatDate() {
        Date date = new Date(2019 - 1900, 0, 1);
        String expResult = "01.01.2019";
        String result = FormatUtils.formatDate(date);
        assertEquals("Date object isn't formatted in correct format", expResult, result);
    }

    /**
     * Test of formatTime method, of class FormatUtils.
     */
    @Test
    public void testFormatTime() {
        Time time = new Time(0, 0, 0);
        String expResult = "00:00";
        String result = FormatUtils.formatTime(time);
        assertEquals("Time object isn't formatted in correct format", expResult, result);
    }

    /**
     * Test of formatTimestamp method, of class FormatUtils.
     */
    @Test
    public void testFormatTimestamp() {
        Timestamp timestamp = new Timestamp(2019 - 1900, 0, 1, 0, 0, 0, 0);
        String expResult = "01.01.2019 00:00";
        String result = FormatUtils.formatTimestamp(timestamp);
        assertEquals("Timestamp object isn't formatted in correct format", expResult, result);
    }

    /**
     * Test of parseDate method, of class FormatUtils.
     */
    @Test
    public void testParseDate() {
        String date = "2019-01-01";
        Date expResult = new Date(2019 - 1900, 0, 1);
        Date result = FormatUtils.parseDate(date);
        assertEquals("Parsed Date is not equal to Date as String", expResult, result);
    }

    /**
     * Test of parseTime method, of class FormatUtils.
     */
    @Test
    public void testParseTime() {
        Time expResult = new Time(0, 0, 0);

        //String without Seconds
        String time = "00:00";
        Time result = FormatUtils.parseTime(time);
        assertEquals("Parsed Time is not equal to Time as String without Seconds", expResult, result);

        // String with seconds
        time = "00:00:00";
        result = FormatUtils.parseTime(time);
        assertEquals("Parsed Time is not equal to Time as String with Seconds", expResult, result);
    }

}
