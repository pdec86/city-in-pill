package pl.pdec.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pdec.city.common.domain.model.vo.Email;
import pl.pdec.city.common.domain.model.vo.Phone;
import pl.pdec.city.events.domain.model.Event;
import pl.pdec.city.events.domain.model.vo.Contact;
import pl.pdec.city.events.domain.repository.EventUiRepositoryInterface;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Locale;
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

    EventUiRepositoryInterface eventUiRepository;

    @Autowired
    public void setEventUiRepository(EventUiRepositoryInterface eventUiRepository) {
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
        TemporalAccessor temporalAccessorStartDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse("2022-06-09T12:35:53.372Z");
        ZonedDateTime zonedEndDateTime = ZonedDateTime.from(temporalAccessorStartDateTime);
        Calendar now = Calendar.getInstance(Locale.getDefault());
        now.set(
                zonedEndDateTime.getYear(),
                zonedEndDateTime.getMonthValue() - 1,
                zonedEndDateTime.getDayOfMonth(),
                zonedEndDateTime.getHour(),
                zonedEndDateTime.getMinute(),
                zonedEndDateTime.getSecond()
        );

        LocalDate localDate = LocalDate.ofInstant(now.toInstant(), ZoneId.of("UTC"));
        LocalTime localTime = LocalTime.ofInstant(now.toInstant(), ZoneId.of("UTC"));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, ZoneId.of("UTC"));
        System.err.println(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime));
        return ResponseEntity.ok(new Contact(new Phone(Phone.Type.MOBILE_PL, "500100200"), new Email("some@mail.com")));
    }

    @GetMapping("/api/testget")
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok("Some test get page: " + passwordEncoder.encode("city_pass"));
    }
}
