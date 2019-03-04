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

    public static final SimpleDateFormat DATE_PARSE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_PARSE = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static String formatDate(String dateToParse) {
        try {
            java.util.Date date = DATE_PARSE.parse(dateToParse);
            return DATE_FORMAT.format(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String formatTime(String dateToParse) {
        try {
            java.util.Date date = TIME_PARSE.parse(dateToParse);
            return TIME_FORMAT.format(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
