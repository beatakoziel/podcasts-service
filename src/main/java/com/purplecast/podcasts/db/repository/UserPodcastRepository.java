package com.purplecast.podcasts.db.repository;

import com.purplecast.podcasts.db.entity.UserPodcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPodcastRepository extends JpaRepository<UserPodcast, Long> {
}
