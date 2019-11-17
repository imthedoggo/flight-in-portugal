package tech.marcusvieira.flightinportugal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.marcusvieira.flightinportugal.dtos.request.Request;
import tech.marcusvieira.flightinportugal.entities.RequestEntity;

@Mapper
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    Request entityToResponse(RequestEntity requestEntity);
}
