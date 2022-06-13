package pl.pdec.city.events.application.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.application.service.command.payload.AddEventPersonPayload;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.PersonAdded;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.util.UUID;

@Service
public class AddEventPerson {

    private final EventGateway eventGateway;

    @Autowired
    public AddEventPerson(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    public void execute(UUID eventId, AddEventPersonPayload payload) {
        Event event = eventGateway.restoreEvent(eventId);
        event.setEventGateway(eventGateway);
        event.addPerson(new PersonAdded(payload.getFirstName(), payload.getLastName(), payload.getPhone(), payload.getEmail()));
    }
}
