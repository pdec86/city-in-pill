package pl.pdec.city.events.domain.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.events.domain.model.event.*;
import pl.pdec.city.events.domain.model.vo.Contact;
import pl.pdec.city.events.domain.model.vo.EventPerson;
import pl.pdec.city.events.infrastructure.utils.EventGateway;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Event {

    @NonNull
    private final List<AbstractEvent> eventList = new ArrayList<>();

    @Nullable
    private final EventGateway eventGateway;

    @NonNull
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private Calendar startDateTime;

    @NonNull
    private Calendar endDateTime;

    private boolean isDone = false;

    @NonNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @NonNull
    private BigDecimal totalPrice;

    @NonNull
    private final Set<EventPerson> persons = new HashSet<>();

    public Event(@Nullable UUID id) {
        this(id, null);
    }

    public Event(@Nullable UUID id, @Nullable EventGateway eventGateway) {
        this.eventGateway = eventGateway;
        this.id = id != null ? id : UUID.randomUUID();
        this.name = "";
        this.startDateTime = Calendar.getInstance(Locale.getDefault());
        this.endDateTime = Calendar.getInstance(Locale.getDefault());
        this.totalPrice = new BigDecimal(0);
        this.owner = new User("anonymous", "empty_pass", null, new HashSet<>());
    }

    @NonNull
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
    public User getOwner() {
        return owner;
    }

    @NonNull
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @NonNull
    public BigDecimal getPricePerPerson() {
        return totalPrice.divide(new BigDecimal(1 + persons.size()), 2, RoundingMode.HALF_UP);
    }

    public boolean isDone() {
        return isDone;
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

        this.id = eventCreated.getEventId();
        this.name = eventCreated.getName();
        this.startDateTime = eventCreated.getStartDateTime();
        this.endDateTime = eventCreated.getEndDateTime();
        this.owner = eventCreated.getOwner();
        this.totalPrice = eventCreated.getTotalPrice();

        this.record(eventCreated);
    }

    /**
     * Adds new person. If person with first name and last name already exists, then replace person and update contact info.
     * @param personAdded Person to be added or replaced contact.
     */
    public void addPerson(PersonAdded personAdded) {
        Contact contact = new Contact(personAdded.getPhone(), personAdded.getEmail());
        EventPerson person = new EventPerson(personAdded.getFirstName(), personAdded.getLastName(), contact);

        if (persons.contains(person)) {
            persons.remove(person);
            persons.add(person);
        } else {
            persons.add(person);
        }

        record(personAdded);
    }

    /**
     * Person to remove from the event group. All payments will remain in history and new ones will be split
     * by other persons in group.
     * @param personRemoved Person to be removed.
     */
    public void removePerson(PersonRemoved personRemoved) {
        EventPerson person = new EventPerson(personRemoved.getFirstName(), personRemoved.getLastName(), null);
        persons.remove(person);

        record(personRemoved);
    }

    /**
     * Mark event as done (or revert to un-done state).
     * @param eventDone If event should be marked as done or not done.
     */
    public void markAsDone(EventDone eventDone) {
        this.isDone = eventDone.isDone();

        record(eventDone);
    }

    /**
     * Get event list, which occurred on this object.
     * @return Processed event list.
     */
    public List<AbstractEvent> getEventList() {
        return Collections.unmodifiableList(this.eventList);
    }

    private void record(AbstractEvent aEvent) {
        this.eventList.add(aEvent);

        if (eventGateway != null) {
            eventGateway.projectEvent(aEvent, this);
        }
    }
}
