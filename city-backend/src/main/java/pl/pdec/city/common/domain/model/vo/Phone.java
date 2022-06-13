package pl.pdec.city.common.domain.model.vo;

import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Phone {

    public enum Type {
        MOBILE_PL;

        @NonNull
        public String getAreaCode() {
            switch (this.name()) {
                case "MOBILE_PL":
                    return AreaCode.PL.getAreaCode();

                default:
                    throw new RuntimeException("Missing area code");
            }
        }
    }

    public enum AreaCode {
        PL;

        @NonNull
        public String getAreaCode() {
            switch (this.name()) {
                case "PL":
                    return "+48";

                default:
                    throw new RuntimeException("Missing area code");
            }
        }
    }

    @NonNull
    String value;

    @NonNull
    Type type;

    protected Phone() {
    }

    public Phone(@NonNull Type type, @NonNull String value) {
        this.type = type;
        this.value = value;
        validate();
    }

    @NonNull
    public Type getType() {
        return type;
    }

    protected void setType(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public String getValue() {
        return type.getAreaCode().concat(" ").concat(value);
    }

    protected void setValue(@NonNull String value) {
        this.value = value;
    }

    public void validate() {
        switch (type) {
            case MOBILE_PL -> {
                Pattern p = Pattern.compile("^[0-9]{9}$");
                Matcher m = p.matcher(value);
                if (!m.matches()) {
                    throw new RuntimeException("Mobile phone for PL not properly formatted.");
                }
            }
        }
    }
}
