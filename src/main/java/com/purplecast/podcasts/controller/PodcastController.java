package com.purplecast.podcasts.controller;

import com.purplecast.podcasts.db.entity.Podcast;
import com.purplecast.podcasts.dto.PodcastRequest;
import com.purplecast.podcasts.service.PodcastService;
import com.purplecast.podcasts.utility.PodcastMapper;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/podcasts")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081",
        "http://localhost:8082", "http://localhost:8083"})
public class PodcastController {

    private final PodcastService podcastService;
    private final PodcastMapper podcastMapper;

    @GetMapping
    public ResponseEntity<List<Podcast>> getPodcasts() {
        return ResponseEntity.ok(podcastService.getPodcasts());
    }

    @GetMapping("/{podcastId}")
    public ResponseEntity<PodcastRequest> getPodcastById(@PathVariable Long podcastId) {
        return ResponseEntity.ok(podcastMapper.mapToRequest(podcastService.getPodcastById(podcastId)));
    }

    @PostMapping
    public ResponseEntity<Void> addPodcast(@RequestBody PodcastRequest podcastRequest) {
        podcastService.addPodcast(podcastMapper.mapToEntity(podcastRequest));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{podcastId}")
    public ResponseEntity<Void> updatePodcast(@PathVariable Long podcastId,
                                              @RequestBody PodcastRequest podcastRequest) {
        podcastService.updatePodcast(podcastId, podcastRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("visibility/{podcastId}")
    public ResponseEntity<Void> updatePodcast(@PathVariable Long podcastId) {
        podcastService.changeVisibility(podcastId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/audio")
    public ResponseEntity<Void> uploadAudioFile(@RequestBody MultipartFile audioFile) {
        podcastService.uploadFile(audioFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getPodcastsFileNames() {
        return ResponseEntity.ok(podcastService.getPodcastsFileNames());
    }

    @GetMapping("/play/{name}")
    public ResponseEntity<ResourceRegion> getVideo(@PathVariable String name, @RequestHeader HttpHeaders headers) throws IOException {
        val video = new UrlResource("file:src/main/resources/podcasts/" + name + ".mp3");
        val region = podcastService.resourceRegion(video, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }
}
