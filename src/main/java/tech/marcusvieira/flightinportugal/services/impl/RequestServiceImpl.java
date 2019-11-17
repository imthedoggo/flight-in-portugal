package tech.marcusvieira.flightinportugal.services.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;
import tech.marcusvieira.flightinportugal.repositories.RequestRepository;
import tech.marcusvieira.flightinportugal.services.RequestService;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestsRepository;

    @Override
    public RequestEntity create(RequestEntity request) {
        return requestsRepository.save(request);
    }

    @Override
    public List<RequestEntity> getAllRequests() {
        return requestsRepository.findAll();
    }

    @Override
    public void deleteAllRequests() {
        requestsRepository.deleteAll();
    }
}
