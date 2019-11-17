package tech.marcusvieira.flightinportugal.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.marcusvieira.flightinportugal.enums.AirlineCompany.RYANAIR;
import static tech.marcusvieira.flightinportugal.enums.AirlineCompany.TAP;

import org.junit.jupiter.api.Test;
import tech.marcusvieira.flightinportugal.utils.FlightUtils;

public class FlightUtilsTest {

    @Test
    void shouldReturnAirlineCompanies() {

        final String airlinesParam = FlightUtils.createAirlinesParam();

        assertEquals(String.join(",", TAP.getIataCode(), RYANAIR.getIataCode()), airlinesParam);
    }
}
