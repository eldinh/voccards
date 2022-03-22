package ru.sfedu.voccards.config.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sfedu.voccards.service.userDetail.UserDetailsServiceImpl;
import static ru.sfedu.voccards.Constants.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter  extends OncePerRequestFilter {

    private static Logger log = LogManager.getLogger(AuthTokenFilter.class.getName());

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${app.authorization.type}")
    private String authorizationType;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        // doing all filthy work
        try {
            String jwt = parseJwt(request);
            if (jwt !=null && jwtUtils.validateJwtToken(jwt)){
                // get username
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // trying to find username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // getting authorization token
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e){
            log.error("Function AuthTokenFilter doFilterInternal had failed - {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request){
        // checking if authorization token exists in header and if it started with Bearer
        String headerAuth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(authorizationType.concat(SPACE)))
            // getting token from header
            return headerAuth.substring(authorizationType.length() + SPACE.length(), headerAuth.length());
        return null;
    }
}
