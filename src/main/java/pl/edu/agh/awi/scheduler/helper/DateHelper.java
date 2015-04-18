package pl.edu.agh.awi.scheduler.helper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {

    public static Date getDate(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        Date returnVal = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        return returnVal;
    }

}
