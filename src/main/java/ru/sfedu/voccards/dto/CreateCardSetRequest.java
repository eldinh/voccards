package ru.sfedu.voccards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sfedu.voccards.entity.Card;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardSetRequest {

    private String name;

    private List<Card> cardList;
}

