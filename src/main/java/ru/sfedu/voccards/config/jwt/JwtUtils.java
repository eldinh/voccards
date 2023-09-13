package ru.sfedu.voccards.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {


    public String generateJwtToken(Authentication authentication) {
        return null;
    }

    public boolean validateJwtToken(String jwt) {
        return false;
    }

    public String getUserNameFromJwtToken(String jwt) {
        return null;
    }

}
