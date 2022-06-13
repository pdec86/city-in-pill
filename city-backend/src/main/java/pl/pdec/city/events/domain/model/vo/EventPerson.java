package pl.pdec.city.events.domain.model.vo;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class EventPerson {

    @NonNull
    private final String firstName;

    @Nullable
    private final String lastName;

    @Nullable
    private final Contact contact;

    public EventPerson(@NonNull String firstName, @Nullable String lastName, @Nullable Contact contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public Contact getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPerson person = (EventPerson) o;

        return firstName.equals(person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
