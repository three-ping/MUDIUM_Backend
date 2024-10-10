package com.threeping.mudium.musical.aggregate;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MusicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musical_id")
    private Long musicalId;

    @Column(name = "musical_name")
    private String title;

    @Column(name = "musical_averagescope")
    private double averageScope;

    @Column(name = "musical_grade")
    private String grade;

    @Column(name = "musical_reviewvideo")
    private String reviewVideo;

    @Column(name = "musical_poster")
    private String poster;

    @Column(name = "musical_viewcount")
    private int viewCount;
}
