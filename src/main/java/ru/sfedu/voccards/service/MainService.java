package ru.sfedu.voccards.service;

import org.springframework.http.ResponseEntity;

public interface MainService {

    ResponseEntity<?> addRoleToUser(String username, String role);

    ResponseEntity<?> getCardSetById(Long id);

}
