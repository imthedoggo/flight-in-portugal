package tech.marcusvieira.flightinportugal.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    /**
     * Used for convert string formatted in dd/MM/yyyy to {@link LocalDate}.
     */
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Converts string format dd/MM/yyyy to {@link LocalDate}
     */
    public static LocalDate toLocalDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Converts {@link LocalDate} to string format dd/MM/yyyy
     */
    public static String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }
}
