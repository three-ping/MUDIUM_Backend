package com.threeping.mudium.performance.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PerformanceDTO {

    private String theater;

    private String region;

    private Timestamp startDate;

    private Timestamp endDate;

}
