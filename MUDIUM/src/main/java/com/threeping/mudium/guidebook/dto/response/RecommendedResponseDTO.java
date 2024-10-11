package com.threeping.mudium.guidebook.dto.response;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class RecommendedResponseDTO {

    private Long recommendedId;
    private String musicalTitle;
    private String musicalDescription;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
