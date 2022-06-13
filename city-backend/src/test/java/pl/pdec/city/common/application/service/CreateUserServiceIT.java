package pl.pdec.city.common.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pdec.city.common.application.service.exceptions.UserAlreadyExistsException;
import pl.pdec.city.common.application.service.payload.CreateUserPayload;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.model.vo.Authority;
import pl.pdec.city.common.domain.model.vo.Phone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserServiceIT {

    private final CreateUserService service;
    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserServiceIT(CreateUserService service, MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void execute() throws UserAlreadyExistsException {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("pablo"));

        CreateUserPayload payload = new CreateUserPayload("pablo", "pass", "Paweł");
        payload
                .setLastName("Konopko")
                .setPhoneType(Phone.Type.MOBILE_PL)
                .setPhoneValue("512112112")
                .setEmail("pawel@mail.com")
                .addAuthority(new Authority(Authority.ROLE_USER))
                .addAuthority(new Authority(Authority.ROLE_VIEWER));

        service.execute(payload);

        UserDetails user = userDetailsService.loadUserByUsername("pablo");
        assertTrue(user instanceof User);
        assertEquals("pablo", user.getUsername());
        assertTrue(passwordEncoder.matches("pass", user.getPassword()));

        assert ((User) user).getContact() != null;
        assertEquals("Paweł", ((User) user).getContact().getFirstName());
        assertEquals("Konopko", ((User) user).getContact().getLastName());
        assert ((User) user).getContact().getPhone() != null;
        assertEquals(Phone.Type.MOBILE_PL, ((User) user).getContact().getPhone().getType());
        assertEquals(Phone.Type.MOBILE_PL.getAreaCode() + " 512112112", ((User) user).getContact().getPhone().getValue());
        assert ((User) user).getContact().getEmail() != null;
        assertEquals("pawel@mail.com", ((User) user).getContact().getEmail().getValue());

        assertEquals(2, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(new Authority(Authority.ROLE_USER)));
        assertTrue(user.getAuthorities().contains(new Authority(Authority.ROLE_VIEWER)));

        assertThrows(UserAlreadyExistsException.class, () -> service.execute(payload));
    }
}