package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.LocationDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.*;
import pl.uniwersytetkaliski.studenteventsplatform.repository.CategoryRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository, CategoryRepository categoryRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EventResponseDto getEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        return mapToDto(event);
    }

    public List<EventResponseDto> getEventByName(String name) {
        List<Event> events = eventRepository.findByNameContainingIgnoreCase(name);
        return events.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EventResponseDto mapToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setLocationCity(event.getLocation().getCity());
        dto.setLocationStreet(event.getLocation().getStreet());
        dto.setLocationHouseNumber(event.getLocation().getHouseNumber());
        dto.setLocationPostalCode(event.getLocation().getPostalCode());
        dto.setStatus(event.getStatus());
        dto.setStartDateTime(event.getStartDate());
        dto.setEndDateTime(event.getEndDate());
        dto.setComments(event.getDescription());
        dto.setStatusLabel(event.getStatus().getStatus());
        dto.setCapacity(event.getMaxCapacity());
        dto.setCategory(event.getCategory().getName());
        return dto;
    }

    public Event createEvent(EventResponseDto eventResponseDto) {

//        Location location = locationRepository
//                .findById(eventResponseDto.getLocationId())
//                .get();
//        Category category = categoryRepository
//                .findById(eventResponseDto.getCategoryId())
//                .get();
//
        Event event = new Event();
//        event.setName(eventResponseDto.getName());
//        event.setLocation(location);
//        event.setStatus(EventStatus.valueOf(eventResponseDto.getStatus().toUpperCase()));
//        event.setMaxCapacity(eventResponseDto.getMaxCapacity());
//        event.setStartDate(eventResponseDto.getStartDate());
//        event.setEndDate(eventResponseDto.getEndDate());
//        event.setDescription(eventResponseDto.getComments());
//        event.setCategory(category);

        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LocationDTO locationDTO = eventDTO.getLocationDTO();

        Location editedlocation;

        Event event = eventRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Event with id " + id + " not found"
                        )
                );

        Optional<User> user = userService.getUserByEmail(auth.getName());
        if (user.isEmpty()) {
            System.out.println("User with email " + auth.getName() + " not found");
            throw new EntityNotFoundException();
        }
        boolean isAdmin = (user.get()).getUserRole() == UserRole.ADMIN;
        boolean isOrganisation = user.get().getUserRole() == UserRole.ORGANIZATION;
        boolean isOwner = user.get().getId() == event.getCreatedBy();

        if(!(isAdmin || (isOrganisation && isOwner))) {
            throw new AccessDeniedException("Access Denied");
        }
        Optional<Location> location = locationRepository
                .findByAll(
                        locationDTO.getCity(),
                        locationDTO.getStreet(),
                        locationDTO.getHouseNumber(),
                        locationDTO.getPostalCode()
                );
        if (location.isEmpty()) {
            Location newLocation = new Location();
            newLocation.setCity(locationDTO.getCity());
            newLocation.setStreet(locationDTO.getStreet());
            newLocation.setHouseNumber(locationDTO.getHouseNumber());
            newLocation.setPostalCode(locationDTO.getPostalCode());
            locationRepository.save(newLocation);
            editedlocation = newLocation;
        } else {
            editedlocation = location.get();
        }

        Optional <Category> category = categoryRepository
                .findByName((eventDTO.getName()));
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Category with name " + eventDTO.getName() + " not found");
        }

        event.setName(eventDTO.getName());
        event.setLocation(editedlocation);
        event.setCategory(category.get());
        event.setStatus(eventDTO.getStatus());
        event.setDescription(eventDTO.getDescription());
        event.setMaxCapacity(eventDTO.getMaxCapacity());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setCreatedBy(user.get().getId());

        return eventRepository.save(event);
    }
}
