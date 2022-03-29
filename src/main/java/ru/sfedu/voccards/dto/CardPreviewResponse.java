package ru.sfedu.voccards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPreviewResponse {

    private Long id;

    private String author;

    private String cardName;

    private Integer count;
}
