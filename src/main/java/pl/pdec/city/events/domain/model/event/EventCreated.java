package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EventCreated extends AbstractEvent {

    @NonNull
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private Calendar startDateTime;

    @NonNull
    private Calendar endDateTime;

    @NonNull
    private User owner;

    @NonNull
    private BigDecimal totalPrice;

    protected EventCreated() {
    }

    public EventCreated(@NonNull UUID id, @NonNull String name, @NonNull Calendar startDateTime,
                        @NonNull Calendar endDateTime, @NonNull User owner, @NonNull BigDecimal totalPrice) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.owner = owner;
        this.totalPrice = totalPrice;
    }

    @NonNull
    public UUID getId() {
        return id;
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
    public EventSource toEventSource() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String eventAsJson = objectMapper.writeValueAsString(this);

            return new EventSource(this.id, eventAsJson, this.getClass(), null,
                    Calendar.getInstance(Locale.getDefault()), 1);
        } catch (JsonProcessingException ex) {
            CityDebugger.getInstance().debugError(ex);
        }

        throw new RuntimeException("Something went wrong with changing Event to EventSource.");
    }
}
