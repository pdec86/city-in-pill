package pl.pdec.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.model.vo.Authority;
import pl.pdec.city.common.domain.model.vo.UserContact;
import pl.pdec.city.common.domain.repository.UserRepository;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.EventCreated;
import pl.pdec.city.events.domain.model.event.PersonAdded;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/ag", "/ag/", "/ag/{ignored:(?:(?!index\\.html|.*js|.*css|.*ico).*)}/**"}, produces = "text/html")
    public String angularApp(@PathVariable Optional<String> ignored) {
        //String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return "forward:/ag/index.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    private EventUiRepository eventUiRepository;
//
//    @Autowired
//    public void setEventUiRepository(EventUiRepository eventUiRepository) {
//        this.eventUiRepository = eventUiRepository;
//    }

    EventGateway eventGateway;
    @Autowired
    public void setEventGateway(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/testSaveUser")
    public String testSave2() {
        UserContact contact = new UserContact("Paweł", "Konopko", "512112112", "pawel@mail.com");

        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("ROLE_USER"));
        authorities.add(new Authority("ROLE_VIEWER"));

        User user = new User("pablo", passwordEncoder.encode("pass"), contact, authorities);
        userRepository.saveAndFlush(user);

        return "forward:/ag/index.html";
    }

    @GetMapping("/testSaveEvent")
    public String testSave1() {
        Event event = new Event(null, eventGateway);

        User userPablo = userRepository.findByUsername("pablo");
        EventCreated eventCreated = new EventCreated(UUID.randomUUID(), "Some tmp",
                Calendar.getInstance(Locale.getDefault()), Calendar.getInstance(Locale.getDefault()),
                userPablo, new BigDecimal("317.21"));
        event.createNew(eventCreated);

        event.addPerson(new PersonAdded("Pablo", "Pawo", "500100100", "pawo@mail.com"));

        return "forward:/ag/index.html";
    }
}
