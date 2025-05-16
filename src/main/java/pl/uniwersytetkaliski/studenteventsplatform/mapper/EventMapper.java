package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;

@Component
public class EventMapper implements Mapper<Event, EventResponseDTO, EventCreateDTO, EventUpdateDTO> {
    @Override
    public Event toEntity(EventCreateDTO eventCreateDTO) {
        Event event = new Event();
        event.setName(eventCreateDTO.getName());
        event.setDescription(eventCreateDTO.getDescription());
        event.setMaxCapacity(eventCreateDTO.getMaxCapacity());
        event.setStartDate(eventCreateDTO.getStartDateTime());
        event.setEndDate(eventCreateDTO.getEndDateTime());
        return event;
    }

    @Override
    public Event updateEntity(Event entity, EventUpdateDTO eventUpdateDTO) {
        return entity;
    }

    @Override
    public EventResponseDTO toResponseDTO(Event entity) {
        EventResponseDTO responseDTO = new EventResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setMaxCapacity(entity.getMaxCapacity());
        responseDTO.setAccepted(entity.isAccepted());

        return responseDTO;
    }
}
