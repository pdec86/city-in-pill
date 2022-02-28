package pl.pdec.city.events.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pdec.city.events.infrastructure.model.EventSource;

import java.util.UUID;

public interface EventSourceRepository extends JpaRepository<EventSource, UUID> {
}
