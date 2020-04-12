package tech.marcusvieira.flightinportugal.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.flightinportugal.controllers.FlightController;
import tech.marcusvieira.flightinportugal.dtos.flight.BagResponse;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsAverageResponse;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.services.FlightService;

@SpringBootTest
public class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    //@Test void shouldReturnFlightAveragePrices() { when(flightService.getFlightsAveragePrice(FlightItinerary.LIS.name(), FlightItinerary.OPO.name(), "17/12/2019", "24/12/2019", Currency.BRL.name())).thenReturn(FlightsAverageResponse.builder().avgPrice(new BigDecimal("276.54")).bagsAvgPrice(BagResponse.builder().first(new BigDecimal("71.23")).second(new BigDecimal("112.98")).build()).currencyByEUR(new BigDecimal("0.56")).airportNameFrom("Porto").airportNameTo("Lisbon Portela").build());final FlightsAverageResponse flightsAveragePrice = flightController.getFlightsAveragePrice(FlightItinerary.LIS.name(), FlightItinerary.OPO.name(), "17/12/2019", "24/12/2019", Currency.BRL.name());assertEquals(new BigDecimal("276.54"), flightsAveragePrice.getAvgPrice());assertEquals(new BigDecimal("71.23"), flightsAveragePrice.getBagsAvgPrice().getFirst());assertEquals(new BigDecimal("112.98"), flightsAveragePrice.getBagsAvgPrice().getSecond());assertEquals(new BigDecimal("0.56"), flightsAveragePrice.getCurrencyByEUR());assertEquals("Porto", flightsAveragePrice.getAirportNameFrom());assertEquals("Lisbon Portela", flightsAveragePrice.getAirportNameTo()); }@Test void shouldNotReturnFlightAveragePrices() { final FlightsAverageResponse flightsAveragePrice = flightController.getFlightsAveragePrice(FlightItinerary.LIS.name(), FlightItinerary.OPO.name(), "17/12/2019", "24/12/2019", Currency.GBP.name());assertNull(flightsAveragePrice); }
}
