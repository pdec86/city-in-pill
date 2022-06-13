package pl.pdec.city.events.application.service.command.payload;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Calendar;

public class CreateEventPayload {

    @NonNull
    String eventName;

    @NonNull
    Calendar eventStartDate;

    @NonNull
    Calendar eventEndDate;

    @NonNull
    BigDecimal eventTotalPrice;

    public CreateEventPayload(@NonNull String eventName, @NonNull Calendar eventStartDate, @NonNull Calendar eventEndDate,
                              @NonNull BigDecimal eventTotalPrice) {
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventTotalPrice = eventTotalPrice;
    }

    @NonNull
    public String getEventName() {
        return eventName;
    }

    public void setEventName(@NonNull String eventName) {
        this.eventName = eventName;
    }

    @NonNull
    public Calendar getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(@NonNull Calendar eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @NonNull
    public Calendar getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(@NonNull Calendar eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    @NonNull
    public BigDecimal getEventTotalPrice() {
        return eventTotalPrice;
    }

    public void setEventTotalPrice(@NonNull BigDecimal eventTotalPrice) {
        this.eventTotalPrice = eventTotalPrice;
    }
}
