package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.LocationDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.*;
import pl.uniwersytetkaliski.studenteventsplatform.repository.CategoryRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.time.LocalDate;
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
        List<Event> events = eventRepository.findByDeletedFalse();
        return events.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EventResponseDto getEventById(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        return mapToDto(event);
    }

    public List<EventResponseDto> getEventByName(String name) {
        List<Event> events = eventRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name);
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

    /**
     * Creates a new {@link Event} entity using the provided {@link EventDTO} data.
     * <p>
     * This method performs the following operations:
     * <ul>
     *     <li>Authenticates the current user from the security context.</li>
     *     <li>Validates that the authenticated user exists in the system.</li>
     *     <li>Initializes a new {@link Event} entity and sets its creator (createdBy) to the authenticated user's ID.</li>
     *     <li>Delegates the entity preparation and saving logic to the {@link #prepareEvent(Event, EventDTO)} method.</li>
     * </ul>
     *
     * @param eventDTO the {@link EventDTO} containing event creation data
     * @return the newly created and saved {@link Event} entity
     * @throws EntityNotFoundException if the authenticated user is not found
     */
    public Event createEvent(EventDTO eventDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.getUserByEmail(auth.getName());
        if (user.isEmpty()) {
            System.out.println("User with email " + auth.getName() + " not found");
            throw new EntityNotFoundException();
        }

        Event event = new Event();
        event.setCreatedBy(user.get().getId());
        return prepareEvent(event, eventDTO);
    }

    /**
     * Updates an existing {@link Event} entity with the provided {@link EventDTO} data.
     * <p>
     * This method performs the following operations:
     * <ul>
     *     <li>Authenticates the current user based on the security context.</li>
     *     <li>Validates that the user exists and has the necessary permissions to update the event
     *     (either ADMIN, or ORGANIZATION that owns the event).</li>
     *     <li>Retrieves the existing {@link Event} by its ID, throwing an exception if not found.</li>
     *    <li>Delegates the update logic to the {@link #prepareEvent(Event, EventDTO)} method, which applies the changes and saves the entity.</li>
     * </ul>
     *
     * @param id       the ID of the event to update
     * @param eventDTO the {@link EventDTO} containing updated event data
     * @return the updated and saved {@link Event} entity
     * @throws EntityNotFoundException if the user or the event is not found
     * @throws AccessDeniedException   if the user does not have permission to update the event
     */
    public Event updateEvent(Long id,EventDTO eventDTO) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = userService.getUserByEmail(auth.getName());
        if (user.isEmpty()) {
            System.out.println("User with email " + auth.getName() + " not found");
            throw new EntityNotFoundException();
        }

        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isEmpty()) {
            System.out.println("Event with id " + id + " not found");
            throw new EntityNotFoundException();
        }

        boolean isAdmin = (user.get()).getUserRole() == UserRole.ADMIN;
        boolean isOrganisation = user.get().getUserRole() == UserRole.ORGANIZATION;
        boolean isOwner = user.get().getId() == existingEvent.get().getCreatedBy();

        if(!(isAdmin || (isOrganisation && isOwner))) {
            throw new AccessDeniedException("Access Denied");
        }

        return prepareEvent(existingEvent.get(),eventDTO);
    }

    /**
     * Prepares and updates an {@link Event} entity with the provided {@link EventDTO} data.
     * <p>
     * This method fills the event object with the provided DTO fields, ensuring that:
     * <ul>
     *     <li>The {@link Location} is either reused if it exists in the database, or a new one is created and saved.</li>
     *     <li>The {@link Category} is retrieved by its ID; if not found, an {@link EntityNotFoundException} is thrown.</li>
     *     <li>Other event fields like name, status, description, capacity, and dates are updated accordingly.</li>
     * </ul>
     * After filling all fields, the method saves the {@code Event} entity to the repository.
     *
     * @param event    the existing {@link Event} entity to update (can be a new instance for creation)
     * @param eventDTO the {@link EventDTO} containing new event data
     * @return the saved {@link Event} entity
     * @throws EntityNotFoundException if the specified category does not exist in the database
     */
    private Event prepareEvent(Event event, EventDTO eventDTO) {
        LocationDTO locationDTO = eventDTO.getLocationDTO();
        Location editedlocation;
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
            editedlocation =  locationRepository.save(newLocation);
            System.out.println(editedlocation.getId());
        } else {
            editedlocation = location.get();
        }
        Optional <Category> category = categoryRepository
                .findById((eventDTO.getCategoryDTO().getId()));
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
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public void softDeleteEvent(Long eventId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = userService.getUserByEmail(auth.getName());
        if (user.isEmpty()) {
            System.out.println("User with email " + auth.getName() + " not found");
            throw new EntityNotFoundException();
        }

        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isEmpty()) {
            System.out.println("Event with id " + eventId + " not found");
            throw new EntityNotFoundException();
        }
        boolean isAdmin = (user.get()).getUserRole() == UserRole.ADMIN;
        boolean isOrganisation = user.get().getUserRole() == UserRole.ORGANIZATION;
        boolean isOwner = user.get().getId() == existingEvent.get().getCreatedBy();

        if(!(isAdmin || (isOrganisation && isOwner))) {
            throw new AccessDeniedException("Access Denied");
        }
        eventRepository.softDelete(eventId);
    }

    public List<EventResponseDto> getFilteredEvents(String categoryId, EventStatus status, LocalDate startDateFrom, LocalDate startDateTo){
        List<Event> filtered = eventRepository.findFilteredEvents(
                categoryId,
                status
//                startDateFrom,
//                startDateTo
        );
        return filtered.stream()
                .filter(e -> startDateFrom == null || !e.getStartDate().toLocalDate().isBefore(startDateFrom))
                .filter(e -> startDateTo == null || !e.getStartDate().toLocalDate().isAfter(startDateTo))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<EventResponseDto> getDeletedEvents(String name) {
        List<Event> eventList;
        if (name == null || name.isEmpty()) {
            eventList = eventRepository.findByDeletedTrue();
        } else {
            eventList = eventRepository.findByNameContainingIgnoreCaseAndDeletedTrue(name);
        }
        return eventList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public void restoreEvent(long id) {
        eventRepository.restoreEvent(id);
    }
}
