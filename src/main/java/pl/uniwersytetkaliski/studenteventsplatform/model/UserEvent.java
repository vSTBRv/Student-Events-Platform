package pl.uniwersytetkaliski.studenteventsplatform.model;

import jakarta.persistence.*;

/**
 * Entity representing the registration of a user for an event.
 * <p>
 * This is a join entity for a many-to-many relationship between {@link User} and {@link Event},
 * using a composite primary key defined by {@link UserEventId}.
 * <p>
 * Fields:
 * <ul>
 *     <li>{@code id} – composite key composed of {@code userId} and {@code eventId}</li>
 *     <li>{@code user} – reference to the user participating in the event</li>
 *     <li>{@code event} – reference to the event the user has registered for</li>
 * </ul>
 *
 * <strong>Note:</strong> The {@code setUser} and {@code setEvent} methods also update the corresponding
 * identifiers in the composite key. This is required when using {@code @MapsId} with {@code @EmbeddedId} in JPA.
 *
 * This entity ensures that each user can register only once for a given event.
 */
@Entity
@Table(name = "user_event")
public class UserEvent {

    @EmbeddedId
    private UserEventId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    private Event event;

    public UserEvent(User user, Event event) {
        this.id = new UserEventId(user.getId(), event.getId());
        this.user = user;
        this.event = event;
    }

    public UserEvent() {
        this.id = new UserEventId();
    }


    public UserEventId getId() {
        return id;
    }

    public void setId(UserEventId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this registration and updates the userId
     * in the composite key.
     *
     * @param user the user registering for the event
     */
    public void setUser(User user) {
        this.user = user;
        this.id.setUserId(user.getId());
    }

    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event associated with this registration and updates the eventId
     * in the composite key.
     *
     * @param event the event being registered for
     */
    public void setEvent(Event event) {
        this.event = event;
        this.id.setEventId(event.getId());
    }
}

