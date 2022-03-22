package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.voccards.entity.Card;

import java.util.List;


public interface CardDao extends JpaRepository<Card, Long> {

    List<Card> findByRu(String word);

    List<Card> findByEn(String word);

}