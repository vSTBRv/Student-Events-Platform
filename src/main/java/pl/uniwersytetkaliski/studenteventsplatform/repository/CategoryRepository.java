package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Modifying
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDelete(Long id);

    List<Category> findByDeletedFalse();

    @Modifying
    @Query("UPDATE Category c SET c.deleted = false WHERE c.id = :id")
    void restoreCategory(Long id);
}
