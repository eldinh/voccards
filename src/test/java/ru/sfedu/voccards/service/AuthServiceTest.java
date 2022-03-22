package ru.sfedu.voccards.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.sfedu.voccards.BaseTest;
import ru.sfedu.voccards.entity.ERole;
import ru.sfedu.voccards.entity.Role;
import static ru.sfedu.voccards.Constants.*;


public class AuthServiceTest extends BaseTest {

    public static final Logger log = LogManager.getLogger(AuthServiceTest.class.getName());

    @Before
    public void before() {
        cardDao.deleteAll();
        cardSetDao.deleteAll();
        userDao.deleteAll();
        roleDao.deleteAll();
        roleDao.save(new Role(null, ERole.ROLE_USER));
        roleDao.save(new Role(null, ERole.ROLE_TEACHER));
        roleDao.save(new Role(null, ERole.ROLE_VIP));
    }

    @Test
    public void testAuthUser(){
        log.info("Test auth user");
        log.debug("Authentication process");
        responseEntity = authService.authUser(loginRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        log.debug("Getting user from database by username");
        userOptional = userDao.findByUsername(loginRequest.getUsername());
        assert userOptional.isPresent();
    }

    @Test
    public void testFailAuthUser(){
        log.info("Test Fail Auth User");

        log.debug("Authenticate with incorrect username");
        responseEntity = authService.authUser(loginRequest);
        log.info(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testRegister(){
        log.info("Test register");

        log.debug("Register user {}", signupRequest.getUsername());
        responseEntity = authService.register(signupRequest);

        messageResponse.setMessage(USER_CREATED);
        assertEquals(responseEntity, ResponseEntity.ok(messageResponse));
    }

    @Test
    public void testFailRegister(){
        log.info("Test Fail Register");

        log.debug("Register user {}", signupRequest.getUsername());
        responseEntity = authService.register(signupRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        log.debug("Register user with the same username");
        responseEntity = authService.register(signupRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }



}
