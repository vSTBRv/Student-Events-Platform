package pl.uniwersytetkaliski.studenteventsplatform.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventCreateDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventRequestDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.service.EventService;

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
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateDto eventCreateDto) {
        Event created = eventService.createEvent(eventCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestBody EventCreateDto eventCreateDto) {
        try {
            Event updated = eventService.updateEvent(id, eventCreateDto);
            return ResponseEntity.ok(updated);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }
}
