package tech.marcusvieira.flightinportugal.constants;

import tech.marcusvieira.flightinportugal.dtos.flight.Flight;

public class FlightConstants {

    /**
     * Represents the amount of airport locations to be retrieved.
     */
    public static int AIRPORT_DATA_LIMIT = 1;

    /**
     * Indicates active airport locations in the request.
     */
    public static boolean AIRPORT_DATA_ACTIVE = true;

    /**
     * Represents the partner code for testing.
     */
    public static String PARTNER_CODE = "picky";

    /**
     * Type of location airport, used to request data.
     */
    public static String LOCATION_AIRPORT = "airport";

    /**
     * Represents a only direct flight flag.
     */
    public static Integer ONLY_DIRECT_FLIGHTS_FLAG = 1;

    /**
     * Represents the first bag into {@link Flight#getBagsPrice()}.
     */
    public static String FIRST_BAG = "1";

    /**
     * Represents the second bag into {@link Flight#getBagsPrice()}.
     */
    public static String SECOND_BAG = "2";

    /**
     * Represents the currency EUR.
     */
    public static String CURRENCY_EUR = "EUR";

    /**
     * Represents the date from field.
     */
    public static String DATE_FROM_FIELD = "date_from";

    /**
     * Represents the date to field.
     */
    public static String DATE_TO_FIELD = "date_to";

    /**
     * Represents the fly from field.
     */
    public static String FLY_FROM_FIELD = "fly_from";

    /**
     * Represents the fly to field.
     */
    public static String FLY_TO_FIELD = "fly_to";

    /**
     * Represents the currency field.
     */
    public static String CURRENCY_FIELD = "currency";
}
