package tech.marcusvieira.flightinportugal.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, Long> {

    List<RequestEntity> findAll();
}
