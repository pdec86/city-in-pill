package pl.pdec.city.events.domain.model.event;

import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;

import java.util.Calendar;
import java.util.Locale;

abstract public class AbstractEvent implements EventProcess {

    protected Calendar occurredOn;

    public AbstractEvent() {
        this.occurredOn = Calendar.getInstance(Locale.getDefault());
    }

    public abstract void process(Event event);

    public abstract EventSource toEventSource(Event event);
}
