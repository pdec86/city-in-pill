package pl.pdec.city.shops.domain.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.shops.domain.model.vo.Contact;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Employee")
@Table(name = "shop_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @Nullable
    @Column(name = "last_name")
    private String lastName;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "contact_phone")),
            @AttributeOverride(name = "email", column = @Column(name = "contact_email"))
    })
    private Contact contact;

    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;

        return firstName.equals(employee.firstName)
                && Objects.equals(lastName, employee.lastName)
                && Objects.equals(contact, employee.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, contact);
    }
}
