package pl.uniwersytetkaliski.studenteventsplatform.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"event_id", "user_id"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Event event;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private int ratingValue; // 1-5

//    @Column

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getValue() {
        return ratingValue;
    }

    public void setValue(int value) {
        this.ratingValue = value;
    }
//    private String uniqueConstraint; // np. eventId_userId - do wymuszenia jedenj oceny
}
