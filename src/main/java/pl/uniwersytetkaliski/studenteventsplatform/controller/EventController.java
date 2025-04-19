package pl.uniwersytetkaliski.studenteventsplatform.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping
//    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
//        return ResponseEntity.ok(eventService.getAllEvents());
//    }

    @GetMapping("")
    public List<EventResponseDto> getEvents(HttpServletRequest request) {
        System.out.println("Sesja: " + request.getSession().getId());
        System.out.println("Użytkownik: " + SecurityContextHolder.getContext().getAuthentication().getName());

        return eventService.getAllEvents();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateDto eventCreateDto) {
        Event created = eventService.createEvent(eventCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestBody EventCreateDto eventCreateDto) {
        Event updated = eventService.updateEvent(id, eventCreateDto);
        return ResponseEntity.ok(updated);
    }
}
