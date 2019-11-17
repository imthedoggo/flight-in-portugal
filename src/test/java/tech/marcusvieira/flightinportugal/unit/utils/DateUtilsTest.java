package tech.marcusvieira.flightinportugal.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import tech.marcusvieira.flightinportugal.utils.DateUtils;

public class DateUtilsTest {

    @Test
    void shouldConvertDateToLocalDate() {
        String date = "10/12/2019";
        final LocalDate convertedDate = DateUtils.toLocalDate(date);

        assertEquals(10, convertedDate.getDayOfMonth());
        assertEquals(12, convertedDate.getMonthValue());
        assertEquals(2019, convertedDate.getYear());
    }

    @Test
    void shouldNotConvertNullDateToLocalDate() {
        final LocalDate convertedDate = DateUtils.toLocalDate(null);

        assertNull(convertedDate);
    }

    @Test
    void shouldConvertLocalDateToString() {
        LocalDate date = LocalDate.now();
        final String convertedDate = DateUtils.localDateToString(date);

        assertEquals(date.getDayOfMonth(), Integer.valueOf(convertedDate.substring(0, 2)));
        assertEquals(date.getMonthValue(), Integer.valueOf(convertedDate.substring(3, 5)));
        assertEquals(date.getYear(), Integer.valueOf(convertedDate.substring(6, 10)));
    }

    @Test
    void shouldNotConvertNullLocalDateToString() {
        final String convertedDate = DateUtils.localDateToString(null);

        assertNull(convertedDate);
    }
}
