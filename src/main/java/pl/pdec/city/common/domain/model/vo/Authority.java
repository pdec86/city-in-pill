package pl.pdec.city.common.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private User user;

    protected Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
