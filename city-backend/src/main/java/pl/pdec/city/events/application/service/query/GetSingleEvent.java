package pl.pdec.city.events.application.service.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.repository.EventUiRepositoryInterface;
import pl.pdec.city.events.infrastructure.repository.exceptions.EventNotFoundException;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.UUID;

@Service
public class GetSingleEvent {

    private final EventUiRepositoryInterface eventUiRepository;

    @Autowired
    public GetSingleEvent(EventUiRepositoryInterface eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    public EventUi execute(UUID eventId) throws EventNotFoundException {
        return eventUiRepository.find(eventId);
    }
}
