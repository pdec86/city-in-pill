package pl.pdec.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pdec.city.common.application.service.CreateUserService;
import pl.pdec.city.common.application.service.exceptions.UserAlreadyExistsException;
import pl.pdec.city.common.application.service.payload.CreateUserPayload;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.model.vo.Authority;
import pl.pdec.city.common.domain.model.vo.Email;
import pl.pdec.city.common.domain.model.vo.Phone;
import pl.pdec.city.common.domain.model.vo.UserContact;
import pl.pdec.city.common.domain.repository.UserRepository;
import pl.pdec.city.events.application.service.command.AddEventPerson;
import pl.pdec.city.events.application.service.command.CreateEvent;
import pl.pdec.city.events.application.service.command.payload.AddEventPersonPayload;
import pl.pdec.city.events.application.service.command.payload.CreateEventPayload;
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
    public String testSaveUser(CreateUserService service) {
        CreateUserPayload payload = new CreateUserPayload("pablo", "pass", "Paweł");
        payload
                .setLastName("Konopko")
                .setPhoneType(Phone.Type.MOBILE_PL)
                .setPhoneValue("512112112")
                .setEmail("pawel@mail.com")
                .addAuthority(new Authority(Authority.ROLE_USER))
                .addAuthority(new Authority(Authority.ROLE_VIEWER));

        try {
            service.execute(payload);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        return "forward:/ag/index.html";
    }

    @GetMapping("/testSaveEvent")
    public String testSaveEvent(CreateEvent createEvent, AddEventPerson addEventPerson) {
        CreateEventPayload createEventPayload = new CreateEventPayload("A simple event",
                Calendar.getInstance(Locale.getDefault()), Calendar.getInstance(Locale.getDefault()),
                new BigDecimal("317.21"));
        Event event = createEvent.execute("pablo", createEventPayload);

        AddEventPersonPayload addEventPersonPayload = new AddEventPersonPayload("Pablo", "Pablo",
                new Phone(Phone.Type.MOBILE_PL, "500100100"), new Email("pawo@mail.com"));
        addEventPerson.execute(event.getId(), addEventPersonPayload);

        return "forward:/ag/index.html";
    }
}
