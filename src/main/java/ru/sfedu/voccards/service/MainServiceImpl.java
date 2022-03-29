package ru.sfedu.voccards.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.dao.CardDao;
import ru.sfedu.voccards.dao.CardSetDao;
import ru.sfedu.voccards.dao.RoleDao;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.dto.CardPreviewResponse;
import ru.sfedu.voccards.dto.MessageResponse;
import ru.sfedu.voccards.entity.Card;
import ru.sfedu.voccards.entity.CardSet;
import ru.sfedu.voccards.entity.ERole;
import ru.sfedu.voccards.entity.UserApp;
import static ru.sfedu.voccards.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class  MainServiceImpl implements MainService{

    private static final Logger log = LogManager.getLogger(MainServiceImpl.class.getName());

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CardSetDao cardSetDao;


    @Override
    @Transactional
    public ResponseEntity<?> addRoleToUser(String username, String role) {
        log.info("Starting MainServiceImpl addRoleToUser[0]");
        log.info("addRoleToUser[1]: adding role {} to user {}", role, username);
        try {
            log.debug("addRoleToUser[2]: Finding user by username {}", username);
            Optional<UserApp> optionalUserApp = userDao.findByUsername(username);
            if (optionalUserApp.isEmpty())
                throw new Exception(String.format("User %s wasn't found", username));
            UserApp user = optionalUserApp.get();
            log.debug("addRoleToUser[3]: Adding role {} to user {}", role, username);
            switch (role){
                case "vip":
                    user.getRoles().add(roleDao.findByName(ERole.ROLE_VIP));
                    break;
                case "teacher":
                    user.getRoles().add(roleDao.findByName(ERole.ROLE_TEACHER));
                    break;
                default:
                    throw new Exception(String.format("Role %s wasn't found", role));
            }
            return ResponseEntity.ok(new MessageResponse(ROLE_ADDED));

        }catch (Exception e){
            log.error("Function MainServiceImpl addRoleToUser had failed[4]: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    public ResponseEntity<?> getCardSetById(Long id){
        log.info("Starting MainServiceImpl getCardSetById[0]");
        log.info("getCardSetById[1]: Getting cardSet by id {}", id);

        Optional<CardSet> cardSet = cardSetDao.findById(id);

        if (cardSet.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format(CARDSET_NOT_EXIST, id)));

        return ResponseEntity.ok(cardSet.get());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createCardSet(String username, List<Long> idCardLost, String name) {
        log.info("Starting MainServiceImpl createCardSet[0]");
        try {
            log.info("createCardSet[1]: username - {}, cardList - {}", username, idCardLost);
            log.debug("createCardSet[2]: Getting user by username {}", username);
            Optional<UserApp> user = userDao.findByUsername(username);
            if (user.isEmpty())
                throw new Exception(AUTHENTICATION_TOKEN_ERROR);
            log.debug("createCardSet[3]: Creating cardSet");
            CardSet cardSet = new CardSet();
            for (Long id: idCardLost){
                Optional<Card> card = cardDao.findById(id);
                if (card.isEmpty())
                    throw new Exception(String.format(CARD_NOT_EXIST_BY_ID, id));
                cardSet.addCard(card.get());
            }
            cardSet.setName(name);
            log.debug("createCardSet[4]: Adding cardSet to user");
            user.get().addOwnCardSet(cardSet);
            return ResponseEntity.ok().body(new MessageResponse(String.format(CARDSET_ADDED, username)));
        }catch (Exception e){
            log.error("Function MainServiceImpl createCardSet had failed[5]: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getOwnCardSetList(String username) {
        log.info("Starting MainServiceImpl getCardSetList[0]");
        try {
            log.info("getCardSetList[1]: username - {}", username);
            log.debug("getCardSetList[2]: Getting user from db by username {}", username);
            Optional<UserApp> user = userDao.findByUsername(username);
            if (user.isEmpty())
                throw new Exception(AUTHENTICATION_TOKEN_ERROR);
            return ResponseEntity.ok(user.get().getOwnSets());
        }catch (Exception e){
            log.error("Function MainServiceImpl getCardSetList had failed[3]: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> findCardByEn(String en) {
        log.info("Starting MainServiceImpl findCardByEn[0]");
        try {
            log.info("findCardByEn[1]: en - {}", en);
            log.debug("findCardByEn[2]: Getting cards from database");
            List<Card> cardList = cardDao.findByPartEn(en);
            if (cardList == null || cardList.isEmpty())
                throw new Exception(String.format(CARD_NOT_EXIST, en));
            return ResponseEntity.ok(cardList);
        }catch (Exception e){
            log.error("Function MainServiceImpl findCardByEn had failed[3]: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }


    }

    @Override
    public ResponseEntity<?> findCardByRu(String ru) {
        log.info("Starting MainServiceImpl findCardByRu[0]");
        try {
            log.info("findCardByRu[1]: ru - {}", ru);
            log.debug("findCardByRu[2]: Getting cards from database");
            List<Card> cardList = cardDao.findByPartRu(ru);
            if (cardList == null || cardList.isEmpty())
                throw new Exception(String.format(CARD_NOT_EXIST, ru));
            return ResponseEntity.ok(cardList);
        }catch (Exception e){
            log.error("Function MainServiceImpl findCardByRu had failed[3]: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteOwnCardSet(String username, Long id) {
        log.info("Starting MainServiceImpl deleteOwnCardSet[0]");
        try {
            log.info("deleteOwnCardSet[1]: username - {}, cardSetId - {}", username, id);
            log.debug("deleteOwnCardSet[2]: Getting user by username {}", username);
            Optional<UserApp> user = userDao.findByUsername(username);
            if (user.isEmpty())
                throw new Exception(AUTHENTICATION_TOKEN_ERROR);
            Optional<CardSet> cardSet = cardSetDao.findById(id);
            if (cardSet.isEmpty() || !Objects.equals(cardSet.get().getCreator().getUsername(), username))
                throw new Exception(String.format(CARDSET_NOT_EXIST, id));
            log.debug("deleteOwnCardSet[3]: Deleting cardSet {}", cardSet.get().getId());
            cardSetDao.delete(cardSet.get());
            return ResponseEntity.ok(new MessageResponse(CARDSET_REMOVED));
        }catch (Exception e){
            log.error("Function MainServiceImpl deleteOwnCardSet had failed[]: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getPreview(String username) {
        log.info("Starting MainServiceImpl getPreview[0]");
        try {
            log.info("getPreview[1]: username - {}", username);
            log.debug("getPreview[2]: Getting user from db by username {}", username);
            Optional<UserApp> user = userDao.findByUsername(username);
            if (user.isEmpty())
                throw new Exception(AUTHENTICATION_TOKEN_ERROR);
            List<CardPreviewResponse> responses = new ArrayList<>();
            log.debug("getPreview[3]: Getting information from cardSets");
            for (CardSet cardSet: user.get().getOwnSets())
                responses.add(new CardPreviewResponse(cardSet.getId() ,cardSet.getCreator().getUsername(),
                        cardSet.getName(), cardSet.getCardList().size()));

            return ResponseEntity.ok(responses);
        }catch (Exception e){
            log.error("Function MainServiceImpl getPreview had failed[4]: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


}
