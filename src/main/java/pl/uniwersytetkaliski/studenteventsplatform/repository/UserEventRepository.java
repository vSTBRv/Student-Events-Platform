package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportDto.ParticipantPerEventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent,Long> {

    boolean existsByUserAndEvent(User user, Event event);

    List<UserEvent> findByUserAndEvent(User user, Event event);

    void deleteByUserAndEvent(User user, Event event);

    List<UserEvent> findByEvent_Id(long id);

    List<UserEvent> findByUser_Id(long userId);

    @Query("""
        SELECT COUNT(ue)
        FROM UserEvent ue
        JOIN ue.event e
        WHERE e.startDate BETWEEN :from AND :to
        AND e.accepted = true
        AND e.deleted = false
    """)
    long countParticipantBetweenDates(
            @Param("from")LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("""
    SELECT new pl.uniwersytetkaliski.studenteventsplatform.dto.reportDto.ParticipantPerEventDTO(
        ue.event.id,
        ue.event.name,
        ue.event.startDate,
        COUNT(ue)
    )
    FROM UserEvent ue
    WHERE ue.event.startDate BETWEEN :from AND :to
      AND ue.event.accepted = true
      AND ue.event.deleted = false
    GROUP BY ue.event.id, ue.event.name, ue.event.startDate
    ORDER BY ue.event.startDate ASC
""")
    List<ParticipantPerEventDTO> countParticipantsGroupedByEvent(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

}
