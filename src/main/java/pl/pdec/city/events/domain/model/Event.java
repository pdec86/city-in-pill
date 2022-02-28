package pl.pdec.city.events.domain.model;

import org.springframework.lang.NonNull;
import pl.pdec.city.events.domain.model.event.AbstractEvent;
import pl.pdec.city.events.domain.model.event.EventCreated;
import pl.pdec.city.events.domain.model.event.PersonAdded;
import pl.pdec.city.events.domain.model.event.PersonRemoved;
import pl.pdec.city.events.domain.model.vo.Contact;
import pl.pdec.city.events.domain.model.vo.EventPerson;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import java.util.*;

public class Event {

    private List<AbstractEvent> eventList = new ArrayList<>();

    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private Calendar startDateTime;

    @NonNull
    private Calendar endDateTime;

    @NonNull
    private EventPerson owner;

    @NonNull
    private Set<EventPerson> persons = new HashSet<>();

    public Event() {
        this.name = "";
        this.startDateTime = Calendar.getInstance(Locale.getDefault());
        this.endDateTime = Calendar.getInstance(Locale.getDefault());
        this.owner = new EventPerson("", "", null);
    }

    public UUID getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public Calendar getEndDateTime() {
        return endDateTime;
    }

    @NonNull
    public EventPerson getOwner() {
        return owner;
    }

    @NonNull
    public Set<EventPerson> getPersons() {
        return persons;
    }

    /**
     * Create new event.
     * @param eventCreated Data to create event.
     */
    public void createNew(EventCreated eventCreated) {
        if (eventCreated.getName().isEmpty()) {
            throw new RuntimeException("Event name cannot be empty.");
        }

        this.record(eventCreated);
    }

    /**
     * Adds new person. If person with first name and last name already exists, then replace person and update contact info.
     * @param personAdded Person to be added or replaced contact.
     */
    public void addPerson(PersonAdded personAdded) {
        record(personAdded);
    }

    /**
     * Person to remove from the event group. All payments will remain in history and new ones will be split
     * by other persons in group.
     * @param personRemoved Person to be removed.
     */
    public void removePerson(PersonRemoved personRemoved) {
        record(personRemoved);
    }

    /**
     * Get event list, which occurred on this object.
     * @return Processed event list.
     */
    public List<AbstractEvent> getEventList() {
        return Collections.unmodifiableList(this.eventList);
    }

    private void record(AbstractEvent event) {
        this.eventList.add(event);
        apply(event);
    }

    private void apply(AbstractEvent event) {
        if (event instanceof EventCreated eventCreated) {
            processCreateNew(eventCreated);
        } else if (event instanceof PersonAdded personAdded) {
            processAddPerson(personAdded);
        } else if (event instanceof PersonRemoved personRemoved) {
            processRemovePerson(personRemoved);
        }

        EventGateway.projectEvent(event, this);
    }

    private void processCreateNew(EventCreated eventCreated) {
        this.id = eventCreated.getId();
        this.name = eventCreated.getName();
        this.startDateTime = eventCreated.getStartDateTime();
        this.endDateTime = eventCreated.getEndDateTime();

        Contact contact = new Contact(eventCreated.getOwnerPhone(), eventCreated.getOwnerEmail());
        this.owner = new EventPerson(eventCreated.getOwnerFirstName(), eventCreated.getOwnerLastName(), contact);
    }

    private void processAddPerson(PersonAdded personAdded) {
        Contact contact = new Contact(personAdded.getPhone(), personAdded.getEmail());
        EventPerson person = new EventPerson(personAdded.getFirstName(), personAdded.getLastName(), contact);

        if (persons.contains(person)) {
            persons.remove(person);
            persons.add(person);
        } else {
            persons.add(person);
        }
    }

    private void processRemovePerson(PersonRemoved personRemoved) {
        EventPerson person = new EventPerson(personRemoved.getFirstName(), personRemoved.getLastName(), null);
        persons.remove(person);
    }
}
