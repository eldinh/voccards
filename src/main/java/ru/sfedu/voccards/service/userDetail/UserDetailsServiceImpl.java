package ru.sfedu.voccards.service.userDetail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.entity.UserApp;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserDao userDao;

    // Checks if user exists
    @Override // Authentication and Authorization process
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserApp> user = userDao.findByUsername(username);
        if (user.isEmpty()){
            log.error("User {} wasn't found in the database", username);
            throw new UsernameNotFoundException(String.format("User %s not found in the database", username));
        }
        log.info("User was found in the database: {}", username);

        return UserDetailsImpl.build(user.get());
    }



}
