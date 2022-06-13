package pl.pdec.city.common.application.service.payload;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.vo.Authority;
import pl.pdec.city.common.domain.model.vo.Phone;

import java.util.HashSet;
import java.util.Set;

public class CreateUserPayload {
    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    String firstName;

    @Nullable
    String lastName;

    @Nullable
    Phone.Type phoneType;

    @Nullable
    String phoneValue;

    @Nullable
    String email;

    Set<Authority> authorities = new HashSet<>();

    public CreateUserPayload(String username, String password, String firstName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public CreateUserPayload setUsername(@NonNull String username) {
        this.username = username;
        return this;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public CreateUserPayload setPassword(@NonNull String password) {
        this.password = password;
        return this;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public CreateUserPayload setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public CreateUserPayload setLastName(@Nullable String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Nullable
    public Phone.Type getPhoneType() {
        return phoneType;
    }

    public CreateUserPayload setPhoneType(@Nullable Phone.Type phoneType) {
        this.phoneType = phoneType;
        return this;
    }

    @Nullable
    public String getPhoneValue() {
        return phoneValue;
    }

    public CreateUserPayload setPhoneValue(@Nullable String phoneValue) {
        this.phoneValue = phoneValue;
        return this;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public CreateUserPayload setEmail(@Nullable String email) {
        this.email = email;
        return this;
    }

    @NonNull
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public CreateUserPayload addAuthority(@NonNull Authority authority) {
        authorities.add(authority);
        return this;
    }
}
