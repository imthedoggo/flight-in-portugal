package tech.marcusvieira.flightinportugal.utils;

import tech.marcusvieira.flightinportugal.enums.ErrorData;
import tech.marcusvieira.flightinportugal.errorhandlers.ErrorItem;

public final class ErrorUtils {

    /**
     * Creates error based on {@link ErrorData} and {@code message}
     */
    public static ErrorItem createError(ErrorData errorData, String message) {
        if (errorData == null) {
            return null;
        }

        return ErrorItem.builder()
            .code(errorData.getCode())
            .message(message)
            .info(errorData.getInfo())
            .build();
    }
}
