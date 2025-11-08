package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventdto.EventResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.mapper.EventMapper;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.UserEventRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEventService {
    private final UserService userService;
    private final UserEventRepository userEventRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public UserEventService(UserService userService, UserEventRepository userEventRepository,
                            EventRepository eventRepository, EventMapper eventMapper) {
        this.userService = userService;
        this.userEventRepository = userEventRepository;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional
    public void registerToEvent(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.getUserByUsername(username);
        Optional<Event> event = eventRepository.findById(id);

        if (user.isEmpty()) {
            System.out.println("User with username " + username + " not found");
            throw new EntityNotFoundException("User not found");
        }
        if (event.isEmpty()) {
            throw new EntityNotFoundException("Event not found");
        }

        int currentCapacity = event.get().getCurrentCapacity();
        if (currentCapacity >= event.get().getMaxCapacity()) {
            throw new IllegalArgumentException("Maximum capacity exceeded");
        }

        if (userEventRepository.existsByUserAndEvent(user.get(), event.get())) {
            throw new IllegalArgumentException("User already registered");
        }

        UserEvent userEvent = new UserEvent(user.get(), event.get());
        userEventRepository.save(userEvent);
        currentCapacity++;
        eventRepository.updateCurrentCapacity(id, currentCapacity);

        System.out.println("User " + username + " registered for event: " + event.get().getName());
    }

    @Transactional
    public void unregisterFromEvent(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.getUserByUsername(username);
        Optional<Event> event = eventRepository.findById(id);

        if (user.isEmpty() || event.isEmpty()) {
            throw new EntityNotFoundException("User or Event not found");
        }

        userEventRepository.deleteByUserAndEvent(user.get(), event.get());
        System.out.println("User " + username + " unregistered from event: " + event.get().getName());
    }

    public List<UserDTO> getParticipants(long id) {
        List<UserEvent> userEvent = userEventRepository.findByEvent_Id(id);
        return userEvent.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UserDTO mapToDTO(UserEvent userEvent) {
        return new UserDTO(userEvent.getUser());
    }

    public List<EventResponseDTO> getParticipatedEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        List<UserEvent> userEvent = userEventRepository.findByUser_Id(user.get().getId());
        return userEvent.stream().map(this::mapToEventDTO).collect(Collectors.toList());
    }

    private EventResponseDTO mapToEventDTO(UserEvent userEvent) {
        Event event = userEvent.getEvent();
        return eventMapper.toResponseDTO(event);
    }
}