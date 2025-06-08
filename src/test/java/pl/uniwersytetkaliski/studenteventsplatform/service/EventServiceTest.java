package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
//
//    @Mock
//    private EventRepository eventRepository;
//
//    @Mock
//    private LocationRepository locationRepository;
//
//    @InjectMocks
//    private EventService eventService;
//
//    @Test
//    void shouldReturnListOfEvents() {
//        Event event = new Event();
//        event.setId(1L);
//        event.setName("Wydarzenie testowe");
//        event.setStatus(EventStatus.PLANNED);
//        event.setDescription("Wydarzenie testowe");
//        event.setMaxCapacity(3000);
//        event.setStartDate(
//                LocalDateTime
//                        .now()
//                        .plusWeeks(3)
//        );
//        event.setEndDate(
//                LocalDateTime
//                        .now()
//                        .plusWeeks(3)
//                        .plusHours(10)
//        );
//
//        Location location = new Location();
//        location.setCity("Kalisz");
//        location.setStreet("Kard. Stefana Wyszyńskiego");
//        location.setHouseNumber("32A");
//        location.setPostalCode("62-800");
//        event.setLocation(location);
//
//        when(eventRepository.findAll()).thenReturn(List.of(event));
//
//        List<EventResponseDto> result = eventService.getAllEvents();
//
//        assertEquals(1, result.size());
//        assertEquals(event.getId(), result.get(0).getId());
//        assertEquals(event.getName(), result.get(0).getName());
//        assertEquals(event.getLocation().getCity(), result.get(0).getLocationCity());
//        assertEquals(event.getLocation().getStreet(), result.get(0).getLocationStreet());
//        assertEquals(event.getLocation().getHouseNumber(), result.get(0).getLocationHouseNumber());
//        assertEquals(event.getLocation().getPostalCode(), result.get(0).getLocationPostalCode());
//        assertEquals(event.getStartDate(), result.get(0).getStartDateTime());
//        assertEquals(event.getEndDate(), result.get(0).getEndDateTime());
//
//    }

//    @Test
//    void shouldThrowWhenEventNotFound() {
//        when(eventRepository.findById(99L)).thenReturn(Optional.empty());
//
//        EventCreateDto dto = new EventCreateDto();
//        dto.setLocationId(1L);
//        dto.setStatus("PLANNED");
//
//        Exception exception = assertThrows(EntityNotFoundException.class, () ->
//                eventService.updateEvent(99L, dto));
//
//        assertEquals("Event with id 99 not found", exception.getMessage());
//    }

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    void shouldCreateEventSuccessfully() {
//        EventCreateDto dto = new EventCreateDto();
//        dto.setName("Wydarzenie testowe");
//        dto.setLocationId(1L);
//        dto.setStatus("PLANNED");
//        dto.setMaxCapacity(100);
//        dto.setStartDateTime(LocalDateTime.now().plusDays(1));
//        dto.setEndDateTime(LocalDateTime.now().plusDays(2));
//        dto.setComments("Wydarzenie testowe");
//
//        Location location = new Location();
//        location.setId(1L);
//        location.setCity("Kalisz");
//        location.setStreet("Kard. Stefana Wyszyskiego");
//        location.setHouseNumber("32A");
//        location.setPostalCode("62-800");
//
//        System.out.println("LocationId in DTO: " + dto.getLocationId());
//
//        Event savedEvent = new Event();
//        savedEvent.setId(1L);
//
//        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
//        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);
//
//        Event result = eventService.createEvent(dto);
//
//        assertNotNull(result);
//        assertEquals(savedEvent.getId(), result.getId());
//        verify(eventRepository).save(any(Event.class));
//        verify(locationRepository).findById(1L);
//    }

//    @Test
//    void shouldUpdateEventSuccessfully() {
//        // DTO z danymi do aktualizacji
//        EventCreateDto dto = new EventCreateDto();
//        dto.setName("Zaktualizowane wydarzenie");
//        dto.setLocationId(1L);
//        dto.setStatus("PLANNED");
//        dto.setMaxCapacity(150);
//        dto.setStartDateTime(LocalDateTime.of(2025, 4, 20, 10, 0));
//        dto.setEndDateTime(LocalDateTime.of(2025, 4, 20, 14, 0));
//        dto.setComments("Po aktualizacji");
//
//        // istniejący event w bazie
//        Event existingEvent = new Event();
//        existingEvent.setId(1L);
//        existingEvent.setName("Stara nazwa");
//
//        // lokalizacja
//        Location location = new Location();
//        location.setId(1L);
//        location.setCity("Kalisz");
//        location.setStreet("Wyszyńskiego");
//        location.setHouseNumber("32A");
//        location.setPostalCode("62-800");
//
//        // co repozytorium ma zwrócić przy findById
//        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
//        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
//        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));
//
//        // act
//        Event updated = eventService.updateEvent(1L, dto);
//
//        // assert
//        assertNotNull(updated);
//        assertEquals(dto.getName(), updated.getName());
//        assertEquals(location, updated.getLocation());
//        assertEquals(EventStatus.PLANNED, updated.getStatus());
//        assertEquals(dto.getComments(), updated.getDescription());
//        assertEquals(dto.getStartDateTime(), updated.getStartDateTime());
//        assertEquals(dto.getEndDateTime(), updated.getEndDateTime());
//        assertEquals(dto.getMaxCapacity(), updated.getMaxCapacity());
//
//        verify(eventRepository).findById(1L);
//        verify(locationRepository).findById(1L);
//        verify(eventRepository).save(any(Event.class));
//    }
}
