package pl.pdec.city.common.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_contact")
public class UserContact implements Serializable {

    @Id
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Nullable
    @Column(name = "last_name")
    private String lastName;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "phone_type")),
            @AttributeOverride(name = "value", column = @Column(name = "phone_value"))
    })
    private Phone phone;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;

    protected UserContact() {
    }

    public UserContact(@NonNull String firstName, String lastName, Phone phone, Email email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
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
    public Phone getPhone() {
        return phone;
    }

    @Nullable
    public Email getEmail() {
        return email;
    }
}
