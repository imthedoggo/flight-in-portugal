package tech.marcusvieira.flightinportugal.constants;

public class ErrorConstants {

    /**
     * Represents invalid currency message.
     */
    public static String MESSAGE_INVALID_CURRENCY = "%s must be a valid type.";

    /**
     * Represents invalid itinerary message.
     */
    public static String MESSAGE_INVALID_ITINERARY = "%s must be a valid itinerary type.";

    /**
     * Represents invalid equal from to itinerary message.
     */
    public static String MESSAGE_EQUAL_FROM_TO_ITINERARY = "%s and %s must be different values.";

    /**
     * Represents invalid date from higher than date to.
     */
    public static String MESSAGE_DATE_FROM_HIGHER_THAN_DATE_TO = "%s is higher than %s.";

    /**
     * Represents invalid date format message.
     */
    public static String MESSAGE_INVALID_DATE_FORMAT = "%s must be a date format DD/MM/YYYY.";

    /**
     * Represents invalid past date message.
     */
    public static String MESSAGE_INVALID_PAST_DATE = "%s must be equal or higher than today.";

    /**
     * Represents invalid future date message.
     */
    public static String MESSAGE_INVALID_FUTURE_DATE = "%s must not be higher than 3 years in future.";

    /**
     * Represents not found airport message.
     */
    public static String MESSAGE_AIRPORT_NOT_FOUND = "Airport information not found.";

    /**
     * Represents not found flight message.
     */
    public static String MESSAGE_FLIGHT_NOT_FOUND = "Flight information not found.";

    /**
     * Represents not found price currency message.
     */
    public static String MESSAGE_PRICE_NOT_FOUND = "Price not found for provided currency '%s'.";

    /**
     * Represents unknown error message.
     */
    public static String MESSAGE_UNKNOWN_ERROR = "The server encountered an internal error.";

    /**
     * Represents request parameter required message.
     */
    public static String MESSAGE_REQUEST_PARAMETER_REQUIRED = "%s is a required parameter.";

    /**
     * Represents third-party provider unavailable error message.
     */
    public static String MESSAGE_THIRD_PARTY_PROVIDER_ERROR = "Third-party provider services unavailable.";
}
