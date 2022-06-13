package pl.pdec.city.shops.domain.model;

import pl.pdec.city.common.domain.model.User;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Shop")
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "regon")
    private String regon;

    @Column(name = "krs")
    private String krs;

    @OneToMany(targetEntity = Employee.class, mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    protected Shop() {
    }
}
