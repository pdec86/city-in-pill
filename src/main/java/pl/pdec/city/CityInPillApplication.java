package pl.pdec.city;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.lang.Nullable;
import pl.pdec.city.utils.CityDebugger;

@SpringBootApplication
public class CityInPillApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CityInPillApplication.class).run(args);
    }

    @Value("${cityDebug:false}")
    public void setDebug(@Nullable String debug) {
        CityDebugger.getInstance().setDebug(Boolean.parseBoolean(debug));
    }
}
