package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.service.EventService;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserEventService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final UserEventService userEventService;

    public EventController(EventService eventService, UserEventService userEventService) {
        this.eventService = eventService;
        this.userEventService = userEventService;
    }

    /**
     * Handles HTTP GET requests to retrieve events.
     * <p>
     * If a query parameter {@code name} is provided, it returns a list of events
     * whose name matches the given value. If the {@code name} parameter is absent
     * or empty, it returns a list of all available events.
     * </p>
     *
     * @param name optional query parameter used to filter events by their name.
     * @return a {@link ResponseEntity} containing a list of {@link EventResponseDto}
     *         objects. The response contains either all undeleted events or filtered undeleted events
     *         matching the specified name.
     */
    @GetMapping()
    public ResponseEntity<List<EventResponseDto>> getAllEvents(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.ok(eventService.getAllEvents());
        } else {
            return ResponseEntity.ok(eventService.getEventByName(name));
        }
    }

    /**
     * Handles HTTP GET request to retrieve a specific event by its ID
     * @param id the ID of the event to retrieve, passed as a path variable
     * @return a {@link ResponseEntity} containing the {@link EventResponseDto} with specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Handles an HTTP POST request to create a new event.
     * <p>
     * This endpoint is restricted to users with the <code>ORGANIZATION</code> role.
     * It accepts an {@link EventDTO} in the request body, which contains the details of the event to be created.
     * Upon successful creation, it returns the created {@link Event} with HTTP status 201 (Created).
     * </p>
     *
     * @param eventDTO the data transfer object containing details of the event to be created
     * @return a {@link ResponseEntity} containing the created {@link Event} and HTTP status 201
     */
    @PreAuthorize("hasRole('ORGANIZATION')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event created = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Handles an HTTP PUT request to update an existing event.
     * <p>
     * This method updates an event identified by its ID using the data provided in the {@link EventDTO}.
     * If the user does not have permission to update the event, an {@link AccessDeniedException} is caught,
     * and the method returns a 401 Unauthorized status.
     * </p>
     *
     * @param id       the ID of the event to update, provided as a path variable
     * @param eventDTO the data transfer object containing updated event details
     * @return a {@link ResponseEntity} containing the updated {@link Event} if successful,
     *         or HTTP 401 if access is denied
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestBody EventDTO eventDTO) {
        try {
            Event updated = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(updated);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    /**
     * Handles an HTTP DELETE request to permanently delete an event by its ID.
     * <p>
     * This endpoint is accessible only to users with the <code>ADMIN</code> role.
     * It performs a hard delete, meaning the event is completely removed from the database.
     * </p>
     *
     * @param id the ID of the event to delete, provided as a path variable
     * @return a {@link ResponseEntity} with HTTP status 200 OK if the deletion was successful
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles an HTTP DELETE request to soft delete an event by its ID.
     * <p>
     * This method marks the event as deleted without physically removing it from the database.
     * It is accessible to users with either <code>ADMIN</code> or <code>ORGANIZATION</code> roles.
     * The soft-deleted events can later be restored if needed.
     * </p>
     *
     * @param id the ID of the event to soft delete, provided as a path variable
     * @return a {@link ResponseEntity} with HTTP status 200 OK upon successful operation
     */
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZATION')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> softDeleteEvent(@PathVariable Long id) {
        eventService.softDeleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
//    @GetMapping("")
    public ResponseEntity<List<EventResponseDto>> getFilteredEvents(
            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) EventStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo
    ) {
        // opcja z frontem
        if (name != null && !name.isEmpty() && category == null && status == null && startDateFrom == null && startDateTo == null) {
            return ResponseEntity.ok(eventService.getEventByName(name));
        }
        if (name == null && category == null && status == null && startDateFrom == null && startDateTo == null) {
            return ResponseEntity.ok(eventService.getAllEvents());
        }
        return ResponseEntity.ok(eventService.getFilteredEvents(category, status, startDateFrom, startDateTo));
    }

    /**
     * Handles an HTTP GET request to retrieve all soft-deleted events.
     * <p>
     * Optionally filters the results by event name if the {@code name} query parameter is provided.
     * Returns a list of {@link EventResponseDto} objects representing the deleted events.
     * </p>
     *
     * @param name optional query parameter to filter deleted events by name
     * @return a {@link ResponseEntity} containing a list of deleted {@link EventResponseDto} objects
     */
    @GetMapping("deleted")
    public ResponseEntity<List<EventResponseDto>> getDeletedEvents(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(eventService.getDeletedEvents(name));
    }

    /**
     * Handles an HTTP PUT request to restore a soft-deleted event by its ID.
     * <p>
     * This method reactivates an event that was previously soft-deleted,
     * making it visible and accessible again.
     * </p>
     *
     * @param id the ID of the event to restore, provided as a path variable
     * @return a {@link ResponseEntity} with HTTP status 200 OK upon successful restoration
     */
    @PutMapping("deleted/{id}")
    public ResponseEntity<Void> restoreDeletedEvent(@PathVariable long id) {
        eventService.restoreEvent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/register")
    public ResponseEntity<String> registerToEvent(@PathVariable long id) {
        try {
            userEventService.registerToEvent(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}/unregister")
    public ResponseEntity<String> unregisterFromEvent(@PathVariable long id) {
            userEventService.unregisterFromEvent(id);
            return ResponseEntity.ok().build();

    }

    @GetMapping("{id}/participants")
    public ResponseEntity<List<UserDTO>> getParticipants(@PathVariable long id) {
        return ResponseEntity.ok(userEventService.getParticipants(id));
    }

    @GetMapping("participated")
    public ResponseEntity<List<EventResponseDto>> getParticipatedEvents() {
        return ResponseEntity.ok(userEventService.getParticipatedEvents());
    }
}
