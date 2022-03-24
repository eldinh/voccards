package ru.sfedu.voccards.service;


import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.BaseTest;
import ru.sfedu.voccards.entity.Card;
import ru.sfedu.voccards.entity.UserApp;

import java.util.List;
import java.util.stream.Collectors;

public class MainServiceTest extends BaseTest {


    @Test
    public void testCreateCardSet(){
        log.info("Test Create CardSet");
        log.debug("Register user {}", username);
        assertEquals(authService.register(signupRequest).getStatusCode(), HttpStatus.OK);
        userOptional = userDao.findByUsername(username);
        assert userOptional.isPresent();

        log.debug("Creating card set for user {}", username);
        responseEntity = mainService.createCardSet(username, cardList.stream().map(Card::getId).collect(Collectors.toList()));

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

}
