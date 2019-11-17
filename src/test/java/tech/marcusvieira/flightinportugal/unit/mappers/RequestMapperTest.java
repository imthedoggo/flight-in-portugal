package tech.marcusvieira.flightinportugal.unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.marcusvieira.flightinportugal.utils.DateUtils.localDateToString;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import tech.marcusvieira.flightinportugal.dtos.request.Request;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.mappers.RequestMapper;

public class RequestMapperTest {

    @Test
    void shouldConvertEntityToResponse() {
        final RequestEntity requestEntity = RequestEntity.builder()
            .flyFrom(FlightItinerary.LIS.name())
            .flyTo(FlightItinerary.OPO.name())
            .dateFrom(localDateToString(LocalDate.now()))
            .dateTo(localDateToString(LocalDate.now()))
            .currency(Currency.ANG.name())
            .build();

        final Request requestResponse = RequestMapper.INSTANCE.entityToResponse(requestEntity);

        assertEquals(requestEntity.getFlyFrom(), requestResponse.getFlyFrom());
        assertEquals(requestEntity.getFlyTo(), requestResponse.getFlyTo());
        assertEquals(requestEntity.getDateTo(), requestResponse.getDateTo());
        assertEquals(requestEntity.getDateFrom(), requestResponse.getDateFrom());
        assertEquals(requestEntity.getCurrency(), requestResponse.getCurrency());
    }
}
