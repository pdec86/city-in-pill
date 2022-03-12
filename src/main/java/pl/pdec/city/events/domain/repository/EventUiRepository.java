package pl.pdec.city.events.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.UUID;

public interface EventUiRepository extends JpaRepository<EventUi, UUID> {
}
