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

public class PersonAdded extends AbstractEvent {

    @NonNull
    private UUID eventId;

    @NonNull
    private final String firstName;

    @Nullable
    private final String lastName;

    @Nullable
    private final String phone;

    @Nullable
    private final String email;

    public PersonAdded(@NonNull UUID eventId, @NonNull String firstName, @Nullable String lastName,
                       @Nullable String phone, @Nullable String email) {
        this.eventId = eventId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    @NonNull
    public UUID getEventId() {
        return eventId;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Override
    public void process(Event event) {
        event.addPerson(this);
    }

    @Override
    public EventSource toEventSource() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String eventAsJson = objectMapper.writeValueAsString(this);

            return new EventSource(this.eventId, eventAsJson, this.getClass(), null,
                    Calendar.getInstance(Locale.getDefault()), 1);
        } catch (JsonProcessingException ex) {
            CityDebugger.getInstance().debugError(ex);
        }

        throw new RuntimeException("Something went wrong with changing Event to EventSource.");
    }
}
