package pl.uniwersytetkaliski.studenteventsplatform.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {

}
