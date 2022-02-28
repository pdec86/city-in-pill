package pl.pdec.city.events.infrastructure.ui.payload;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    @Column(name = "owner_first_name")
    private String ownerFirstName;

    @Nullable
    @Column(name = "owner_last_name")
    private String ownerLastName;

    @Nullable
    @Column(name = "owner_phone")
    private String ownerPhone;

    @Nullable
    @Column(name = "owner_email")
    private String ownerEmail;

    @NonNull
    @OneToMany(targetEntity = EventPerson.class, mappedBy = "event", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventPerson> persons = new HashSet<>();

    public EventUi() {
    }

    public EventUi(@NonNull UUID id, @NonNull String name, @NonNull Calendar startDateTime, @NonNull Calendar endDateTime,
                   @NonNull String ownerFirstName, @Nullable String ownerLastName, @Nullable String ownerPhone,
                   @Nullable String ownerEmail) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerPhone = ownerPhone;
        this.ownerEmail = ownerEmail;
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
    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    @Nullable
    public String getOwnerLastName() {
        return ownerLastName;
    }

    @Nullable
    public String getOwnerPhone() {
        return ownerPhone;
    }

    @Nullable
    public String getOwnerEmail() {
        return ownerEmail;
    }

    @NonNull
    public Set<EventPerson> getPersons() {
        return persons;
    }

    public void addPerson(@NonNull EventPerson person) {
        this.persons.add(person);
    }

    public void removePerson(@NonNull EventPerson person) {
        this.persons.remove(person);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", ownerFirstName='" + ownerFirstName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
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
