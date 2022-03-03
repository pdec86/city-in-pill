package pl.pdec.city.events.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.AbstractEvent;
import pl.pdec.city.events.domain.model.event.EventProcess;
import pl.pdec.city.events.domain.repository.EventSourceRepository;
import pl.pdec.city.events.domain.repository.EventUiRepository;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.events.infrastructure.ui.payload.EventPersonUi;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;
import pl.pdec.city.utils.CityDebugger;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class EventGateway {

    private EventSourceRepository eventSourceRepository;
    private EventUiRepository eventUiRepository;

    @Autowired
    public void setEventSourceRepository(EventSourceRepository eventSourceRepository) {
        this.eventSourceRepository = eventSourceRepository;
    }

    @Autowired
    public void setEventUiRepository(EventUiRepository eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    public Event restoreEvent(UUID id) {
        Event event = new Event(id, null,null);
        List<EventSource> eventSources = eventSourceRepository.findAllById(event.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        eventSources.forEach(eventSource -> {
            try {
                EventProcess eventProcess = (EventProcess)
                        objectMapper.readValue(eventSource.getEventData(), eventSource.getEventType());
                eventProcess.process(event);
            } catch (JsonProcessingException exception) {
                CityDebugger.getInstance().debugError(exception);
            }
        });

        return event;
    }

    public void projectEvent(AbstractEvent domainEvent, Event event) {
        this.eventSourceRepository.saveAndFlush(domainEvent.toEventSource());
        projectEvent(event);
    }

    private void projectEvent(Event event) {
        EventUi eventUi = new EventUi(event.getId(), event.getName(), event.getStartDateTime(), event.getEndDateTime(),
                event.getOwner(), event.getTotalPrice());

        event.getPersons().forEach(eventPerson -> eventUi.addPerson(
                new EventPersonUi(eventPerson.getFirstName(), eventPerson.getLastName(),
                        eventPerson.getContact() != null ? eventPerson.getContact().getPhone() : null,
                        eventPerson.getContact() != null ? eventPerson.getContact().getEmail() : null,
                        eventUi)));

        this.eventUiRepository.saveAndFlush(eventUi);
    }
}
