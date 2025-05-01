package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
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
     *         objects. The response contains either all events or filtered events
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


    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PreAuthorize("hasRole('ORGANIZATION')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event created = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZATION')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> softDeleteEvent(@PathVariable Long id) {
        eventService.softDeleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDto>> getFilteredEvents(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) EventStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTo
    ) {
        return ResponseEntity.ok(eventService.getFilteredEvents(categoryId, status, startDateFrom, startDateTo));
    }
}
