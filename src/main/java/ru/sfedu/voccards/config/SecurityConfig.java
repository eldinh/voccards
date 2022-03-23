package ru.sfedu.voccards.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ru.sfedu.voccards.config.jwt.AuthEntryPointJwt;
import ru.sfedu.voccards.config.jwt.AuthTokenFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enable method @PreAuthorized annotation in controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // using daoAuthenticationProvider
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override  // using jwt, stateless system
    protected void configure(HttpSecurity http) throws Exception {

        // configuring web based(using http) security for specific http requests

        http.csrf().disable();

//        http.cors().disable();

        // Dismissing CORS policy
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        // configuring exception handling for non-authenticate user
        http.exceptionHandling().authenticationEntryPoint(authEntryPointJwt);

        //
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/register").permitAll();
        http.authorizeRequests().antMatchers("/api/signin").permitAll();
        http.authorizeRequests().antMatchers("/api/users").permitAll();
        http.authorizeRequests().antMatchers("/api/createCardSet").authenticated();
        http.authorizeRequests().antMatchers("/api/getOwnCardSet").authenticated();

        // adding custom filter
        http.addFilterBefore(authJwtTokenFilter()
                , UsernamePasswordAuthenticationFilter.class);
    }



}
