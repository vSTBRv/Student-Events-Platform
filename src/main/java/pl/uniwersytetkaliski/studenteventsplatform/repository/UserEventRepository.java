package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserEvent;

import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent,Long> {

    boolean existsByUserAndEvent(User user, Event event);

    List<UserEvent> findByUserAndEvent(User user, Event event);

    void deleteByUserAndEvent(User user, Event event);

    List<UserEvent> findByEvent_Id(long id);
}
