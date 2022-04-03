package ru.sfedu.voccards.service;


import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.BaseTest;
import ru.sfedu.voccards.entity.Card;
import ru.sfedu.voccards.entity.CardSet;
import ru.sfedu.voccards.entity.UserApp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainServiceTest extends BaseTest {


    private void createUser(){
        log.info("create User");
        log.debug("Register user {}", username);
        assertEquals(authService.register(signupRequest).getStatusCode(), HttpStatus.OK);
        userOptional = userDao.findByUsername(username);
        assert userOptional.isPresent();

    }

    @Test
    public void testCreateCardSet(){
        log.info("Test Create CardSet");
        createUser();

        log.debug("Creating card set for user {}", username);
        responseEntity = mainService.createCardSet(username, cardList, "name");

        log.debug("Getting user {}", username);
        userOptional = userDao.findByUsername(username);
        assert userOptional.isPresent();
        UserApp userApp = userOptional.get();
        log.debug("Getting cardSet from user");
        cardSetOptional = userApp.getOwnSets().stream().findFirst();
        assert cardSetOptional.isPresent();

        assertEquals(cardSetOptional.get().getCreator().getUsername(), username);
    }

    @Test
    public void testFailCreateCardSet(){
        log.info("Test fail Create CardSet");
        log.debug("Create Card Set for non exist user");
        responseEntity = mainService.createCardSet(username, cardList, "q");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        log.debug("Register user");
        assertEquals(authService.register(signupRequest).getStatusCode(), HttpStatus.OK);
        log.debug("Null argument");
        responseEntity = mainService.createCardSet(username, null, null);
        log.info(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

        log.debug("Wrong card is");
        responseEntity = mainService.createCardSet(username, new ArrayList<>(), "name");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteOwnCardSet(){
        log.info("Test delete cardSet");
        createUser();
        log.debug("Creating card set to user {}", username);
        responseEntity = mainService.createCardSet(username, cardList, "name");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        userOptional = userDao.findByUsername(username);
        assert userOptional.isPresent();

        log.debug("Getting cardSets from user {}", username);
        List<CardSet> cardSets = userOptional.get().getOwnSets();
        log.info(cardSets.stream().map(CardSet::getId).collect(Collectors.toList()));
        assert !cardSets.isEmpty();

        log.debug("Deleting cardSet");
        long id = cardSets.get(0).getId();
        responseEntity = mainService.deleteOwnCardSet(username, id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        assert cardSetDao.findById(id).isEmpty();
    }

    @Test
    public void testFailDeleteOwnCardSet(){
        log.info("Test Fail DeleteOwnCardSet");
        log.debug("Null value");
        assertEquals(mainService.deleteOwnCardSet(null, null).getStatusCode()
                , HttpStatus.BAD_REQUEST);
        log.debug("Null id");
        createUser();
        assertEquals(mainService.deleteOwnCardSet(username, null).getStatusCode()
                , HttpStatus.BAD_REQUEST);
        log.debug("Non exist cardSet");
        assertEquals(mainService.deleteOwnCardSet(username, 1L).getStatusCode()
                , HttpStatus.BAD_REQUEST);

    }




}
