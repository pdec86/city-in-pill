package pl.pdec.city.events.infrastructure.repository;

import org.springframework.jdbc.core.RowMapper;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.repository.UserRepository;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EventUiRowMapper implements RowMapper<EventUi> {

    private final UserRepository userRepository;

    public EventUiRowMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public EventUi mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));

        TemporalAccessor temporalStartDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(rs.getString("start_date_time"));
        ZonedDateTime zonedStartDateTime = ZonedDateTime.from(temporalStartDateTime);
        Calendar startDateTime = Calendar.getInstance(Locale.getDefault());
        startDateTime.set(
                zonedStartDateTime.getYear(),
                zonedStartDateTime.getMonthValue() - 1,
                zonedStartDateTime.getDayOfMonth(),
                zonedStartDateTime.getHour(),
                zonedStartDateTime.getMinute(),
                zonedStartDateTime.getSecond()
        );

        TemporalAccessor temporalEndDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(rs.getString("end_date_time"));
        ZonedDateTime zonedEndDateTime = ZonedDateTime.from(temporalEndDateTime);
        Calendar endDateTime = Calendar.getInstance(Locale.getDefault());
        endDateTime.set(
                zonedEndDateTime.getYear(),
                zonedEndDateTime.getMonthValue() - 1,
                zonedEndDateTime.getDayOfMonth(),
                zonedEndDateTime.getHour(),
                zonedEndDateTime.getMinute(),
                zonedEndDateTime.getSecond()
        );

        User owner = userRepository.getReferenceById(rs.getLong("owner_id"));
        BigDecimal totalPrice = new BigDecimal(rs.getString("total_price"));

        return new EventUi(id, rs.getString("name"), startDateTime, endDateTime, owner, totalPrice);
    }
}
