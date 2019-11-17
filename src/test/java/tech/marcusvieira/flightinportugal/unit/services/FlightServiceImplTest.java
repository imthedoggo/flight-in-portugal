package tech.marcusvieira.flightinportugal.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.AIRPORT_DATA_ACTIVE;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.AIRPORT_DATA_LIMIT;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.LOCATION_AIRPORT;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.ONLY_DIRECT_FLIGHTS_FLAG;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.PARTNER_CODE;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.flightinportugal.dtos.airport.AirportData;
import tech.marcusvieira.flightinportugal.dtos.airport.Location;
import tech.marcusvieira.flightinportugal.dtos.flight.Flight;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsAverageResponse;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsData;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.AirportNotFoundException;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.FlightNotFoundException;
import tech.marcusvieira.flightinportugal.proxies.AirportServiceProxy;
import tech.marcusvieira.flightinportugal.proxies.FlightServiceProxy;
import tech.marcusvieira.flightinportugal.services.FlightService;
import tech.marcusvieira.flightinportugal.services.RequestService;
import tech.marcusvieira.flightinportugal.services.impl.FlightServiceImpl;
import tech.marcusvieira.flightinportugal.utils.FlightUtils;

@SpringBootTest
public class FlightServiceImplTest {

    @Mock
    private FlightServiceProxy flightServiceProxy;

    @Mock
    private AirportServiceProxy airportServiceProxy;

    @Mock
    private RequestService requestService;

    @InjectMocks
    private FlightService flightService = new FlightServiceImpl();

    @BeforeEach
    void setMockOutput() {
        when(flightServiceProxy
            .getFlights(FlightUtils.createAirlinesParam(), FlightItinerary.LIS.name(), FlightItinerary.OPO.name(),
                "17/12/2019", "24/12/2019", PARTNER_CODE, ONLY_DIRECT_FLIGHTS_FLAG, Currency.BRL.name()))
            .thenReturn(
                FlightsData.builder()
                    .flights(Arrays.asList(
                        Flight.builder()
                            .price(new BigDecimal(111))
                            .bagsPrice(Map.of("1", new BigDecimal(39), "2", new BigDecimal(78)))
                            .conversion(Map.of("BRL", new BigDecimal(111), "EUR", new BigDecimal(23)))
                            .build(),
                        Flight.builder()
                            .price(new BigDecimal(356))
                            .bagsPrice(Map.of("1", new BigDecimal(52), "2", new BigDecimal(169)))
                            .conversion(Map.of("BRL", new BigDecimal(356), "EUR", new BigDecimal(76)))
                            .build(),
                        Flight.builder()
                            .price(new BigDecimal(217))
                            .bagsPrice(Map.of("1", new BigDecimal(58.5), "2", new BigDecimal(143)))
                            .conversion(Map.of("BRL", new BigDecimal(217), "EUR", new BigDecimal(46)))
                            .build()))
                    .build());

        when(airportServiceProxy
            .getAirports(FlightItinerary.LIS.name(), LOCATION_AIRPORT, AIRPORT_DATA_LIMIT, AIRPORT_DATA_ACTIVE))
            .thenReturn(AirportData.builder()
                .locations(Collections.singletonList(Location
                    .builder()
                    .name("Lisbon Portela")
                    .build()))
                .build());

        when(airportServiceProxy
            .getAirports(FlightItinerary.OPO.name(), LOCATION_AIRPORT, AIRPORT_DATA_LIMIT, AIRPORT_DATA_ACTIVE))
            .thenReturn(AirportData.builder()
                .locations(Collections.singletonList(Location
                    .builder()
                    .name("Porto")
                    .build()))
                .build());
    }

    @Test
    void shouldReturnFlightAveragePrices() {

        final FlightsAverageResponse flightsAveragePrice = flightService
            .getFlightsAveragePrice(FlightItinerary.LIS.name(), FlightItinerary.OPO.name(), "17/12/2019", "24/12/2019",
                Currency.BRL.name());

        final RequestEntity requestEntity = RequestEntity.builder()
            .flyFrom(FlightItinerary.LIS.name())
            .flyTo(FlightItinerary.OPO.name())
            .dateFrom("17/12/2019")
            .dateTo("24/12/2019")
            .currency(Currency.BRL.name())
            .build();

        verify(requestService, times(1)).create(requestEntity);

        assertEquals(new BigDecimal("228.00"), flightsAveragePrice.getAvgPrice());
        assertEquals(new BigDecimal("49.84"), flightsAveragePrice.getBagsAvgPrice().getFirst());
        assertEquals(new BigDecimal("130.00"), flightsAveragePrice.getBagsAvgPrice().getSecond());
        assertEquals(new BigDecimal("0.21"), flightsAveragePrice.getCurrencyByEUR());
        assertEquals("Lisbon Portela", flightsAveragePrice.getAirportNameFrom());
        assertEquals("Porto", flightsAveragePrice.getAirportNameTo());
    }

    @Test
    void shouldThrowFlightNotFoundException() {

        assertThrows(
            FlightNotFoundException.class, () ->
                flightService.getFlightsAveragePrice("SXF", FlightItinerary.OPO.name(), "17/12/2019", "24/12/2019",
                    Currency.GBP.name()));
    }

    @Test
    void shouldThrowAirportNotFoundException() {

        when(airportServiceProxy
            .getAirports(FlightItinerary.OPO.name(), LOCATION_AIRPORT, AIRPORT_DATA_LIMIT, AIRPORT_DATA_ACTIVE))
            .thenReturn(AirportData.builder().build());

        assertThrows(
            AirportNotFoundException.class, () ->
                flightService
                    .getFlightsAveragePrice(FlightItinerary.LIS.name(), FlightItinerary.OPO.name(), "17/12/2019",
                        "24/12/2019",
                        Currency.BRL.name()));
    }
}
