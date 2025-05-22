package pl.uniwersytetkaliski.studenteventsplatform.mapper;

public interface Mapper<E, ResponseDTO, CreateDTO, UpdateDTO> {
    E toEntity(CreateDTO dto);
    E updateEntity(E entity, UpdateDTO dto);
    ResponseDTO toResponseDTO(E entity);
}
