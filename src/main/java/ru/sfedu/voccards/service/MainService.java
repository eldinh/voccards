package ru.sfedu.voccards.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MainService {

    /**
     * Adding role to user
     * @param username
     * @param role
     * @return ResponseEntity
     */
    ResponseEntity<?> addRoleToUser(String username, String role);

    /**
     * Getting card set by id
     * @param id
     * @return
     */
    ResponseEntity<?> getCardSetById(Long id);

    /**
     * Finding card by english word
     * @param en
     * @return ResponseEntity
     */
    ResponseEntity<?> findCardByEn(String en);

    /**
     * Finding card by russian word
     * @param ru
     * @return ResponseEntity
     */
    ResponseEntity<?> findCardByRu(String ru);

    /**
     * Creating new CardSet
     * @param username -
     * @param idCardLost - list of card's ids
     * @return ResponseEntity
     */
    ResponseEntity<?> createCardSet(String username, List<Long> idCardLost);

    /**
     * Getting all user's own cardSet
     * @param username
     * @return ResponseEntity
     */
    ResponseEntity<?> getOwnCardSetList(String username);

    /**
     * Deleting user's cardSet by id
     * @param username
     * @param id
     * @return ResponseEntity
     */
    ResponseEntity<?> deleteOwnCardSet(String username, Long id);



}
