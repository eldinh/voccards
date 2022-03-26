package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sfedu.voccards.entity.Card;

import java.util.List;


public interface CardDao extends JpaRepository<Card, Long> {

    List<Card> findByRu(String word);

    List<Card> findByEn(String word);

    @Query("select c from Card c where c.en like ?1%")
    List<Card> findByPartEn(String word);

    @Query("select c from Card c where c.ru like ?1%")
    List<Card> findByPartRu(String word);

}