package pl.pdec.city.events.domain.model.event;

import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.infrastructure.model.EventSource;

import java.util.Calendar;

abstract public class AbstractEvent implements EventProcess {

    private Calendar occurredOn;

    public abstract void process(Event event);

    public abstract EventSource toEventSource();
}
