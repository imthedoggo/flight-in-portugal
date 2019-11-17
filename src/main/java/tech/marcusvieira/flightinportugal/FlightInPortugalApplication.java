package tech.marcusvieira.flightinportugal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class FlightInPortugalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightInPortugalApplication.class, args);
    }
}
