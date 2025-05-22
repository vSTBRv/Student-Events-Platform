package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO.EventUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;

@Component
public class EventMapper implements Mapper<Event, EventResponseDTO, EventCreateDTO, EventUpdateDTO> {
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    public EventMapper(LocationMapper locationMapper, UserMapper userMapper, CategoryMapper categoryMapper) {
        this.locationMapper = locationMapper;
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
    }

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
        entity.setName(eventUpdateDTO.getName());
        entity.setDescription(eventUpdateDTO.getDescription());
        entity.setMaxCapacity(eventUpdateDTO.getMaxCapacity());
        entity.setStartDate(eventUpdateDTO.getStartDateTime());
        entity.setEndDate(eventUpdateDTO.getEndDateTime());
        entity.setStatus(eventUpdateDTO.getStatus());
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
        responseDTO.setStartDateTime(entity.getStartDate());
        responseDTO.setEndDateTime(entity.getEndDate());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setCreationDate(entity.getCreationDate());
        responseDTO.setDeleted(entity.isDeleted());
        responseDTO.setDeletedAt(entity.getDeletedAt());
        responseDTO.setCurrentCapacity(entity.getCurrentCapacity());
        responseDTO.setCreatedBy(userMapper.mapToDTO(entity.getCreatedBy()));
        responseDTO.setLocationDTO(locationMapper.toResponseDTO(entity.getLocation()));
        responseDTO.setCategoryDTO(categoryMapper.toResponseDTO(entity.getCategory()));
        return responseDTO;
    }
}
