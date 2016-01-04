package app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static LocalDate formatLocaleDateToDate(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = format.format(date);
        Date datef = format.parse(stringDate);
        return LocalDateTime.ofInstant(datef.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static String formatDate(LocalDate date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date convertToDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return format.format(convertToDate);
    }
    public static Date formatLDtoDate(LocalDate date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        return format.format(date);
    }

    public static String formatDateToTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        return format.format(date);
    }
}
