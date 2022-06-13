package pl.pdec.city.events.application.service.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pdec.city.events.domain.repository.EventUiRepositoryInterface;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.List;

@Service
public class GetListOfEvents {

    private final EventUiRepositoryInterface eventUiRepository;

    @Autowired
    public GetListOfEvents(EventUiRepositoryInterface eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    public List<EventUi> execute() {
         return eventUiRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }
}
