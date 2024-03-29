package ru.sfedu.voccards.controller;


import lombok.extern.slf4j.Slf4j;
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



@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {


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

    @PostMapping("/createCardSet")
    public ResponseEntity<?> createCardSet(@RequestBody CreateCardSetRequest cardSetRequest, Principal principal){
        log.info("/api/createCardSet - Creating cardSet to user {}", principal.getName());
        return mainService.createCardSet(principal.getName(), cardSetRequest.getCardList(), cardSetRequest.getName());
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

    @GetMapping("/preview")
    public ResponseEntity<?> getPreview(Principal principal){
        log.info("/api/preview - Getting cardSet preview for user {}", principal.getName());
        return mainService.getPreview(principal.getName());
    }



}
