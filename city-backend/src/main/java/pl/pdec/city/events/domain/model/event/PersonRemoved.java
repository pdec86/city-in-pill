package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

public class PersonRemoved extends AbstractEvent {

    @NonNull
    private String firstName = "";

    @Nullable
    private String lastName;

    protected PersonRemoved() {
    }

    public PersonRemoved(@NonNull String firstName, @Nullable String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Override
    public void process(Event event) {
        event.removePerson(this);
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
