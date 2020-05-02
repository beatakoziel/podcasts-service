package com.purplecast.podcasts.service;

import com.purplecast.podcasts.db.repository.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PodcastService {

    private final PodcastRepository podcastRepository;
}
