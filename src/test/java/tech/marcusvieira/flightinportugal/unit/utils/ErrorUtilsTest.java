package tech.marcusvieira.flightinportugal.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import tech.marcusvieira.flightinportugal.enums.ErrorData;
import tech.marcusvieira.flightinportugal.errorhandlers.ErrorItem;
import tech.marcusvieira.flightinportugal.utils.ErrorUtils;

public class ErrorUtilsTest {

    @Test
    void shouldCreateError() {
        String errorMessage = "Error Message";
        final ErrorItem errorItem = ErrorUtils.createError(ErrorData.INVALID_ITINERARY, errorMessage);

        assertEquals(ErrorData.INVALID_ITINERARY.getCode(), errorItem.getCode());
        assertEquals(errorMessage, errorItem.getMessage());
        assertEquals(ErrorData.INVALID_ITINERARY.getInfo(), errorItem.getInfo());
    }

    @Test
    void shouldNotCreateErrorWithoutErrorData() {
        String errorMessage = "Error Message";
        final ErrorItem errorItem = ErrorUtils.createError(null, errorMessage);

        assertNull(errorItem);
    }
}
