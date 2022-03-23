package ru.sfedu.voccards.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        log.info("Getting all users");
        return ResponseEntity.ok().body(userDao.findAll());
    }

    @GetMapping("/cardSet/{cardSetId}")
    public ResponseEntity<?>getCardSet(@PathVariable Long cardSetId){
        return mainService.getCardSetById(cardSetId);
    }


    @PostMapping("/addRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleRequest addRoleRequest){
        return mainService.addRoleToUser(addRoleRequest.getUsername(), addRoleRequest.getRole());
    }

    @GetMapping("/findCardByRu/{ru}")
    public ResponseEntity<?> findCardByRu(@PathVariable String ru){
        return mainService.findCardByRu(ru);
    }

    @GetMapping("/findCardByEn/{en}")
    public ResponseEntity<?> findCardByEn(@PathVariable String en){
        return mainService.findCardByEn(en);
    }

    @PostMapping("/createCardSet")
    public ResponseEntity<?> createCardSet(@RequestBody CreateCardSetRequest cardSetRequest, Principal principal){
        return mainService.createCardSet(principal.getName(), cardSetRequest.getIdCardList());
    }

    @GetMapping("/getOwnCardSet")
    public ResponseEntity<?> getOwnCardSet(Principal principal){
        return mainService.getOwnCardSetList(principal.getName());
    }



}
