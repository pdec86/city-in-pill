package pl.pdec.city.common.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "user_contact")
public class UserContact implements Serializable {

    @Id
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    protected UserContact() {
    }

    public UserContact(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        if (phone != null) {
            Pattern p = Pattern.compile("^[+]{0,1}[0-9]+$");
            Matcher m = p.matcher(phone);
            if (!m.matches()) {
                throw new RuntimeException("Phone not properly formatted.");
            }
        }

        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        if (email != null) {
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                throw new RuntimeException("E-mail address not properly formatted.");
            }
        }

        this.email = email;
    }
}
