package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
