package tech.marcusvieira.flightinportugal.unit.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.repositories.RequestRepository;
import tech.marcusvieira.flightinportugal.services.RequestService;
import tech.marcusvieira.flightinportugal.services.impl.RequestServiceImpl;

@SpringBootTest
public class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestService requestService = new RequestServiceImpl();

    @Test
    void shouldSaveRequest() {

        final RequestEntity requestEntity = RequestEntity.builder()
            .flyFrom(FlightItinerary.LIS.name())
            .flyTo(FlightItinerary.OPO.name())
            .dateFrom("28/03/2020")
            .dateTo("01/04/2020")
            .currency(Currency.CRC.name())
            .build();

        requestService.create(requestEntity);

        verify(requestRepository, times(1)).save(requestEntity);
    }

    @Test
    void shouldReturnAllRequests() {

        final List<RequestEntity> mockRequests = Arrays.asList(RequestEntity.builder()
                .flyFrom(FlightItinerary.LIS.name())
                .flyTo(FlightItinerary.OPO.name())
                .dateFrom("17/01/2019")
                .dateTo("24/02/2019")
                .currency(Currency.BRL.name())
                .build(),
            RequestEntity.builder()
                .flyFrom(FlightItinerary.OPO.name())
                .flyTo(FlightItinerary.LIS.name())
                .dateFrom("01/11/2020")
                .dateTo("15/12/2020")
                .currency(Currency.AED.name())
                .build());

        when(requestRepository.findAll()).thenReturn(mockRequests);

        final List<RequestEntity> allRequests = requestService.getAllRequests();

        assertArrayEquals(mockRequests.toArray(), allRequests.toArray());
    }

    @Test
    void shouldDeleteAllRequests() {

        requestService.deleteAllRequests();

        verify(requestRepository, times(1)).deleteAll();
    }
}
