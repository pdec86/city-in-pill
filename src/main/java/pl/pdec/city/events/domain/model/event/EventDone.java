package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EventDone extends AbstractEvent {

    @NonNull
    private UUID eventId;

    @NonNull
    private boolean isDone;

    public EventDone(@NonNull UUID eventId, boolean isDone) {
        this.eventId = eventId;
        this.isDone = isDone;
    }

    @NonNull
    public UUID getEventId() {
        return eventId;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void process(Event event) {
        event.markAsDone(this);
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
