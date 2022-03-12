package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

public class EventCreated extends AbstractEvent {

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private UUID eventId;

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private String name;

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private Calendar startDateTime;

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private Calendar endDateTime;

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private User owner;

    @SuppressWarnings({"NotNullFieldNotInitialized"})
    @NonNull
    private BigDecimal totalPrice;

    protected EventCreated() {
    }

    public EventCreated(@NonNull UUID eventId, @NonNull String name, @NonNull Calendar startDateTime,
                        @NonNull Calendar endDateTime, @NonNull User owner, @NonNull BigDecimal totalPrice) {
        super();
        this.eventId = eventId;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.owner = owner;
        this.totalPrice = totalPrice;
    }

    @NonNull
    public UUID getEventId() {
        return eventId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public Calendar getEndDateTime() {
        return endDateTime;
    }

    @NonNull
    public User getOwner() {
        return owner;
    }

    @NonNull
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void process(Event event) {
        event.createNew(this);
    }

    @Override
    public EventSource toEventSource(Event event) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            String eventAsJson = objectMapper.writeValueAsString(this);

            return new EventSource(event.getId(), eventAsJson, this.getClass(), null, this.occurredOn, 1);
        } catch (JsonProcessingException ex) {
            CityDebugger.getInstance().debugError(ex);
        }

        throw new RuntimeException("Something went wrong with changing Event to EventSource.");
    }
}
