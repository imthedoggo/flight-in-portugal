package tech.marcusvieira.flightinportugal.controllers;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.marcusvieira.flightinportugal.dtos.request.RequestResponse;
import tech.marcusvieira.flightinportugal.mappers.RequestMapper;
import tech.marcusvieira.flightinportugal.services.RequestService;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping(value = "/v1/requests", produces = "application/json")
    public RequestResponse getAllRequests() {
        return RequestResponse.builder()
            .data(Optional.ofNullable(requestService.getAllRequests())
                .map(requests -> requests.stream()
                    .map(req -> RequestMapper.INSTANCE.entityToResponse(req))
                    .collect(Collectors.toList()))
                .orElse(Collections.emptyList()))
            .build();
    }

    @DeleteMapping(value = "/v1/requests", produces = "application/json")
    public void deleteAllRequests() {
        requestService.deleteAllRequests();
    }
}
