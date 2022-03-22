package ru.sfedu.voccards.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.sfedu.voccards.service.userDetail.UserDetailsImpl;

import java.util.Date;

@Component
public class JwtUtils {

    Logger log = LogManager.getLogger(JwtUtils.class.getName());

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationsMs}")
    private int jwtExpirationsMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        // using Algorithm HS512 with username and jwtSecret for creating jwt token
        return Jwts.builder()
                .setId(userPrincipal.getId().toString())
                .setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateJwtToken(String jwt){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException e){
            log.error("Function JwtUtils validateJwtToken had failed[0]: {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String jwt){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }

}
