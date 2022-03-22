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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.REMOVE})
    @JoinTable(
            name = "card_set_card",
            joinColumns = { @JoinColumn(name = "card_set_id") },
            inverseJoinColumns = { @JoinColumn(name = "card_id") }
    )
    private List<Card> cardList;

    public void addCard(Card card){
        if (cardList == null)
            cardList = new ArrayList<>();
        cardList.add(card);

    }

//    @ManyToMany
//    private List<Customer> visitors;

}
