package pl.pdec.city.common.domain.model.vo;

import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    @NonNull
    String value;

    protected Email() {
    }

    public Email(@NonNull String value) {
        this.value = value;
        validate();
    }

    @NonNull
    public String getValue() {
        return value;
    }

    protected void setValue(@NonNull String value) {
        this.value = value;
    }

    public void validate() {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(value);
        if (!m.matches()) {
            throw new RuntimeException("E-mail address not properly formatted.");
        }
    }
}
