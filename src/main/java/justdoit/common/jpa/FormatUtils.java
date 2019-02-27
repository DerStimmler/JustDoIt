/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package justdoit.common.jpa;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Lichter, Ansgar
 */
public class FormatUtils {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    public static Date parseDate(String dateToParse) {
        try {
            java.util.Date date = DATE_FORMATTER.parse(dateToParse);
            return new Date(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static Time parseTime(String dateToParse) {
        try {
            java.util.Date date = TIME_FORMATTER.parse(dateToParse);
            return new Time(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
