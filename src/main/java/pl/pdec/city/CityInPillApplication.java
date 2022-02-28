package pl.pdec.city;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CityInPillApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CityInPillApplication.class).run(args);
    }
}
