package pl.pdec.city.common.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pdec.city.common.domain.model.vo.Authority;
import pl.pdec.city.common.domain.model.vo.UserContact;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "city_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @NonNull
    @Column(name = "password")
    private String password;

    @Nullable
    @OneToOne(targetEntity = UserContact.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private UserContact contact;

    @NonNull
    @OneToMany(targetEntity = Authority.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonManagedReference
    private Set<Authority> authorities;

    @JsonProperty("credentials_expired")
    @Column(name = "credentials_expired")
    private boolean credentialsExpired = false;

    @JsonProperty("account_expired")
    @Column(name = "account_expired")
    private boolean accountExpired = false;

    @JsonProperty("account_locked")
    @Column(name = "account_locked")
    private boolean accountLocked = false;

    @JsonProperty("is_enabled")
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    protected User() {
    }

    public User(@NonNull String username, @NonNull String password, @Nullable UserContact contact, @Nullable Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.authorities = authorities != null ? authorities : new HashSet<>();

        if (this.contact != null) {
            this.contact.setUser(this);
        }
        this.authorities.forEach(authority -> authority.setUser(this));
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @NonNull
    @Override
    public String getPassword() {
        return password;
    }

    @NonNull
    @Override
    public String getUsername() {
        return username;
    }

    @Nullable
    public UserContact getContact() {
        return contact;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
