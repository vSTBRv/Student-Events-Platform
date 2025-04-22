package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
