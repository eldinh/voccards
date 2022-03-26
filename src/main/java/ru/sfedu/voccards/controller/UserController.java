package ru.sfedu.voccards.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.dto.AddRoleRequest;
import ru.sfedu.voccards.dto.CreateCardSetRequest;
import ru.sfedu.voccards.entity.UserApp;
import ru.sfedu.voccards.service.MainService;

import java.security.Principal;
import java.util.List;



@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private MainService mainService;

    @GetMapping("/users")
    public ResponseEntity<List<UserApp>>getUsers(){
        log.info("/api/users - Getting all users");
        return ResponseEntity.ok().body(userDao.findAll());
    }

    @GetMapping("/cardSet/{cardSetId}")
    public ResponseEntity<?>getCardSet(@PathVariable Long cardSetId){
        log.info("/api/cardSet/{} - Getting card by id {}", cardSetId, cardSetId);
        return mainService.getCardSetById(cardSetId);
    }


    @PostMapping("/addRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleRequest addRoleRequest){
        log.info("/api/addRole - Adding role {} to user {}", addRoleRequest.getRole(), addRoleRequest.getUsername());
        return mainService.addRoleToUser(addRoleRequest.getUsername(), addRoleRequest.getRole());
    }

    @GetMapping("/findCardByRu/{ru}")
    public ResponseEntity<?> findCardByRu(@PathVariable String ru){
        log.info("/api/findCardByRu - Finding card by word {}", ru);
        return mainService.findCardByRu(ru);
    }

    @GetMapping("/findCardByEn/{en}")
    public ResponseEntity<?> findCardByEn(@PathVariable String en){
        log.info("/api/findCardByEn - Finding card by word {}", en);
        return mainService.findCardByEn(en);
    }

    @PostMapping("/createCardSet")
    public ResponseEntity<?> createCardSet(@RequestBody CreateCardSetRequest cardSetRequest, Principal principal){
        log.info("/api/createCardSet - Creating cardSet to user {}", principal.getName());
        return mainService.createCardSet(principal.getName(), cardSetRequest.getIdCardList());
    }

    @GetMapping("/getOwnCardSet")
    public ResponseEntity<?> getOwnCardSet(Principal principal){
        log.info("/api/getOwnCardSet - Getting CardSet to user {}", principal.getName());
        return mainService.getOwnCardSetList(principal.getName());
    }

    @DeleteMapping("/deleteCardSet/{id}")
    public ResponseEntity<?> deleteOwnCardSet(@PathVariable Long id, Principal principal){
        log.info("/api/deleteCardSet/{} - Deleting cardSet {}", id, id);
        return mainService.deleteOwnCardSet(principal.getName(), id);
    }



}
