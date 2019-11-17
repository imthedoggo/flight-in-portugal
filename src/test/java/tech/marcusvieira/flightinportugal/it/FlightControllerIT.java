package tech.marcusvieira.flightinportugal.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Period;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.ErrorData;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.utils.DateUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFlightAveragePricesWithAllFields() throws Exception {

        LocalDate date = LocalDate.now();

        mockMvc.perform(get("/v1/flights/avg-price")
            .param("fly_from", FlightItinerary.LIS.name())
            .param("fly_to", FlightItinerary.OPO.name())
            .param("date_from", DateUtils.localDateToString(date))
            .param("date_to", DateUtils.localDateToString(date.plus(Period.ofWeeks(3))))
            .param("currency", Currency.USD.name())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.avg_price").isNotEmpty())
            .andExpect(jsonPath("$.bags_avg_price.first").isNotEmpty())
            .andExpect(jsonPath("$.bags_avg_price.second").isNotEmpty())
            .andExpect(jsonPath("$.currency_by_eur").isNotEmpty())
            .andExpect(jsonPath("$.airport_name_from").value("Lisbon Portela"))
            .andExpect(jsonPath("$.airport_name_to").value("Porto"));
    }

    @Test
    void shouldReturnFlightAveragePricesWithOnlyRequiredFields() throws Exception {

        LocalDate date = LocalDate.now();

        mockMvc.perform(get("/v1/flights/avg-price")
            .param("fly_from", FlightItinerary.LIS.name())
            .param("fly_to", FlightItinerary.OPO.name())
            .param("currency", Currency.USD.name())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.avg_price").isNotEmpty())
            .andExpect(jsonPath("$.bags_avg_price.first").isNotEmpty())
            .andExpect(jsonPath("$.bags_avg_price.second").isNotEmpty())
            .andExpect(jsonPath("$.currency_by_eur").isNotEmpty())
            .andExpect(jsonPath("$.airport_name_from").value("Lisbon Portela"))
            .andExpect(jsonPath("$.airport_name_to").value("Porto"));
    }

    @Test
    void shouldNotReturnFlightAveragePricesForInvalidParams() throws Exception {

        mockMvc.perform(get("/v1/flights/avg-price")
            .param("fly_from", "SPO")
            .param("fly_to", "GHT")
            .param("date_from", "0/1/2")
            .param("date_to", "//t")
            .param("currency", "Dollar")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors.[0].code").value(ErrorData.INVALID_ITINERARY.getCode()))
            .andExpect(jsonPath("$.errors.[1].code").value(ErrorData.INVALID_ITINERARY.getCode()))
            .andExpect(jsonPath("$.errors.[2].code").value(ErrorData.INVALID_DATE.getCode()))
            .andExpect(jsonPath("$.errors.[3].code").value(ErrorData.INVALID_DATE.getCode()))
            .andExpect(jsonPath("$.errors.[4].code").value(ErrorData.INVALID_CURRENCY.getCode()));
    }

    @Test
    void shouldNotReturnFlightAveragePricesForRequiredParams() throws Exception {

        mockMvc.perform(get("/v1/flights/avg-price")
            .param("fly_to", FlightItinerary.OPO.name())
            .param("currency", Currency.USD.name())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors.[0].code").value(ErrorData.REQUEST_PARAMETER_REQUIRED.getCode()));
    }
}
