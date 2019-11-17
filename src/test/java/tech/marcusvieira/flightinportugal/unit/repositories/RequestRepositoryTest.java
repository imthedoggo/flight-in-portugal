package tech.marcusvieira.flightinportugal.unit.repositories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.repositories.RequestRepository;
import tech.marcusvieira.flightinportugal.utils.DateUtils;

@SpringBootTest
public class RequestRepositoryTest {

    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    public void cleanData() {
        requestRepository.deleteAll();
    }

    @Test
    void shouldSaveRequest() {
        final RequestEntity requestEntity = createRequestEntity(FlightItinerary.LIS, FlightItinerary.OPO,
            LocalDate.now(), LocalDate.now(), Currency.ANG);

        final RequestEntity savedEntity = requestRepository.save(requestEntity);

        assertSavedEntity(requestEntity, savedEntity);
    }

    @Test
    void shouldFindAllRequests() {
        LocalDate today = LocalDate.now();

        final RequestEntity requestEntity1 = createRequestEntity(FlightItinerary.LIS, FlightItinerary.OPO, today,
            today.plus(Period.ofDays(3)), Currency.ANG);

        final RequestEntity requestEntity2 = createRequestEntity(FlightItinerary.OPO, FlightItinerary.LIS,
            today.plus(Period.ofDays(3)), today.plus(Period.ofMonths(1)), Currency.EUR);

        requestRepository.save(requestEntity1);
        requestRepository.save(requestEntity2);

        final List<RequestEntity> requests = requestRepository.findAll();

        assertSavedEntity(requestEntity1, requests.get(0));
        assertSavedEntity(requestEntity2, requests.get(1));
    }

    @Test
    void shouldDeleteAllRequests() {
        LocalDate today = LocalDate.now();

        final RequestEntity requestEntity1 = createRequestEntity(FlightItinerary.LIS, FlightItinerary.OPO, today,
            today.plus(Period.ofDays(3)), Currency.ANG);

        final RequestEntity requestEntity2 = createRequestEntity(FlightItinerary.OPO, FlightItinerary.LIS,
            today.plus(Period.ofDays(3)), today.plus(Period.ofMonths(1)), Currency.EUR);

        requestRepository.save(requestEntity1);
        requestRepository.save(requestEntity2);

        requestRepository.deleteAll();

        final List<RequestEntity> requests = requestRepository.findAll();

        assertArrayEquals(new RequestEntity[]{}, requests.toArray());
    }

    private RequestEntity createRequestEntity(FlightItinerary flyFrom, FlightItinerary flyTo, LocalDate dateFrom,
        LocalDate dateTo, Currency currency) {
        return RequestEntity.builder()
            .flyFrom(flyFrom.name())
            .flyTo(flyTo.name())
            .dateFrom(DateUtils.localDateToString(dateFrom))
            .dateTo(DateUtils.localDateToString(dateTo))
            .currency(currency.name())
            .build();
    }

    private void assertSavedEntity(RequestEntity requestEntity, RequestEntity savedEntity) {
        assertNotNull(savedEntity.getId());
        assertEquals(requestEntity.getFlyFrom(), savedEntity.getFlyFrom());
        assertEquals(requestEntity.getFlyTo(), savedEntity.getFlyTo());
        assertEquals(requestEntity.getDateTo(), savedEntity.getDateTo());
        assertEquals(requestEntity.getDateFrom(), savedEntity.getDateFrom());
        assertEquals(requestEntity.getCurrency(), savedEntity.getCurrency());
    }
}
