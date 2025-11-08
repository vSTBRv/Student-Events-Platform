//package pl.uniwersytetkaliski.studenteventsplatform.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;
//import pl.uniwersytetkaliski.studenteventsplatform.security.SecurityConfig;
//import pl.uniwersytetkaliski.studenteventsplatform.service.EventService;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = EventController.class)
//@AutoConfigureMockMvc
//@Import(SecurityConfig.class)
//@ActiveProfiles("test")
//class EventControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    @MockBean
//    private EventService eventService;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @WithMockUser
//    @Test
//    void shouldReturnAllEvents() throws Exception {
//        long eventId = 1;
//        EventResponseDto eventResponseDto = new EventResponseDto();
//        eventResponseDto.setId(eventId);
//        eventResponseDto.setName("Test");
//        eventResponseDto.setStatus(EventStatus.PLANNED);
//        when(eventService.getAllEvents()).thenReturn(List.of(eventResponseDto));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/events"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(eventId))
//                .andExpect(jsonPath("$[0].name").value("Test"));
//    }
//
//    @WithMockUser
//    @Test
//    void shouldReturnEventById() throws Exception {
//        long eventId = 3;
//        EventResponseDto eventResponseDto = new EventResponseDto();
//        eventResponseDto.setId(eventId);
//        eventResponseDto.setName("Test");
//        eventResponseDto.setStatus(EventStatus.PLANNED);
//        when(eventService.getEventById(eventId)).thenReturn((eventResponseDto));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(eventId))
//                .andExpect(jsonPath("$.name").value("Test"));
//    }
//
////    @WithMockUser(roles = "ORGANIZATION")
////    @Test
////    void shouldCreateEvent() throws Exception {
////        EventCreateDto eventCreateDto = new EventCreateDto();
////        eventCreateDto.setName("Test");
////        eventCreateDto.setStatus(EventStatus.PLANNED.name());
////
////        Event event = new Event();
////        event.setName("Test");
////        event.setStatus(EventStatus.PLANNED);
////        event.setId(5);
////
////        when(eventService.createEvent(Mockito.any(EventCreateDto.class))).thenReturn(event);
////
////        mockMvc.perform(post("/api/events")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(eventCreateDto))
////        ).andExpect(status().isCreated())
////        .andExpect(jsonPath("$.id").value(5))
////        .andExpect(jsonPath("$.name").value("Test"))
////        .andExpect(jsonPath("$.status").value(EventStatus.PLANNED.name()));
////    }
//
////    @WithMockUser
////    @Test
////    void shouldUpdateEvent() throws Exception {
////        long id = 3L;
////        EventCreateDto updateDto = new EventCreateDto();
////        updateDto.setName("Updated Event");
////
////        Event event = new Event();
////        event.setId(id);
////        event.setName("Updated Event");
////
////        when(eventService.updateEvent(Mockito.eq(id), Mockito.any(EventCreateDto.class))).thenReturn(event);
////
////        mockMvc.perform(put("/api/events/{id}", id)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(updateDto)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").value(id))
////                .andExpect(jsonPath("$.name").value("Updated Event"));
////    }
//
//}