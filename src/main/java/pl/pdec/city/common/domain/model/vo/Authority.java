package pl.pdec.city.common.domain.model.vo;

import org.springframework.security.core.GrantedAuthority;
import pl.pdec.city.common.domain.model.User;

import javax.persistence.*;

@Entity
@Table(name = "user_authority")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Authority() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
