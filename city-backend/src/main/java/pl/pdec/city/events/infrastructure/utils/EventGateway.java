package pl.pdec.city.events.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.AbstractEvent;
import pl.pdec.city.events.domain.model.event.EventProcess;
import pl.pdec.city.events.domain.repository.EventSourceRepository;
import pl.pdec.city.events.domain.repository.EventUiRepositoryInterface;
import pl.pdec.city.events.infrastructure.model.EventSource;
import pl.pdec.city.events.infrastructure.ui.payload.EventPersonUi;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;
import pl.pdec.city.utils.CityDebugger;

import java.util.List;
import java.util.UUID;

@Service
public class EventGateway {

    private EventSourceRepository eventSourceRepository;
    private EventUiRepositoryInterface eventUiRepository;

    @Autowired
    public void setEventSourceRepository(EventSourceRepository eventSourceRepository) {
        this.eventSourceRepository = eventSourceRepository;
    }

    @Autowired
    public void setEventUiRepository(EventUiRepositoryInterface eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    public Event restoreEvent(UUID id) {
        Event event = new Event(id);
        List<EventSource> eventSources = eventSourceRepository.findAllById(event.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
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

    public void projectEvent(AbstractEvent aEvent, Event event) {
        this.eventSourceRepository.saveAndFlush(aEvent.toEventSource(event));
        projectEvent(event);
    }

    private void projectEvent(Event event) {
        EventUi eventUi = new EventUi(event.getId(), event.getName(), event.getStartDateTime(), event.getEndDateTime(),
                event.getOwner(), event.getTotalPrice());

        event.getPersons().forEach(eventPerson -> eventUi.addPerson(
                new EventPersonUi(eventPerson.getFirstName(), eventPerson.getLastName(),
                        eventPerson.getContact() != null
                                ? (eventPerson.getContact().getPhone() != null ? eventPerson.getContact().getPhone().getValue() : null)
                                : null,
                        eventPerson.getContact() != null
                                ? (eventPerson.getContact().getEmail() != null ? eventPerson.getContact().getEmail().getValue() : null)
                                : null,
                        eventUi)));

        this.eventUiRepository.saveAndFlush(eventUi);
    }
}
