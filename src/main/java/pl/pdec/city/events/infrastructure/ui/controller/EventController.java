package pl.pdec.city.events.infrastructure.ui.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pdec.city.events.domain.repository.EventSourceRepository;

@RestController
public class EventController {

    EventSourceRepository eventSourceRepository;

    @ResponseBody
    public ResponseEntity<String> getListOfEvents() {
        return ResponseEntity.ok("Test");
    }
}
