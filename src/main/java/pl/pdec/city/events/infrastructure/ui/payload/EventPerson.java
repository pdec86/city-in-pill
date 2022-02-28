package pl.pdec.city.events.infrastructure.ui.payload;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "v_event_person")
public class EventPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @Nullable
    @Column(name = "last_name")
    private String lastName;

    @Nullable
    @Column(name = "contact_phone")
    private String phone;

    @Nullable
    @Column(name = "contact_email")
    private String email;

    @ManyToOne(targetEntity = EventUi.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventUi event;

    protected EventPerson() {
    }

    public EventPerson(@NonNull String firstName, @Nullable String lastName, @Nullable String phone,
                       @Nullable String email, EventUi event) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.event = event;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
