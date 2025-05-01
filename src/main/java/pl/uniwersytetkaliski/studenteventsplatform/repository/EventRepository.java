package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.uniwersytetkaliski.studenteventsplatform.dto.EventResponseDto;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCase(String name);

    @Modifying
    @Query("UPDATE Event e SET e.deleted = true, e.deletedAt = CURRENT TIMESTAMP WHERE e.id = :eventId")
    void softDelete(Long eventId);

    @Query("SELECT e FROM Event e " +
            "WHERE (:categoryId IS NULL OR e.category.id = :categoryId)" +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (:startDateFrom IS NULL OR e.startDate >= :startDateFrom ) " +
            "AND (:startDateTo IS NULL OR e.startDate <= :startDateTo)")
    List<Event> findFilteredEvents(
            @org.springframework.lang.Nullable Long categoryId,
            @org.springframework.lang.Nullable pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus status,
            @org.springframework.lang.Nullable java.time.LocalDateTime startDateFrom,
            @org.springframework.lang.Nullable java.time.LocalDateTime startDateTo
    );
}


