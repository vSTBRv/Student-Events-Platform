package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.mapper.EventMapper;
import pl.uniwersytetkaliski.studenteventsplatform.mapper.LocationMapper;
import pl.uniwersytetkaliski.studenteventsplatform.model.*;
import pl.uniwersytetkaliski.studenteventsplatform.repository.CategoryRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;

    public EventService(EventRepository eventRepository, LocationService locationService, CategoryRepository categoryRepository, UserService userService, EventMapper eventMapper, LocationMapper locationMapper) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
        this.locationMapper = locationMapper;
    }

    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findByDeletedFalse();
        return events.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO getEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        return eventMapper.toResponseDTO(event);
    }

    public List<EventResponseDTO> getEventByName(String name) {
        List<Event> events = eventRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name);
        return events.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO createEvent(EventCreateDTO dto) {
        if (dto.getStartDateTime().isAfter(dto.getEndDateTime())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        User user = getLoggedUser();
        Event event = eventMapper.toEntity(dto);
        event.setCreatedBy(user);
        event.setAccepted(false);
        event.setDeleted(false);
        event.setCurrentCapacity(0);
        event.setStatus(EventStatus.PLANNED);

        Location location = locationService.getLocation(
                locationMapper.toEntity(dto.getLocation())
        ).orElseGet(()-> locationService.createLocation(dto.getLocation()));
        event.setLocation(location);

        event.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new EntityNotFoundException("Category not found")));
        return eventMapper.toResponseDTO(eventRepository.save(event));
    }

    public EventResponseDTO updateEvent(Long id, EventUpdateDTO dto) throws AccessDeniedException {
        if (dto.getStartDateTime().isAfter(dto.getEndDateTime())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        User user = getLoggedUser();
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        if(isNotAdminOrCreator(user, event)) {
            throw new AccessDeniedException("You do not have permission to update this event");
        }

        if(dto.getLocation() != null) {
            event.setLocation(locationService.getOrCreateLocation(dto.getLocation()));
        }

        event.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found")));
        return eventMapper.toResponseDTO(eventRepository.save(event));
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public void softDeleteEvent(Long eventId) {
        User user = getLoggedUser();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));
        if(isNotAdminOrCreator(user, event)) {
            throw new AccessDeniedException("Access Denied");
        }
        eventRepository.softDelete(eventId);
    }

    public List<EventResponseDTO> getFilteredEvents(String categoryId, EventStatus status, LocalDate startDateFrom, LocalDate startDateTo){
        List<Event> filtered = eventRepository.findFilteredEvents(
                categoryId,
                status
//                startDateFrom,
//                startDateTo
        );
        return filtered.stream()
                .filter(e -> startDateFrom == null || !e.getStartDate().toLocalDate().isBefore(startDateFrom))
                .filter(e -> startDateTo == null || !e.getStartDate().toLocalDate().isAfter(startDateTo))
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> getDeletedEvents(String name) {
        List<Event> eventList;

        if (name == null || name.isEmpty()) {

            eventList = eventRepository.findByDeletedTrue();
        } else {
            eventList = eventRepository.findByNameContainingIgnoreCaseAndDeletedTrue(name);
        }
        return eventList.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public void restoreEvent(long id) {
        eventRepository.restoreEvent(id);
    }

    public List<EventResponseDTO> getUnacceptedEvents() {
        List<Event> eventList = eventRepository.findByAccepted(false);

        return eventList.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByEmail(authentication.getName())
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }
    private boolean isNotAdminOrCreator(User user, Event event) {
        if (user.getUserRole() == UserRole.ADMIN) {
            return false;
        }
        if (user.getUserRole() == UserRole.ORGANIZATION) {
            return event.getCreatedBy().getId() != user.getId();
        }
        return true;
    }

    @Transactional
    public void acceptEvent(long id) {
        eventRepository.acceptEvent(id);
    }
}
