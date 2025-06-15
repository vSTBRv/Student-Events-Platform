package pl.uniwersytetkaliski.studenteventsplatform.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {

    Optional<User> findPasswordByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
