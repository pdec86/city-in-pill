package pl.pdec.city.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.event.EventCreated;
import pl.pdec.city.events.domain.model.event.PersonAdded;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/testsave")
    public String testSave() {
        Event event = new Event();
        EventCreated eventCreated = new EventCreated(UUID.randomUUID(), "Some tmp",
                Calendar.getInstance(Locale.getDefault()), Calendar.getInstance(Locale.getDefault()),
                "Przemyslavo", "Niebieski", "677133077",
                "przemyslavo@mail.com", new BigDecimal("317.21"));
        event.createNew(eventCreated);

        event.addPerson(new PersonAdded(event.getId(), "Pablo", "Pawo", "500100100", "pawo@mail.com"));

        return "forward:/ag/index.html";
    }
}
