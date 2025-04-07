package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventCreateDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventRequestDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
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
        dto.setComments(event.getComments());
        return dto;
    }

    public Event createEvent(EventCreateDto eventCreateDto) {

        Location location = locationRepository
                .findById(eventCreateDto.getLocationId())
                .get();

        Event event = new Event();
        event.setName(eventCreateDto.getName());
        event.setLocation(location);
        event.setStatus(EventStatus.valueOf(eventCreateDto.getStatus().toUpperCase()));
        event.setMaxCapacity(eventCreateDto.getMaxCapacity());
        event.setStartDate(eventCreateDto.getStartDate());
        event.setEndDate(eventCreateDto.getEndDate());
        event.setComments(eventCreateDto.getComments());

        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventCreateDto eventCreateDto) {

        Event event = eventRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Event with id " + id + " not found"
                        )
                );

        Location location = locationRepository
                .findById(eventCreateDto.getLocationId())
                .orElseThrow(
                        ()-> new EntityNotFoundException(
                                "Location with id " + eventCreateDto.getLocationId() + " not found"
                        )
                );

        event.setName(eventCreateDto.getName());
        event.setLocation(location);
        System.out.println("DTO status = " + eventCreateDto.getStatus());
        event.setStatus(EventStatus.valueOf(eventCreateDto.getStatus().toUpperCase()));
        event.setMaxCapacity(eventCreateDto.getMaxCapacity());
        event.setStartDate(eventCreateDto.getStartDate());
        event.setEndDate(eventCreateDto.getEndDate());
        event.setComments(eventCreateDto.getComments());

//        System.out.println("Setting status: " + eventCreateDto.getStatus());
//        System.out.println("Enum: " + EventStatus.valueOf(eventCreateDto.getStatus().toUpperCase()));
        return eventRepository.save(event);
    }
}
