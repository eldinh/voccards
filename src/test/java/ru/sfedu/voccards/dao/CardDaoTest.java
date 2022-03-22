package ru.sfedu.voccards.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.BaseTest;
import ru.sfedu.voccards.entity.Card;

import java.util.List;


public class CardDaoTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(CardDaoTest.class.getName());

    @Before
    public void before() {
        log.debug("Before");
        card.setId(null);
        cardSetDao.deleteAll();
        cardDao.deleteAll();
    }

    @Test
    public void testSaveCard(){
        log.info("Test save card");
        log.debug("Saving card");
        cardDao.save(card);
        List<Card> cardList = cardDao.findAll();
        log.debug("Getting all cards");
        assert !cardList.isEmpty();
        assert cardList.size() == 1;
    }

    @Test
    public void testSaveCardFail(){
        log.info("TestFail saveCard");
        log.debug("Saving 2 same cards");
        cardDao.save(card);
        cardDao.save(card);
        List<Card> cardList = cardDao.findAll();
        log.debug("Getting all cards");
        assertFalse(cardList.size() == 2);
    }

    @Test
    public void testGetCards(){
        log.info("Test get cards");
        cardDao.saveAll(cardList);
        log.debug("Getting all cards");
        List<Card> cards = cardDao.findAll();
        log.debug("Size: {}", cardList.size());
        assert cardList.size() == cards.size() ;
    }

    @Test
    public void testGetCardById(){
        log.info("Test get card by id");
        cardDao.save(card);
        log.debug("Getting card by id");
        cardOptional = cardDao.findById(card.getId());
        assert cardOptional.isPresent();
        assertEquals(card, cardOptional.get());
    }

    @Test
    public void testGetCardByIdFail(){
        log.info("Test Fail get card by id");
        log.debug("Getting card by id");
        try {
            cardOptional = cardDao.findById(null);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        cardOptional = cardDao.findById(3128312973021L);
        assert cardOptional.isEmpty();
    }



    @Test
    public void testGetCardByEn(){
        log.info("Test get card by En");
        cardDao.save(card);
        log.debug("Getting card by En");
        cardOptional = cardDao.findByEn(card.getEn()).stream().findFirst();
        assert cardOptional.isPresent();
        assertEquals(card.getEn(), cardOptional.get().getEn());
        assertEquals(card.getRu(), cardOptional.get().getRu());
    }

    @Test
    public void testGetCardByEnFail(){
        log.info("Test Fail get card by En");
        cardDao.save(card);
        log.debug("Getting card by wrong en");
        cardOptional = cardDao.findByEn(card.getEn() + "random").stream().findFirst();
        assert cardOptional.isEmpty();
    }

    @Test
    public void testGetCardByRu(){
        log.info("Test get card by Ru");
        cardDao.save(card);
        log.debug("Getting card by Ru");
        cardOptional = cardDao.findByRu(card.getRu()).stream().findFirst();
        assert cardOptional.isPresent();
        assertEquals(card.getEn(), cardOptional.get().getEn());
        assertEquals(card.getRu(), cardOptional.get().getRu());
    }

    @Test
    public void testGetCardByRuFail(){
        log.info("Test Fail get card by Ru");
        cardDao.save(card);
        log.debug("Getting card by Ru");
        cardOptional = cardDao.findByRu(card.getRu() + "random").stream().findFirst();
        assert cardOptional.isEmpty();
    }

}
