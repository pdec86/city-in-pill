package pl.pdec.city.events.domain.model.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.utils.CityDebugger;

public class EventDone extends AbstractEvent {

    private boolean isDone;

    protected EventDone() {
    }

    public EventDone(boolean isDone) {
        super();
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void process(Event event) {
        event.markAsDone(this);
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
