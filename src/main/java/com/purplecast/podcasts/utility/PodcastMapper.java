package com.purplecast.podcasts.utility;

import com.purplecast.podcasts.db.entity.Podcast;
import com.purplecast.podcasts.dto.PodcastRequest;
import com.purplecast.podcasts.service.PodcastService;
import com.purplecast.podcasts.utility.exception.PodcastNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PodcastMapper {

    private final PodcastService podcastService;

    public Podcast mapToEntity(PodcastRequest podcastRequest) {
        if(!podcastService.getPodcastsFileNames().contains(podcastRequest.getFileName())){
            throw new PodcastNotFoundException();
        }
        return Podcast.builder()
                .title(podcastRequest.getTitle())
                .description(podcastRequest.getDescription())
                .category(podcastRequest.getCategory())
                .imageUrl(podcastRequest.getImageUrl())
                .price(podcastRequest.getPrice())
                .length(podcastRequest.getLength().toString())
                .audioUrl(podcastRequest.getFileName().replace(".mp3", ""))
                .blocked(!podcastRequest.getPrice().equals(new BigDecimal(0)))
                .build();
    }

}
