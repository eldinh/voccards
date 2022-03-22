package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.voccards.entity.ERole;
import ru.sfedu.voccards.entity.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(ERole name);

    Boolean existsByName(ERole name);
}
