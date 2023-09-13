package ru.sfedu.voccards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.sfedu.voccards.config.properties.VoccardsProperties;

@EnableConfigurationProperties(VoccardsProperties.class)
@SpringBootApplication
public class VoccardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoccardsApplication.class, args);
    }
}
