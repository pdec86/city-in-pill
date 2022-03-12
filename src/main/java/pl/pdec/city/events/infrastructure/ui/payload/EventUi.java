package pl.pdec.city.events.infrastructure.ui.payload;

import org.springframework.lang.NonNull;
import pl.pdec.city.common.domain.model.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "v_event")
public class EventUi {

    @NonNull
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Convert(converter = UUIDConverter.class)
    @Column(name = "id")
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "start_date_time")
    private Calendar startDateTime;

    @NonNull
    @Column(name = "end_date_time")
    private Calendar endDateTime;

    @NonNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @NonNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @NonNull
    @OneToMany(targetEntity = EventPersonUi.class, mappedBy = "event", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<EventPersonUi> persons = new HashSet<>();

    public EventUi() {
    }

    public EventUi(@NonNull UUID id, @NonNull String name, @NonNull Calendar startDateTime, @NonNull Calendar endDateTime,
                   @NonNull User owner, @NonNull BigDecimal totalPrice) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.owner = owner;
        this.totalPrice = totalPrice;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public Calendar getEndDateTime() {
        return endDateTime;
    }

    @NonNull
    public User getOwner() {
        return owner;
    }

    @NonNull
    public Set<EventPersonUi> getPersons() {
        return persons;
    }

    public void addPerson(@NonNull EventPersonUi person) {
        this.persons.add(person);
    }

    public void removePerson(@NonNull EventPersonUi person) {
        this.persons.remove(person);
    }

    @Override
    public String toString() {
        return "EventUi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", owner=" + owner +
                ", totalPrice=" + totalPrice +
                ", persons=" + persons +
                '}';
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
}
