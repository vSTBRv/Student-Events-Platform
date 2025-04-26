package pl.uniwersytetkaliski.studenteventsplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.uniwersytetkaliski.studenteventsplatform.model.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT 1 FROM Location l WHERE l.city = :city and l.street = :street and l.houseNumber = :houseNumber and l.postalCode = :postalCode")
    Optional<Location> findByAll(String city, String street, String houseNumber, String postalCode);
}
