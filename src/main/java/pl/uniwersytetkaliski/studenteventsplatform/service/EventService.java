package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        dto.setStartDateTime(event.getStartDate());
        dto.setEndDateTime(event.getEndDate());
        dto.setComments(event.getComments());
        return dto;
    }

}
