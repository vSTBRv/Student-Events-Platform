package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventUpdateDTO;
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

    @GetMapping()
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.ok(eventService.getAllEvents());
        } else {
            return ResponseEntity.ok(eventService.getEventByName(name));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PreAuthorize("hasRole('ORGANIZATION')")
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody EventUpdateDTO dto) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, dto));
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
//    @GetMapping("")
    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(
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

    @GetMapping("deleted")
    public ResponseEntity<List<EventResponseDTO>> getDeletedEvents(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(eventService.getDeletedEvents(name));
    }

    @PatchMapping("deleted/{id}")
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
    public ResponseEntity<List<EventResponseDTO>> getParticipatedEvents() {
        return ResponseEntity.ok(userEventService.getParticipatedEvents());
    }

    @GetMapping("unaccepted")
    public ResponseEntity<List<EventResponseDTO>> getUnacceptedEvents() {
        return ResponseEntity.ok(eventService.getUnacceptedEvents());
    }
}
