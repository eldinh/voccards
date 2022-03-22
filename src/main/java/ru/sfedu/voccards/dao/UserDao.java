package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.voccards.entity.UserApp;

import java.util.Optional;


public interface UserDao extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByUsername(String username);
    Optional<UserApp> findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
