package pl.pdec.city.events.infrastructure.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.AbstractEvent;
import pl.pdec.city.events.domain.repository.EventSourceRepository;
import pl.pdec.city.events.domain.repository.EventUiRepository;
import pl.pdec.city.events.infrastructure.ui.payload.EventPerson;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

@Service
public class EventGateway {

    private static EventSourceRepository eventSourceRepository;
    private static EventUiRepository eventUiRepository;

    @Autowired
    public void setEventSourceRepository(EventSourceRepository eventSourceRepository) {
        EventGateway.eventSourceRepository = eventSourceRepository;
    }

    @Autowired
    public void setEventUiRepository(EventUiRepository eventUiRepository) {
        EventGateway.eventUiRepository = eventUiRepository;
    }

    public static void projectEvent(AbstractEvent domainEvent, Event event) {
        EventGateway.eventSourceRepository.saveAndFlush(domainEvent.toEventSource());
        projectEvent(event);
    }

    private static void projectEvent(Event event) {
        EventUi eventUi = new EventUi(event.getId(), event.getName(), event.getStartDateTime(), event.getEndDateTime(),
                event.getOwner().getFirstName(), event.getOwner().getLastName(),
                event.getOwner().getContact() != null ? event.getOwner().getContact().getPhone() : null,
                event.getOwner().getContact() != null ? event.getOwner().getContact().getEmail() : null);

        event.getPersons().forEach(eventPerson -> eventUi.addPerson(
                new EventPerson(eventPerson.getFirstName(), eventPerson.getLastName(),
                        eventPerson.getContact() != null ? eventPerson.getContact().getPhone() : null,
                        eventPerson.getContact() != null ? eventPerson.getContact().getEmail() : null,
                        eventUi)));

        eventUiRepository.saveAndFlush(eventUi);
    }
}
