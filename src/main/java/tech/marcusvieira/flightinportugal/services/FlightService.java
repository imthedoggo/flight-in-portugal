package tech.marcusvieira.flightinportugal.services;

import tech.marcusvieira.flightinportugal.dtos.flight.FlightsAverageResponse;

public interface FlightService {

    FlightsAverageResponse getFlightsAveragePrice(String flyFrom, String flyTo, String dateFrom, String dateTo,
        String currency);
}
