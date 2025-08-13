package codes.dimitri.apps.moviereservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieReservationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReservationAppApplication.class, args);
	}

}
