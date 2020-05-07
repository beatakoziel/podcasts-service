package com.purplecast.podcasts.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPodcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Podcast podcast;

    @Column(nullable = false)
    private boolean blocked;

    @Column(nullable = false)
    private boolean favourite;

    @Column(nullable = false)
    private boolean inCart;

}
