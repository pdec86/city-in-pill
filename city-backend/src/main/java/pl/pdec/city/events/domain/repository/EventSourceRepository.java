package pl.pdec.city.events.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.pdec.city.events.infrastructure.model.EventSource;

import java.util.List;
import java.util.UUID;

public interface EventSourceRepository extends JpaRepository<EventSource, UUID> {

    @Query("select es from EventSource es where es.aggregateId = ?1")
    List<EventSource> findAllById(UUID uuids);
}
