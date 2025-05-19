package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.uniwersytetkaliski.studenteventsplatform.model.Comment;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.Rating;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndEvent(User user, Event event);
    List<Rating> findByEvent(Event event);
}
