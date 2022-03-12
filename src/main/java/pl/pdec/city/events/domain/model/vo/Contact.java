package pl.pdec.city.events.domain.model.vo;

import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {

    @Nullable
    private String phone;

    @Nullable
    private String email;

    public Contact(@Nullable String phone, @Nullable String email) {
        if (phone == null && email == null) {
            throw new RuntimeException("At least one contact method must be provided.");
        }

        setPhone(phone);
        setEmail(email);
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        if (phone != null) {
            Pattern p = Pattern.compile("^[+]{0,1}[0-9]+$");
            Matcher m = p.matcher(phone);
            if (!m.matches()) {
                throw new RuntimeException("Phone not properly formatted.");
            }
        }

        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        if (email != null) {
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                throw new RuntimeException("E-mail address not properly formatted.");
            }
        }

        this.email = email;
    }
}
