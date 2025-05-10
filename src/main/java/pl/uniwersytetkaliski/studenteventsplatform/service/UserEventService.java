package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.UserEventRepository;

import java.util.Optional;

@Service
public class UserEventService {
    private final UserService userService;
    private final UserEventRepository userEventRepository;
    private final EventRepository eventRepository;

    public UserEventService(UserService userService, UserEventRepository userEventRepository, EventRepository eventRepository) {
        this.userService = userService;
        this.userEventRepository = userEventRepository;
        this.eventRepository = eventRepository;
    }

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
        if (userEventRepository.existsByUserAndEvent(user.get(), event.get())) {
            throw new IllegalArgumentException("User already registered with email " + auth.getName() + " and event " + event.get());
        }
        UserEvent userEvent = new UserEvent();
        userEvent.setUser(user.get());
        userEvent.setEvent(event.get());
        userEventRepository.save(userEvent);

    }

    public void unregisterFromEvent(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.getUserByEmail(auth.getName());
        Optional<Event> event = eventRepository.findById(id);
        if (user.isEmpty() || event.isEmpty()) {
            throw new EntityNotFoundException();
        }
        userEventRepository.deleteByUserAndEvent(user.get(), event.get());
    }
}
