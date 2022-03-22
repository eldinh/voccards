package ru.sfedu.voccards.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.dao.CardSetDao;
import ru.sfedu.voccards.dao.RoleDao;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.dto.MessageResponse;
import ru.sfedu.voccards.entity.CardSet;
import ru.sfedu.voccards.entity.ERole;
import ru.sfedu.voccards.entity.UserApp;
import static ru.sfedu.voccards.Constants.*;

import java.util.Optional;

@Service
public class MainServiceImpl implements MainService{

    private static final Logger log = LogManager.getLogger(MainServiceImpl.class.getName());

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

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
                    .body(new MessageResponse(String.format(CARDSET_BY_ID_ERROR, id)));

        return ResponseEntity.ok(cardSet.get());
    }



}
