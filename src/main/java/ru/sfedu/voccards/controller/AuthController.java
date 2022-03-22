package ru.sfedu.voccards.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.voccards.dto.LoginRequest;
import ru.sfedu.voccards.dto.SignupRequest;
import ru.sfedu.voccards.service.AuthService;
@RestController
@RequestMapping("/api")
public class AuthController {

    private static Logger log = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest){
        log.debug("Authentication");
        return authService.authUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        return authService.register(signupRequest);
    }
}
