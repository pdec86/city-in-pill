package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EventCreated extends AbstractEvent {

    @NonNull
    private final UUID id;

    @NonNull
    private final String name;

    @NonNull
    private final String ownerFirstName;

    @Nullable
    private final String ownerLastName;

    @Nullable
    private final String ownerPhone;

    @Nullable
    private final String ownerEmail;

    @NonNull
    private final Calendar startDateTime;

    @NonNull
    private final Calendar endDateTime;

    public EventCreated(@NonNull UUID id, @NonNull String name, @NonNull String ownerFirstName, @Nullable String ownerLastName,
                        @Nullable String ownerPhone, @Nullable String ownerEmail, @NonNull Calendar startDateTime,
                        @NonNull Calendar endDateTime) {
        this.id = id;
        this.name = name;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerPhone = ownerPhone;
        this.ownerEmail = ownerEmail;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    @Nullable
    public String getOwnerLastName() {
        return ownerLastName;
    }

    @Nullable
    public String getOwnerPhone() {
        return ownerPhone;
    }

    @Nullable
    public String getOwnerEmail() {
        return ownerEmail;
    }

    @NonNull
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public Calendar getEndDateTime() {
        return endDateTime;
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
