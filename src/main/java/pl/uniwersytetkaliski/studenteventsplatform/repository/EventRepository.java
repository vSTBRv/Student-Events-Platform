package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

    @Modifying
    @Query("UPDATE Event e SET e.deleted = true, e.deletedAt = CURRENT TIMESTAMP WHERE e.id = :eventId")
    void softDelete(Long eventId);

    @Query("SELECT e FROM Event e " +
            "WHERE (:category IS NULL OR e.category.name = :category)" +
            "AND (:status IS NULL OR e.status = :status) AND e.deleted = false "
//            "AND (:startDateFrom IS NULL OR e.startDate >= :startDateFrom ) " +
//            "AND (:startDateTo IS NULL OR e.startDate <= :startDateTo)"
    )
    List<Event> findFilteredEvents(
            @org.springframework.lang.Nullable String category,
            @org.springframework.lang.Nullable pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus status
//            @org.springframework.lang.Nullable java.time.LocalDate startDateFrom,
//            @org.springframework.lang.Nullable java.time.LocalDate startDateTo
    );

    List<Event> findByDeletedFalse();

    List<Event> findByNameContainingIgnoreCaseAndDeletedTrue(String name);

    List<Event> findByDeletedTrue();

    @Modifying
    @Query("UPDATE Event e SET e.deleted = false, e.deletedAt = null WHERE e.id = :id")
    void restoreEvent(long id);

    @Modifying
    @Query ("UPDATE Event e SET e.currentCapacity = :capacity WHERE e.id = :id")
    void updateCurrentCapacity(long id, int capacity);

    List<Event> findByAccepted(boolean accepted);

    @Modifying
    @Query ("UPDATE Event e SET e.accepted = true WHERE e.id = :id")
    void acceptEvent(long id);
}


