package pl.pdec.city.events.domain.model.vo;

import org.springframework.lang.Nullable;
import pl.pdec.city.common.domain.model.vo.Email;
import pl.pdec.city.common.domain.model.vo.Phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {

    @Nullable
    private Phone phone;

    @Nullable
    private Email email;

    public Contact(@Nullable Phone phone, @Nullable Email email) {
        if (phone == null && email == null) {
            throw new RuntimeException("At least one contact method must be provided.");
        }

        setPhone(phone);
        setEmail(email);
    }

    @Nullable
    public Phone getPhone() {
        return phone;
    }

    public void setPhone(@Nullable Phone phone) {
        this.phone = phone;
    }

    @Nullable
    public Email getEmail() {
        return email;
    }

    public void setEmail(@Nullable Email email) {
        this.email = email;
    }
}
