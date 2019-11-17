package tech.marcusvieira.flightinportugal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsAverageResponse;
import tech.marcusvieira.flightinportugal.services.FlightService;
import tech.marcusvieira.flightinportugal.validators.FlightValidator;

@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Cacheable(value = "flight-avg-price")
    @GetMapping(value = "/v1/flights/avg-price", produces = "application/json")
    public FlightsAverageResponse getFlightsAveragePrice(
        @RequestParam("fly_from") String flyFrom,
        @RequestParam("fly_to") String flyTo,
        @RequestParam(value = "date_from", required = false) String dateFrom,
        @RequestParam(value = "date_to", required = false) String dateTo,
        @RequestParam("currency") String currency) {

        FlightValidator.validate(flyFrom, flyTo, dateFrom, dateTo, currency);

        return flightService.getFlightsAveragePrice(flyFrom, flyTo, dateFrom, dateTo, currency);
    }
}
