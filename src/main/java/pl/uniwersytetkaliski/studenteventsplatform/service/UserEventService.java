package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.UserEventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEventService {
    private final UserService userService;
    private final UserEventRepository userEventRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;

    public UserEventService(UserService userService, UserEventRepository userEventRepository, EventRepository eventRepository, EventService eventService) {
        this.userService = userService;
        this.userEventRepository = userEventRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @Transactional
    public void registerToEvent(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.getUserByEmail(auth.getName());
        Optional<Event> event = eventRepository.findById(id);
        if (user.isEmpty()) {
            System.out.println("User with email " + auth.getName() + " not found");
            throw new EntityNotFoundException();
        }
        if (event.isEmpty()) {
            throw new EntityNotFoundException();
        }
        int currentCapacity = event.get().getCurrentCapacity();
        if (currentCapacity == 0) {
            throw new IllegalArgumentException("Maximum capacity exceeded");
        }

        if (userEventRepository.existsByUserAndEvent(user.get(), event.get())) {
            throw new IllegalArgumentException("User already registered with email " + auth.getName() + " and event " + event.get());
        }
        UserEvent userEvent = new UserEvent(user.get(),event.get());
        userEventRepository.save(userEvent);
        currentCapacity--;
        eventRepository.updateCurrentCapacity(id,currentCapacity);
    }

    @Transactional
    public void unregisterFromEvent(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.getUserByEmail(auth.getName());
        Optional<Event> event = eventRepository.findById(id);
        if (user.isEmpty() || event.isEmpty()) {
            throw new EntityNotFoundException();
        }
        userEventRepository.deleteByUserAndEvent(user.get(), event.get());
    }

    public List<UserDTO> getParticipants(long id) {
        List<UserEvent> userEvent = userEventRepository.findByEvent_Id(id);
       return userEvent.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UserDTO mapToDTO(UserEvent userEvent) {
        return new UserDTO(
                userEvent.getUser().getFullName()
        );
    }

    public List<EventResponseDto> getParticipatedEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.getUserByEmail(auth.getName());
        List<UserEvent> userEvent = userEventRepository.findByUser_Id(user.get().getId());
        return userEvent.stream().map(this::mapToEventDTO).collect(Collectors.toList());
    }

    private EventResponseDto mapToEventDTO(UserEvent userEvent) {
        Event event = userEvent.getEvent();
        return eventService.mapToDto(event);
    }
}
