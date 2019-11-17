package tech.marcusvieira.flightinportugal.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.marcusvieira.flightinportugal.dtos.airport.AirportData;

@FeignClient(name = "skypicker-airport-api", url = "${external.flight.api}")
public interface AirportServiceProxy {

    @GetMapping("/locations")
    AirportData getAirports(
        @RequestParam("term") String iataCode,
        @RequestParam("location_types") String locationType,
        @RequestParam("limit") Integer limit,
        @RequestParam("active_only") boolean activeOnly);
}
