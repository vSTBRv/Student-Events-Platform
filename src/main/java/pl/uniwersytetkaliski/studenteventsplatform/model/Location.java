package pl.uniwersytetkaliski.studenteventsplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @lombok.Setter
    @lombok.Getter
    @Column(nullable = false)
    private String city;

    @lombok.Setter
    @lombok.Getter
    @Column(nullable = false)
    private String street;

    @lombok.Setter
    @lombok.Getter
    @Column(nullable = false)
    private String houseNumber;

    @lombok.Setter
    @lombok.Getter
    @Column(nullable = false)
    private String postalCode;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
