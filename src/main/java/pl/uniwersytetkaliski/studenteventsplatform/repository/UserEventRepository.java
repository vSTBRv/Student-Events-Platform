package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent,Long> {

    boolean existsByUserAndEvent(User user, Event event);

    List<UserEvent> findByUserAndEvent(User user, Event event);

    void deleteByUserAndEvent(User user, Event event);

    List<UserEvent> findByEvent_Id(long id);

    List<UserEvent> findByUser_Id(long userId);

    @Query("""
        SELECT count(ue)\s
        FROM UserEvent ue
        WHERE ue.event.startDate BETWEEN :from AND :to
        AND ue.event.accepted = true\s
        AND ue.event.deleted = false
""")
    long countParticipantBetweenDates(
            @Param("from")LocalDateTime from,
            @Param("to") LocalDateTime to);
}
