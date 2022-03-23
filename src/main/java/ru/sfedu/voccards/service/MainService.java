package ru.sfedu.voccards.service;

import org.springframework.http.ResponseEntity;
import ru.sfedu.voccards.entity.Card;
import ru.sfedu.voccards.entity.CardSet;

import java.util.List;

public interface MainService {
    ResponseEntity<?> addRoleToUser(String username, String role);
    ResponseEntity<?> getCardSetById(Long id);
    ResponseEntity<?> createCardSet(String username, List<Long> idCardLost);
    ResponseEntity<?> getOwnCardSetList(String username);

    ResponseEntity<?> findCardByEn(String en);
    ResponseEntity<?> findCardByRu(String ru);

}
