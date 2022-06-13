package pl.pdec.city.events.domain.model.event;

import pl.pdec.city.events.domain.model.Event;

public interface EventProcess {

    void process(Event event);
}
