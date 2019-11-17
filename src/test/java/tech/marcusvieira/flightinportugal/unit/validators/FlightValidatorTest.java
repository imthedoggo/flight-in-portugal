package tech.marcusvieira.flightinportugal.unit.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_DATE_FROM_HIGHER_THAN_DATE_TO;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_CURRENCY;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_INVALID_DATE_FORMAT;
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
import static tech.marcusvieira.flightinportugal.utils.DateUtils.localDateToString;
import static tech.marcusvieira.flightinportugal.utils.ErrorUtils.createError;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.errorhandlers.ErrorItem;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.InvalidRequestParamsException;
import tech.marcusvieira.flightinportugal.validators.FlightValidator;

public class FlightValidatorTest {

    @ParameterizedTest
    @MethodSource("createFlightsRequestParamsData")
    void shouldValidateFlightsRequestParams(
        String name,
        String flyFrom,
        String flyTo,
        String dateFrom,
        String dateTo,
        String currency,
        List<ErrorItem> errors) {

        if (errors.size() > 0) {
            final InvalidRequestParamsException exception = assertThrows(
                InvalidRequestParamsException.class, () ->
                    FlightValidator.validate(flyFrom, flyTo, dateFrom, dateTo, currency));

            for (ErrorItem error : errors) {
                assertTrue(exception.getErrors().stream()
                    .anyMatch(e -> e.getCode().equals(error.getCode())
                        && e.getInfo().equals(error.getInfo())
                        && e.getMessage().equals(error.getMessage())));
            }
        } else {
            assertDoesNotThrow(() ->
                FlightValidator.validate(flyFrom, flyTo, dateFrom, dateTo, currency));
        }
    }

    private static Stream<Arguments> createFlightsRequestParamsData() {
        final LocalDate today = LocalDate.now();

        return Stream.of(

            //itinerary tests
            Arguments.of("Test valid itinerary OPorto to Lisbon", "OPO", "LIS", localDateToString(today),
                localDateToString(today.plus(Period.ofMonths(6))), Currency.EUR.name(), Collections.emptyList()),
            Arguments.of("Test valid itinerary Lisbon to Oporto", "LIS", "OPO", localDateToString(today),
                localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections.emptyList()),

            //fly from
            Arguments.of("Test flyFrom invalid", "OPX", "LIS", localDateToString(today),
                localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections.singletonList(
                    createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_FROM_FIELD)))),
            Arguments.of("Test empty flyFrom", "", "OPO", localDateToString(today),
                localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections.singletonList(
                    createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_FROM_FIELD)))),

            //fly to
            Arguments.of("Test empty flyTo", "OPO", "", localDateToString(today),
                localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections.singletonList(
                    createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_TO_FIELD)))),
            Arguments.of("Test flyTo invalid", "OPO", "LIT", localDateToString(today),
                localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections.singletonList(
                    createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_TO_FIELD)))),

            //date tests
            Arguments.of("Test dateFrom and dateTo null", "LIS", "OPO", null, null, Currency.EUR.name(),
                Collections.emptyList()),
            Arguments.of("Test dateFrom and dateTo same day", "LIS", "OPO", localDateToString(today),
                localDateToString(today), Currency.EUR.name(),
                Collections.emptyList()),
            Arguments
                .of("Test dateFrom higher than dateTo", "LIS", "OPO", localDateToString(today.plus(Period.ofMonths(2))),
                    localDateToString(today.plus(Period.ofDays(10))), Currency.EUR.name(), Collections
                        .singletonList(createError(INVALID_DATE,
                            String.format(MESSAGE_DATE_FROM_HIGHER_THAN_DATE_TO, DATE_FROM_FIELD,
                                DATE_TO_FIELD)))),
            Arguments.of("Test past date", "LIS", "OPO", localDateToString(today.minus(Period.ofDays(10))),
                localDateToString(today), Currency.EUR.name(), Collections.singletonList(
                    createError(INVALID_DATE, String.format(MESSAGE_INVALID_PAST_DATE, DATE_FROM_FIELD)))),
            Arguments.of("Test both empty dates", "LIS", "OPO", null, null, Currency.EUR.name(),
                Collections.emptyList()),

            //date to
            Arguments
                .of("Test one empty dateTo", "LIS", "OPO", null, localDateToString(today),
                    Currency.EUR.name(),
                    Collections.emptyList()),
            Arguments
                .of("Test invalid day dateTo", "LIS", "OPO", null, "41/12/2019", Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),
            Arguments
                .of("Test invalid month dateTo", "LIS", "OPO", null, "01/13/2019", Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),
            Arguments
                .of("Test invalid year dateTo", "LIS", "OPO", null, "01/12/XVVII", Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),
            Arguments.of("Test invalid date dateTo", "LIS", "OPO", null, "1/3/4", Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),
            Arguments.of("Test invalid date dateTo", "LIS", "OPO", null, "RTrryre", Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),
            Arguments.of("Test empty date dateTo", "LIS", "OPO", null, "", Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)))),

            //date from
            Arguments.of("Test one empty dateTo", "LIS", "OPO", localDateToString(today), null,
                Currency.EUR.name(), Collections.emptyList()),
            Arguments
                .of("Test invalid day dateTo", "LIS", "OPO", "41/12/2019", null, Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),
            Arguments
                .of("Test invalid month dateTo", "LIS", "OPO", "01/13/2019", null, Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),
            Arguments
                .of("Test invalid year dateTo", "LIS", "OPO", "01/12/XVVII", null, Currency.EUR.name(),
                    Collections.singletonList(createError(INVALID_DATE,
                        String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),
            Arguments.of("Test invalid date dateTo", "LIS", "OPO", "1/3/4", null, Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),
            Arguments.of("Test invalid date dateTo", "LIS", "OPO", "RTrryre", null, Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),
            Arguments.of("Test empty date dateTo", "LIS", "OPO", "", null, Currency.EUR.name(),
                Collections.singletonList(createError(INVALID_DATE,
                    String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)))),

            //currency
            Arguments.of("Test valid currency ", "LIS", "OPO", null, null, Currency.BRL.name(),
                Collections.emptyList()),
            Arguments.of("Test invalid currency ", "LIS", "OPO", null, null, "XXT",
                Collections.singletonList(
                    createError(INVALID_CURRENCY, String.format(MESSAGE_INVALID_CURRENCY, CURRENCY_FIELD)))),
            Arguments
                .of("Test empty currency ", "LIS", "OPO", null, null, "", Collections.singletonList(
                    createError(INVALID_CURRENCY, String.format(MESSAGE_INVALID_CURRENCY, CURRENCY_FIELD)))),

            //test many errors
            Arguments
                .of("Test many errors", "LIR", "OPY", "03/56/2019", "10/XX/2016", "EUXX",
                    Arrays.asList(
                        createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_FROM_FIELD)),
                        createError(INVALID_ITINERARY, String.format(MESSAGE_INVALID_ITINERARY, FLY_TO_FIELD)),
                        createError(INVALID_DATE, String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_FROM_FIELD)),
                        createError(INVALID_DATE, String.format(MESSAGE_INVALID_DATE_FORMAT, DATE_TO_FIELD)),
                        createError(INVALID_CURRENCY, String.format(MESSAGE_INVALID_CURRENCY, CURRENCY_FIELD))
                    )));
    }
}
