package com.threeping.mudium.musical.aggregate;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "TBL_MUSICAL_INFO")
public class Musical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musical_info_id")
    private Long musicalId;

    @Column(name = "musical_name")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "review_video")
    private String reviewVideo;

    @Column(name = "image_url")
    private String poster;

    @Column(name = "view_count")
    private int viewCount;
}
