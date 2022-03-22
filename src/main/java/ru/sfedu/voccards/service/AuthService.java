package ru.sfedu.voccards.service;


import org.springframework.http.ResponseEntity;
import ru.sfedu.voccards.dto.LoginRequest;
import ru.sfedu.voccards.dto.SignupRequest;

public interface AuthService {

    ResponseEntity<?> register(SignupRequest signupRequest);

    ResponseEntity<?> authUser(LoginRequest loginRequest);
}
