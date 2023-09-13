package ru.sfedu.voccards.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("ru.voccards")
public record VoccardsProperties(
        String jwtSecret,
        long jwtExpirationsMs
) {}
