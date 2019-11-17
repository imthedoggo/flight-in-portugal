package tech.marcusvieira.flightinportugal.services.impl;

import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_AIRPORT_NOT_FOUND;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_FLIGHT_NOT_FOUND;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_PRICE_NOT_FOUND;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.AIRPORT_DATA_ACTIVE;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.AIRPORT_DATA_LIMIT;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.CURRENCY_EUR;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.FIRST_BAG;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.LOCATION_AIRPORT;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.ONLY_DIRECT_FLIGHTS_FLAG;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.PARTNER_CODE;
import static tech.marcusvieira.flightinportugal.constants.FlightConstants.SECOND_BAG;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.marcusvieira.flightinportugal.dtos.airport.AirportData;
import tech.marcusvieira.flightinportugal.dtos.flight.BagResponse;
import tech.marcusvieira.flightinportugal.dtos.flight.Flight;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsAverageResponse;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsData;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.AirportNotFoundException;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.FlightNotFoundException;
import tech.marcusvieira.flightinportugal.proxies.AirportServiceProxy;
import tech.marcusvieira.flightinportugal.proxies.FlightServiceProxy;
import tech.marcusvieira.flightinportugal.services.FlightService;
import tech.marcusvieira.flightinportugal.services.RequestService;
import tech.marcusvieira.flightinportugal.utils.FlightUtils;
import tech.marcusvieira.flightinportugal.utils.MathUtils;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightServiceProxy flightServiceProxy;

    @Autowired
    private AirportServiceProxy airportServiceProxy;

    @Autowired
    private RequestService requestService;

    /**
     * Returns flights average price based on {@code flyFrom}, {@code flyTo}, {@code dateFrom},{@code dateTo} and {@code
     * currency}
     *
     * @param flyFrom  - IATA code of the origin airport
     * @param flyTo    - IATA code of the destination airport
     * @param dateFrom - search flights from this date (dd/mm/YYYY).
     * @param dateTo   - search flights up to this date (dd/mm/YYYY).
     * @param currency - currency used to get price
     * @return {@link FlightsAverageResponse} flights average prices
     */
    @Override
    public FlightsAverageResponse getFlightsAveragePrice(String flyFrom, String flyTo, String dateFrom, String dateTo,
        String currency) {

        //save flight request for auditing proposes
        saveFlightRequest(flyFrom, flyTo, dateFrom, dateTo, currency);

        final FlightsData flightsData = getFlightsData(flyFrom, flyTo, dateFrom, dateTo, currency);

        return calculateAveragePrice(flyFrom, flyTo, currency, flightsData);
    }

    private FlightsData getFlightsData(String flyFrom, String flyTo, String dateFrom, String dateTo, String currency) {

        final FlightsData flightData = flightServiceProxy
            .getFlights(FlightUtils.createAirlinesParam(), flyFrom, flyTo, dateFrom, dateTo, PARTNER_CODE,
                ONLY_DIRECT_FLIGHTS_FLAG, currency);

        if (flightData == null || flightData.getFlights() == null || flightData.getFlights().size() == 0) {
            throw new FlightNotFoundException(MESSAGE_FLIGHT_NOT_FOUND);
        }

        final boolean hasInvalidPrice = flightData.getFlights().stream()
            .anyMatch(f -> f.getPrice() == null || f.getPrice().compareTo(BigDecimal.ZERO) == 0);
        if (hasInvalidPrice) {
            throw new FlightNotFoundException(String.format(MESSAGE_PRICE_NOT_FOUND, currency));
        }

        return flightData;
    }

    private FlightsAverageResponse calculateAveragePrice(String flyFrom, String flyTo, String currency,
        FlightsData flightsData) {

        BigDecimal avgPrice = BigDecimal.ZERO;
        BigDecimal avgBagFirst = BigDecimal.ZERO;
        BigDecimal avgBagSecond = BigDecimal.ZERO;

        for (Flight flight : flightsData.getFlights()) {
            avgPrice = avgPrice.add(flight.getPrice());
            if (flight.getBagsPrice() != null) {
                avgBagFirst = avgBagFirst.add(getBagPrice(flight.getBagsPrice(), FIRST_BAG));
                avgBagSecond = avgBagSecond.add(getBagPrice(flight.getBagsPrice(), SECOND_BAG));
            }
        }
        return createFlightResponse(flyFrom, flyTo, currency, flightsData, avgPrice, avgBagFirst, avgBagSecond);
    }

    private FlightsAverageResponse createFlightResponse(String flyFrom, String flyTo, String currency,
        FlightsData flightsData, BigDecimal avgPrice, BigDecimal avgBagFirst, BigDecimal avgBagSecond) {

        final BigDecimal amountOfFlights = new BigDecimal(flightsData.getFlights().size());

        return FlightsAverageResponse.builder()
            .avgPrice(MathUtils.divide(avgPrice, amountOfFlights))
            .currencyByEUR(getConversionByEUR(flightsData, currency))
            .bagsAvgPrice(BagResponse.builder()
                .first(MathUtils.divide(avgBagFirst, amountOfFlights))
                .second(MathUtils.divide(avgBagSecond, amountOfFlights))
                .build())
            .airportNameFrom(getAirportName(flyFrom))
            .airportNameTo(getAirportName(flyTo))
            .build();
    }

    private BigDecimal getConversionByEUR(FlightsData flightsData, String currency) {

        final Optional<BigDecimal> priceInEUR = getConversionByCurrency(flightsData, CURRENCY_EUR);
        final Optional<BigDecimal> priceRequestedCurrency = getConversionByCurrency(flightsData, currency);

        if (priceInEUR.isEmpty() || priceRequestedCurrency.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return MathUtils.divide(priceInEUR.get(), priceRequestedCurrency.get());
    }

    private BigDecimal getBagPrice(Map<String, BigDecimal> bagsPrice, String bagKey) {
        return Optional.ofNullable(bagsPrice.get(bagKey))
            .orElse(BigDecimal.ZERO);
    }

    private String getAirportName(String flyItinerary) {

        final AirportData airportData = airportServiceProxy
            .getAirports(flyItinerary, LOCATION_AIRPORT, AIRPORT_DATA_LIMIT, AIRPORT_DATA_ACTIVE);

        if (airportData == null || airportData.getLocations() == null || airportData.getLocations().size() == 0) {
            throw new AirportNotFoundException(MESSAGE_AIRPORT_NOT_FOUND);
        }
        return airportData.getLocations().get(0).getName();
    }

    private Optional<BigDecimal> getConversionByCurrency(FlightsData flightsData, String currency) {
        return Optional.ofNullable(flightsData.getFlights())
            .map(data -> data.get(0).getConversion().get(currency));
    }

    private void saveFlightRequest(String flyFrom, String flyTo, String dateFrom, String dateTo, String currency) {
        requestService.create(RequestEntity.builder()
            .flyFrom(flyFrom)
            .flyTo(flyTo)
            .dateFrom(dateFrom)
            .dateTo(dateTo)
            .currency(currency)
            .build());
    }
}
