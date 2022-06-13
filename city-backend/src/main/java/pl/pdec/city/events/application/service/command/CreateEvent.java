package pl.pdec.city.events.application.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.repository.UserRepository;
import pl.pdec.city.events.application.service.command.payload.CreateEventPayload;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.EventCreated;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.util.UUID;

@Service
public class CreateEvent {

    private final EventGateway eventGateway;
    private final UserRepository userRepository;

    @Autowired
    public CreateEvent(EventGateway eventGateway, UserRepository userRepository) {

        this.eventGateway = eventGateway;
        this.userRepository = userRepository;
    }

    public Event execute(String ownerUsername, CreateEventPayload payload) {
        Event event = new Event(null, eventGateway);

        User owner = userRepository.findByUsername(ownerUsername);
        EventCreated eventCreated = new EventCreated(UUID.randomUUID(), payload.getEventName(),
                payload.getEventStartDate(), payload.getEventEndDate(),
                owner, payload.getEventTotalPrice());
        event.createNew(eventCreated);

        return event;
    }
}
