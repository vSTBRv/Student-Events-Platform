package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldReturnListOfEvents() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Wydarzenie testowe");
        event.setStatus(EventStatus.PLANNED);
        event.setComments("Wydarzenie testowe");
        event.setMaxCapacity(3000);
        event.setStartDate(
                LocalDateTime
                        .now()
                        .plusWeeks(3)
        );
        event.setEndDate(
                LocalDateTime
                        .now()
                        .plusWeeks(3)
                        .plusHours(10)
        );

        Location location = new Location();
        location.setCity("Kalisz");
        location.setStreet("Kard. Stefana Wyszyńskiego");
        location.setHouseNumber("32A");
        location.setPostalCode("62-800");
        event.setLocation(location);

        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<EventResponseDto> result = eventService.getAllEvents();

        assertEquals(1, result.size());
        assertEquals(event.getId(), result.get(0).getId());
        assertEquals(event.getName(), result.get(0).getName());
        assertEquals(event.getLocation().getCity(), result.get(0).getLocationCity());
        assertEquals(event.getLocation().getStreet(), result.get(0).getLocationStreet());
        assertEquals(event.getLocation().getHouseNumber(), result.get(0).getLocationHouseNumber());
        assertEquals(event.getLocation().getPostalCode(), result.get(0).getLocationPostalCode());
        assertEquals(event.getStartDate(), result.get(0).getStartDateTime());
        assertEquals(event.getEndDate(), result.get(0).getEndDateTime());

    }
}
