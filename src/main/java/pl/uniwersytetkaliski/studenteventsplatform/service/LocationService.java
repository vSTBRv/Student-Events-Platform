package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.mapper.LocationMapper;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public Location createLocation(LocationCreateDTO dto){
        return locationRepository.save(
                locationMapper.toEntity(dto)
        );
    }

    public Location getOrCreateLocation(LocationUpdateDTO dto){
        Location location = locationMapper.updateEntity(new Location(),dto);
        Optional<Location> existingLocation = getLocation(location);
        return existingLocation.orElseGet(() -> locationRepository.save(location));
    }

    public Optional<Location> getLocation(Location location) {
        return locationRepository.findByAll(location.getCity(),location.getStreet(),location.getHouseNumber(),location.getPostalCode());
    }
}
