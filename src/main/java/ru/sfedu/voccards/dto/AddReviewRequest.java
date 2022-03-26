package ru.sfedu.voccards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequest {

    private String review;

    private Long CardSetId;
}
