package pl.pdec.city.events.infrastructure.model;

import pl.pdec.city.utils.CityDebugger;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity(name = "EventSource")
@Table(name = "event_source")
public class EventSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Convert(converter = UUIDConverter.class)
    @Column(name = "aggregate_id")
    private UUID aggregateId;

    @Column(name = "event_data")
    private String eventData;

    @Convert(converter = EventTypeConverter.class)
    @Column(name = "event_type")
    private Class<?> eventType;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "occurred_datetime")
    private Calendar occurredDatetime;

    @Column(name = "event_version")
    private int eventVersion = 1;

    protected EventSource() {
    }

    public EventSource(UUID aggregateId, String eventData, Class<?> eventType, String metadata, Calendar occurredDatetime,
                       int eventVersion) {
        this.aggregateId = aggregateId;
        this.eventData = eventData;
        this.eventType = eventType;
        this.metadata = metadata;
        this.occurredDatetime = occurredDatetime;
        this.eventVersion = eventVersion;
    }

    public Long getId() {
        return id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getEventData() {
        return eventData;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public String getMetadata() {
        return metadata;
    }

    public Calendar getOccurredDatetime() {
        return occurredDatetime;
    }

    public int getEventVersion() {
        return eventVersion;
    }

    @Converter
    public static final class UUIDConverter implements AttributeConverter<UUID, String> {

        @Override
        public String convertToDatabaseColumn(UUID attribute) {
            return attribute.toString();
        }

        @Override
        public UUID convertToEntityAttribute(String dbData) {
            return UUID.fromString(dbData);
        }
    }

    @Converter
    public static final class EventTypeConverter implements AttributeConverter<Class<?>, String> {

        @Override
        public String convertToDatabaseColumn(Class<?> attribute) {
            return attribute.getName();
        }

        @Override
        public Class<?> convertToEntityAttribute(String dbData) {
            try {
                return Class.forName(dbData);
            } catch (ClassNotFoundException ex) {
                CityDebugger.getInstance().debugError(ex);
            }

            throw new RuntimeException("Problem with reading event type (class) from database.");
        }
    }
}
