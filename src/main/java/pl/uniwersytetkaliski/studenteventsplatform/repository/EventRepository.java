package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCase(String name);
}
