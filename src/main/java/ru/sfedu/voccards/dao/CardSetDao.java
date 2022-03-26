package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.voccards.entity.CardSet;


public interface CardSetDao extends JpaRepository<CardSet, Long> {

}
