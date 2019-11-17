package tech.marcusvieira.flightinportugal.services;

import java.util.List;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;

public interface RequestService {

    RequestEntity create(RequestEntity request);

    List<RequestEntity> getAllRequests();

    void deleteAllRequests();
}
