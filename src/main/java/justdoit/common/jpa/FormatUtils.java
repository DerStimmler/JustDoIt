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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FormatUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(Time time) {
        return TIME_FORMAT.format(time);
    }

    public static String formatTimestamp(Timestamp timestamp) {
        return TIMESTAMP_FORMAT.format(timestamp);
    }

    public static Date parseDate(String date) {
        return Date.valueOf(date);
    }

    public static Time parseTime(String time) {
        if (time.length() < 8) {
            time = time + ":00";
        }
        return Time.valueOf(time);
    }

}
