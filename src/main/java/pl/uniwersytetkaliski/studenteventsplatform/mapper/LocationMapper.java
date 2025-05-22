package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;

@Component
public class LocationMapper implements Mapper<Location, LocationResponseDTO, LocationCreateDTO, LocationUpdateDTO>{

    @Override
    public Location toEntity(LocationCreateDTO locationCreateDTO) {
        Location location = new Location();
        location.setCity(locationCreateDTO.getCity());
        location.setStreet(locationCreateDTO.getStreet());
        location.setHouseNumber(locationCreateDTO.getHouseNumber());
        location.setPostalCode(locationCreateDTO.getPostalCode());
        return location;
    }

    @Override
    public Location updateEntity(Location entity, LocationUpdateDTO locationUpdateDTO) {
        entity.setCity(locationUpdateDTO.getCity());
        entity.setStreet(locationUpdateDTO.getStreet());
        entity.setHouseNumber(locationUpdateDTO.getHouseNumber());
        entity.setPostalCode(locationUpdateDTO.getPostalCode());
        return entity;
    }

    @Override
    public LocationResponseDTO toResponseDTO(Location entity) {
        LocationResponseDTO locationResponseDTO = new LocationResponseDTO();
        locationResponseDTO.setId(entity.getId());
        locationResponseDTO.setCity(entity.getCity());
        locationResponseDTO.setStreet(entity.getStreet());
        locationResponseDTO.setHouseNumber(entity.getHouseNumber());
        locationResponseDTO.setPostalCode(entity.getPostalCode());
        return locationResponseDTO;
    }
}
