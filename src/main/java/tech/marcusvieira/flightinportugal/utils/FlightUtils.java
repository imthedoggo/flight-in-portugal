package tech.marcusvieira.flightinportugal.utils;

import static tech.marcusvieira.flightinportugal.enums.AirlineCompany.RYANAIR;
import static tech.marcusvieira.flightinportugal.enums.AirlineCompany.TAP;

public final class FlightUtils {

    /**
     * Creates the airlines parameter used to call external APIs
     */
    public static String createAirlinesParam() {
        return String.join(",", TAP.getIataCode(), RYANAIR.getIataCode());
    }
}
