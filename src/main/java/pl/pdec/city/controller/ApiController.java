package pl.pdec.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pdec.city.events.domain.model.vo.Contact;

import java.security.Principal;

@RestController
public class ApiController {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/api/testa")
    public ResponseEntity<Contact> testA() {
        return ResponseEntity.ok(new Contact("500100200", "some@mail.com"));
    }

    @GetMapping("/api/testget")
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok("Some test get page: " + passwordEncoder.encode("city_pass"));
    }
}
