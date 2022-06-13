package pl.pdec.city.events.application.service.command.payload;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.vo.Email;
import pl.pdec.city.common.domain.model.vo.Phone;

public class AddEventPersonPayload {

    @NonNull
    String firstName;

    @Nullable
    String lastName;

    @Nullable
    Phone phone;

    @Nullable
    Email email;

    public AddEventPersonPayload(@NonNull String firstName, String lastName, Phone phone, Email email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public Phone getPhone() {
        return phone;
    }

    public void setPhone(@Nullable Phone phone) {
        this.phone = phone;
    }

    @Nullable
    public Email getEmail() {
        return email;
    }

    public void setEmail(@Nullable Email email) {
        this.email = email;
    }
}
