package pl.pdec.city.events.infrastructure.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pdec.city.events.application.service.query.GetListOfEvents;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.util.List;

@RestController
public class EventController {

    GetListOfEvents getListOfEvents;

    @Autowired
    public void setGetListOfEvents(GetListOfEvents getListOfEvents) {
        this.getListOfEvents = getListOfEvents;
    }

    @GetMapping("/api/events/getList")
    public ResponseEntity<List<EventUi>> getListOfEvents() {
        return ResponseEntity.ok(getListOfEvents.execute());
    }
}
