package com.threeping.mudium.musical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MusicalDTO {

    private String title;

    private String rating;

    private String poster;

    private String production;

    private Long viewCount;

    private String synopsys;

    private String reviewVideos;
}
