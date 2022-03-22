package ru.sfedu.voccards.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static ru.sfedu.voccards.Constants.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger log = LogManager.getLogger(AuthEntryPointJwt.class.getName());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException authException) throws IOException, ServletException {

        log.info("Starting AuthEntryPointJwt commence[0]");

        // non-authorize user

        log.info(authException.getCause());
        log.error("commence[1]: Non-authorize user, reason - {}", authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.debug("commence[2]: Creating response answer");
        final Map<String, Object> body = new HashMap<>();
        body.put(STATUS, HttpServletResponse.SC_UNAUTHORIZED);
        body.put(ERROR, UNAUTHORIZED);
        body.put(MESSAGE, authException.getMessage());
        body.put(PATH, request.getServletPath());

        log.debug("commence[3]: Mapping to json");
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}
