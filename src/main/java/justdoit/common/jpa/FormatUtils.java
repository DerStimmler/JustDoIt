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

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Lichter, Ansgar
 */
public class FormatUtils {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    public static String formatDate(String dateToParse) {
        try {
            java.util.Date date = DATE_FORMATTER.parse(dateToParse);
            return DATE_FORMATTER.format(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String formatTime(String dateToParse) {
        try {
            java.util.Date date = TIME_FORMATTER.parse(dateToParse);
            return TIME_FORMATTER.format(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
