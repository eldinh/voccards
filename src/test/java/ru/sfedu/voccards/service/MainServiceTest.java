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
    public void testFindCardByEn(){
        log.info("Test Find Card by en");
        log.debug("Getting card that mention");
        responseEntity = mainService.findCardByEn("app");
        log.info(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assert ((List<Card>) responseEntity.getBody()).size() > 0;
    }

    @Test
    public void testFindCardByEnFail(){
        log.info("Test Fail Find Card by ru");
        log.debug("Null value");
        assertEquals(mainService.findCardByEn(null).getStatusCode(), HttpStatus.BAD_REQUEST);

        log.debug("Card or cards don't exist");
        assertEquals(mainService.findCardByEn("RANDWADSLDJAJFAPWF").getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testFindCardByRu(){
        log.info("Test Find Card by ru");
        log.debug("Getting card that mention");
        responseEntity = mainService.findCardByRu("п");
        log.info(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testFindCardByRuFail(){
        log.info("Test Fail Find Card by ru");
        log.debug("Null value");
        assertEquals(mainService.findCardByRu(null).getStatusCode(), HttpStatus.BAD_REQUEST);

        log.debug("Card or cards don't exist");
        assertEquals(mainService.findCardByRu("вцфаолрфдрадфоац").getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateCardSet(){
        log.info("Test Create CardSet");
        createUser();

        log.debug("Creating card set for user {}", username);
        responseEntity = mainService.createCardSet(username, cardList.stream().map(Card::getId).collect(Collectors.toList()), "name");

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
        responseEntity = mainService.createCardSet(username, cardList.stream().map(Card::getId).collect(Collectors.toList()), "q");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        log.debug("Register user");
        assertEquals(authService.register(signupRequest).getStatusCode(), HttpStatus.OK);
        log.debug("Null argument");
        responseEntity = mainService.createCardSet(username, null, null);
        log.info(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

        log.debug("Wrong card is");
        responseEntity = mainService.createCardSet(username, new ArrayList<>(List.of(0L)), "name");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteOwnCardSet(){
        log.info("Test delete cardSet");
        createUser();
        log.debug("Creating card set to user {}", username);
        responseEntity = mainService.createCardSet(username, cardList.stream().map(Card::getId)
                .collect(Collectors.toList()), "name");
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
