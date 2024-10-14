package com.threeping.mudium.musical.dto;

import com.threeping.mudium.performance.dto.PerformanceDTO;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MusicalTotalDTO {

    private String title;

    private String poster;

    private String scope;

    private String rating;

    private List<PerformanceDTO> performanceList;

//    private List<Review> reviewList; 리뷰가 추가되면 추가할 예정

}
