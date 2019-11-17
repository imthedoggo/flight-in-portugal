package tech.marcusvieira.flightinportugal.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.marcusvieira.flightinportugal.utils.DateUtils.localDateToString;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllRequests() throws Exception {

        LocalDate date = LocalDate.now();

        performFlightAveragePriceRequest(FlightItinerary.OPO, FlightItinerary.LIS, localDateToString(date),
            localDateToString(date.plus(Period.ofWeeks(3))), Currency.BRL);
        performFlightAveragePriceRequest(FlightItinerary.LIS, FlightItinerary.OPO, localDateToString(date),
            localDateToString(date.plus(Period.ofMonths(1))), Currency.EUR);

        mockMvc.perform(get("/v1/requests/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            //request 1
            .andExpect(jsonPath("$.data.[0].fly_from").value(FlightItinerary.OPO.name()))
            .andExpect(jsonPath("$.data.[0].fly_to").value(FlightItinerary.LIS.name()))
            .andExpect(jsonPath("$.data.[0].date_from").value(localDateToString(date)))
            .andExpect(jsonPath("$.data.[0].date_to").value(localDateToString(date.plus(Period.ofWeeks(3)))))
            .andExpect(jsonPath("$.data.[0].currency").value(Currency.BRL.name()))
            //request 2
            .andExpect(jsonPath("$.data.[1].fly_from").value(FlightItinerary.LIS.name()))
            .andExpect(jsonPath("$.data.[1].fly_to").value(FlightItinerary.OPO.name()))
            .andExpect(jsonPath("$.data.[1].date_from").value(localDateToString(date)))
            .andExpect(jsonPath("$.data.[1].date_to").value(localDateToString(date.plus(Period.ofMonths(1)))))
            .andExpect(jsonPath("$.data.[1].currency").value(Currency.EUR.name()));
    }

    @Test
    void shouldDeleteAllRequests() throws Exception {

        LocalDate date = LocalDate.now();

        performFlightAveragePriceRequest(FlightItinerary.LIS, FlightItinerary.OPO, localDateToString(date),
            localDateToString(date.plus(Period.ofWeeks(1))), Currency.USD);
        performFlightAveragePriceRequest(FlightItinerary.OPO, FlightItinerary.LIS, localDateToString(date),
            localDateToString(date.plus(Period.ofWeeks(2))), Currency.BRL);

        mockMvc.perform(delete("/v1/requests/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/v1/requests/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isEmpty());
    }

    private void performFlightAveragePriceRequest(FlightItinerary flyFrom, FlightItinerary flyTo, String dateFrom,
        String dateTo, Currency currency) throws Exception {

        mockMvc.perform(get("/v1/flights/avg-price")
            .param("fly_from", flyFrom.name())
            .param("fly_to", flyTo.name())
            .param("date_from", dateFrom)
            .param("date_to", dateTo)
            .param("currency", currency.name())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
