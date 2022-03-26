package ru.sfedu.voccards.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CardSet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private UserApp creator;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "card_set_card",
            joinColumns = { @JoinColumn(name = "card_set_id") },
            inverseJoinColumns = { @JoinColumn(name = "card_id") }
    )
    private List<Card> cardList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardSet", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public void addCard(Card card){
        if (cardList == null)
            cardList = new ArrayList<>();
        cardList.add(card);
    }

    public void addReview(Review review){
        if (reviews == null)
            reviews = new ArrayList<>();
        review.setCardSet(this);
        reviews.add(review);
    }



}
