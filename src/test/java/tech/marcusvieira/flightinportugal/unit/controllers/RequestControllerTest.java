package tech.marcusvieira.flightinportugal.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.flightinportugal.controllers.RequestController;
import tech.marcusvieira.flightinportugal.dtos.request.Request;
import tech.marcusvieira.flightinportugal.dtos.request.RequestResponse;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.enums.Currency;
import tech.marcusvieira.flightinportugal.enums.FlightItinerary;
import tech.marcusvieira.flightinportugal.mappers.RequestMapper;
import tech.marcusvieira.flightinportugal.services.RequestService;

@SpringBootTest
public class RequestControllerTest {

    @Mock
    private RequestService requestService;

    @InjectMocks
    private RequestController requestController;

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

        when(requestService.getAllRequests()).thenReturn(mockRequests);

        final List<Request> expectedResquests = mockRequests.stream()
            .map(req -> RequestMapper.INSTANCE.entityToResponse(req))
            .collect(Collectors.toList());

        final RequestResponse requests = requestController.getAllRequests();

        assertArrayEquals(expectedResquests.toArray(), requests.getData().toArray());
    }

    @Test
    void shouldNotReturnAllRequests() {

        final RequestResponse requests = requestController.getAllRequests();

        assertArrayEquals(new Request[]{}, requests.getData().toArray());
    }

    @Test
    void shouldDeleteAllRequests() {

        requestController.deleteAllRequests();

        verify(requestService, times(1)).deleteAllRequests();
    }
}
