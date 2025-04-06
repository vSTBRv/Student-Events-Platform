package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventCreateDto;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private LocationRepository locationRepository;

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

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void shouldCreateEventSuccessfully() {
        EventCreateDto dto = new EventCreateDto();
        dto.setName("Wydarzenie testowe");
        dto.setLocationId(1L);
        dto.setStatus("PLANNED");
        dto.setMaxCapacity(100);
        dto.setStartDate(LocalDateTime.now().plusDays(1));
        dto.setEndDate(LocalDateTime.now().plusDays(2));
        dto.setComments("Wydarzenie testowe");

        Location location = new Location();
        location.setId(1L);
        location.setCity("Kalisz");
        location.setStreet("Kard. Stefana Wyszyskiego");
        location.setHouseNumber("32A");
        location.setPostalCode("62-800");

        System.out.println("LocationId in DTO: " + dto.getLocationId());

        Event savedEvent = new Event();
        savedEvent.setId(1L);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event result = eventService.createEvent(dto);

        assertNotNull(result);
        assertEquals(savedEvent.getId(), result.getId());
        verify(eventRepository).save(any(Event.class));
        verify(locationRepository).findById(1L);
    }
}
