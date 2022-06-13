package pl.pdec.city.events.domain.repository;

import org.springframework.data.domain.Sort;
import pl.pdec.city.events.infrastructure.repository.exceptions.EventNotFoundException;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.List;
import java.util.UUID;

public interface EventUiRepositoryInterface {

    EventUi find(UUID id) throws EventNotFoundException;

    List<EventUi> findAll();

    List<EventUi> findAll(Sort sort);

    void saveAndFlush(EventUi eventUi);
}
