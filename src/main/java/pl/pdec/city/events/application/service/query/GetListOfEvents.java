package pl.pdec.city.events.application.service.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.repository.EventUiRepository;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.List;

@Service
public class GetListOfEvents {

    EventUiRepository eventUiRepository;

    @Autowired
    public void setEventUiRepository(EventUiRepository eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    public List<EventUi> execute() {
         return eventUiRepository.findAll();
    }
}
