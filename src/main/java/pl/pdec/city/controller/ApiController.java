package pl.pdec.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.vo.Contact;
import pl.pdec.city.events.domain.repository.EventUiRepository;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.security.Principal;
import java.util.Optional;

@RestController
public class ApiController {

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    EventGateway eventGateway;
    @Autowired
    public void setEventGateway(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    EventUiRepository eventUiRepository;
    @Autowired
    public void setEventUiRepository(EventUiRepository eventUiRepository) {
        this.eventUiRepository = eventUiRepository;
    }

    @GetMapping("/api/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/api/getevent")
    public ResponseEntity<Event> getEvent() {
        Optional<EventUi> eventUiOpt = eventUiRepository.findAll().stream().findFirst();
        Event event = null;
        if (eventUiOpt.isPresent()) {
            event = eventGateway.restoreEvent(eventUiOpt.get().getId());
        }

        return ResponseEntity.ok(event);
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
