package com.purplecast.podcasts.db.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String audioUrl;

    @Column(nullable = false)
    private boolean blocked;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable = false)
    private String length;

    @Column(nullable = false)
    private boolean visible;

}
