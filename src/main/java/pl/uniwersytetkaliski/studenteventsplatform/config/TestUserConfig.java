package pl.uniwersytetkaliski.studenteventsplatform.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.time.LocalDateTime;

@Configuration
public class TestUserConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("test")
                .password("{noop}password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public CommandLineRunner commandLineRunner(LocationRepository locationRepository, EventRepository eventRepository) {
        return args -> {
            Location location = new Location();
            location.setCity("Kalisz");
            location.setStreet("Niecała");
            location.setHouseNumber("3A");
            location.setPostalCode("62-800");
            locationRepository.save(location);

            Event event = new Event();
            event.setName("Test Event");
            event.setLocation(location);
            event.setStatus(EventStatus.ONGOING);
            event.setStartDate(LocalDateTime.now().plusDays(2));
            event.setEndDate(LocalDateTime.now().plusDays(3));
            event.setComments("Wydarzenie testowe");
            eventRepository.save(event);
        };
    }
}
