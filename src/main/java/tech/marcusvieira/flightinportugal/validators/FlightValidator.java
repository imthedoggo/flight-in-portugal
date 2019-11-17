package tech.marcusvieira.flightinportugal.validators;

import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_DATE_FROM_HIGHER_THAN_DATE_TO;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_EQUAL_FROM_TO_ITINERARY;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_CURRENCY;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_DATE_FORMAT;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_FUTURE_DATE;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_ITINERARY;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_PAST_DATE;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.CURRENCY_FIELD;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.DATE_FROM_FIELD;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.DATE_TO_FIELD;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.FLY_FROM_FIELD;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.FLY_TO_FIELD;
import static tech.marcusvieira.flightinportugal.enums.ErrorData.INVALID_CURRENCY;
import static tech.marcusvieira.flightinportugal.enums.ErrorData.INVALID_DATE;
import static tech.marcusvieira.flightinportugal.enums.ErrorData.INVALID_ITINERARY;
import static tech.marcusvieira.flightinportugal.utils.DateUtils.toLocalDate;
import static tech.marcusvieira.flightinportugal.utils.ErrorUtils.createError;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import tech.marcusvieira.flightinportugal.controllers.FlightController;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.errorhandlers.ErrorItem;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.InvalidRequestParamsException;

public class FlightValidator {

    /**
     * Validates flight request params used to call {@link FlightController#getFlightsAveragePrice(String, String,
     * String, String, String)}
     *
     * @param flyFrom  - IATA code of the origin airport
     * @param flyTo    - IATA code of the destination airport
     * @param dateFrom - search flights from this date (dd/mm/YYYY).
     * @param dateTo   - search flights up to this date (dd/mm/YYYY).
     * @param currency - currency used to get price
     */
    public static void validate(String flyFrom, String flyTo, String dateFrom, String dateTo, String currency) {

        //keep all errors to return them into response in one time
        List<ErrorItem> errors = new ArrayList<>();

        validateFromToItineraries(errors, flyFrom, flyTo);
        validateFromToDates(errors, dateFrom, dateTo);
        validateCurrency(errors, currency);

        if (errors.size() > 0) {
            throw new InvalidRequestParamsException(errors);
        }
    }

    private static void validateFromToItineraries(List<ErrorItem> errors, String flyFrom, String flyTo) {
        validateItinerary(errors, flyFrom, FLY_FROM_FIELD);
        validateItinerary(errors, flyTo, FLY_TO_FIELD);

        if (flyFrom.equalsIgnoreCase(flyTo)) {
            errors.add(createError(INVALID_ITINERARY, String.format(MESSAGE_EQUAL_FROM_TO_ITINERARY, FLY_FROM_FIELD,
                FLY_TO_FIELD)));
        }
    }

    private static void validateItinerary(List<ErrorItem> errors, String itinerary, String field) {
        try {
            FlightItinerary.valueOf(itinerary);
        } catch (IllegalArgumentException ex) {
            errors.add(createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, field)));
        }
    }

    private static void validateFromToDates(List<ErrorItem> errors, String dateFrom, String dateTo) {
        validateDate(errors, dateFrom, DATE_FROM_FIELD);
        validateDate(errors, dateTo, DATE_TO_FIELD);

        final boolean hasDateErrors = errors.stream().anyMatch(e -> e.getCode().equals(INVALID_DATE.getCode()));

        if (!hasDateErrors && dateFrom != null && dateTo != null
            && toLocalDate(dateFrom).isAfter(toLocalDate(dateTo))) {
            errors.add(createError(INVALID_DATE, String.format(MESSAGE_DATE_FROM_HIGHER_THAN_DATE_TO, DATE_FROM_FIELD,
                DATE_TO_FIELD)));
        }
    }

    private static void validateDate(List<ErrorItem> errors, String date, String field) {
        if (date != null) {
            try {
                final LocalDate today = LocalDate.now();
                final LocalDate convertedDate = toLocalDate(date);

                if (convertedDate.isBefore(today)) {
                    errors.add(createError(INVALID_DATE, String.format(MESSAGE_INVALID_PAST_DATE, field)));
                }

                final LocalDate maxDate = LocalDate.now().plus(Period.ofYears(3));
                if (convertedDate.isAfter(maxDate)) {
                    errors.add(createError(INVALID_DATE, String.format(MESSAGE_INVALID_FUTURE_DATE, field)));
                }
            } catch (DateTimeParseException | IllegalArgumentException ex) {
                errors.add(createError(INVALID_DATE, String.format(MESSAGE_INVALID_DATE_FORMAT, field)));
            }
        }
    }

    private static void validateCurrency(List<ErrorItem> errors, String currency) {
        try {
            Currency.valueOf(currency);
        } catch (IllegalArgumentException ex) {
            errors.add(createError(INVALID_CURRENCY, String.format(MESSAGE_INVALID_CURRENCY, CURRENCY_FIELD)));
        }
    }
}
