package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"api", "service", "model"})
public class AweleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AweleServerApplication.class, args);
    }
}
