package pl.uniwersytetkaliski.studenteventsplatform.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key class for the {@link UserEvent} entity.
 * <p>
 * Represents a unique pairing of a user and an event. This class is used as the embedded ID
 * for the join entity that models user registrations to events.
 * <p>
 * Fields:
 * <ul>
 *     <li>{@code userId} - ID of the user</li>
 *     <li>{@code eventId} - ID of the event</li>
 * </ul>
 */
@Embeddable
public class UserEventId implements Serializable {

    private long userId;
    private long eventId;

    public UserEventId() {}

    public UserEventId(long userId, long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEventId that)) return false;
        return userId == that.userId &&
                eventId == that.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
