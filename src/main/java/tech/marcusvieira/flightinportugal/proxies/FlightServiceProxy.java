package tech.marcusvieira.flightinportugal.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.marcusvieira.flightinportugal.dtos.flight.FlightsData;

@FeignClient(name = "skypicker-flight-api", url = "${external.flight.api}")
public interface FlightServiceProxy {

    @GetMapping("/flights")
    FlightsData getFlights(
        @RequestParam("select_airlines") String airlineCompanies,
        @RequestParam("fly_from") String flyFrom,
        @RequestParam("fly_to") String flyTo,
        @RequestParam(value = "date_from", required = false) String dateFrom,
        @RequestParam(value = "date_to", required = false) String dateTo,
        @RequestParam("partner") String partner,
        @RequestParam("direct_flights") Integer directFlyFlag,
        @RequestParam("curr") String currency);
}
