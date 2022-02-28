package pl.pdec.city.shops.domain.model;

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

    @Column(name = "owner_first_name")
    private String ownerFirstName;

    @Column(name = "owner_last_name")
    private String ownerLastName;

    @Column(name = "regon")
    private String regon;

    @Column(name = "krs")
    private String krs;

    @OneToMany(targetEntity = Employee.class, mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    protected Shop() {
    }
}
