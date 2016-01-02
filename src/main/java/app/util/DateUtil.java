package app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import app.model.DateFormat;

public class DateUtil {

    public static LocalDate formatLocaleDateToDate(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = format.format(date);

            Date datef = format.parse(stringDate);


        LocalDate dateOnly = LocalDateTime.ofInstant(datef.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return dateOnly;
    }

    public static String formatDate(LocalDate date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date convertToDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String coToDate = format.format(convertToDate);
        return coToDate;
    }


    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        String stringDate = format.format(date);
        return stringDate;
    }


    public static String formatDateToTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        String stringDate = format.format(date);
        return stringDate;
    }
}
