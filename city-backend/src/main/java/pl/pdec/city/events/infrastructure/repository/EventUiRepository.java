package pl.pdec.city.events.infrastructure.repository;

import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.pdec.city.common.domain.repository.UserRepository;
import pl.pdec.city.events.domain.repository.EventUiRepositoryInterface;
import pl.pdec.city.events.infrastructure.repository.exceptions.EventNotFoundException;
import pl.pdec.city.events.infrastructure.ui.payload.EventUi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Repository
public class EventUiRepository implements EventUiRepositoryInterface {

    private final NamedParameterJdbcTemplate template;
    private final UserRepository userRepository;

    public EventUiRepository(NamedParameterJdbcTemplate template, UserRepository userRepository) {
        this.template = template;
        this.userRepository = userRepository;
    }

    @Override
    public EventUi find(UUID id) throws EventNotFoundException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<EventUi> events = template.query("select * from v_event where id=:id", params, new EventUiRowMapper(userRepository));

        if (events.size() == 0) {
            throw new EventNotFoundException();
        }

        return events.get(0);
    }

    @Override
    public List<EventUi> findAll() {
        return template.query("select * from v_event", new EventUiRowMapper(userRepository));
    }

    @Override
    public List<EventUi> findAll(Sort sort) {
        StringBuilder order = new StringBuilder();
        sort.get().forEach(orderTmp -> {
            if (order.length() <= 0) {
                order.append(" order by ");
            } else {
                order.append(", ");
            }
            order.append(orderTmp.getProperty());
            order.append(orderTmp.getDirection().isAscending() ? " ASC " : " DESC ");
        });

        return template.query("select * from v_event " + order, new EventUiRowMapper(userRepository));
    }

    @Override
    public void saveAndFlush(EventUi eventUi) {
        final String insertSql = "insert into v_event(id, name, start_date_time, end_date_time, owner_id, total_price)"
                + " values(:id, :name, :startDateTime, :endDateTime, :ownerId, :totalPrice)";

        final String updateSql = "update v_event set name=:name, start_date_time=:startDateTime,"
                + " end_date_time=:endDateTime, owner_id=:ownerId, total_price=:totalPrice where id=:id";

        String sql = insertSql;

        SqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("id", eventUi.getId());
        List<Integer> eventsCount = template.query("select count(*) as cnt from v_event where id=:id", queryParams, (RowMapper) (rs, rowNum) -> rs.getInt("cnt"));
        if (eventsCount.size() > 0 && eventsCount.stream().findFirst().get() > 0) {
            sql = updateSql;
        }

        LocalDate localStartDate = LocalDate.ofInstant(eventUi.getStartDateTime().toInstant(), ZoneId.of("UTC"));
        LocalTime localStartTime = LocalTime.ofInstant(eventUi.getStartDateTime().toInstant(), ZoneId.of("UTC"));
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(localStartDate, localStartTime, ZoneId.of("UTC"));

        LocalDate localEndDate = LocalDate.ofInstant(eventUi.getEndDateTime().toInstant(), ZoneId.of("UTC"));
        LocalTime localEndTime = LocalTime.ofInstant(eventUi.getEndDateTime().toInstant(), ZoneId.of("UTC"));
        ZonedDateTime zonedEndDateTime = ZonedDateTime.of(localEndDate, localEndTime, ZoneId.of("UTC"));

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", eventUi.getId())
                .addValue("name", eventUi.getName())
                .addValue("startDateTime", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedStartDateTime))
                .addValue("endDateTime", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedEndDateTime))
                .addValue("ownerId", eventUi.getOwner().getId())
                .addValue("totalPrice", new BigDecimal("0"));
        template.update(sql, param, holder);
    }
}
