package ru.sfedu.voccards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.voccards.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

}
