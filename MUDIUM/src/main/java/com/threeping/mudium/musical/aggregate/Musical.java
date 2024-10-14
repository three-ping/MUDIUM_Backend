package com.threeping.mudium.musical.aggregate;

import com.threeping.mudium.bookmark.entity.Bookmark;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "review_video")
    private String reviewVideo;

    @Column(name = "image_url")
    private String poster;

    @Column(name = "view_count")
    private int viewCount;

    // 북마크 N:1 연관관계
    @OneToMany(mappedBy = "musicalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<> ();
}
