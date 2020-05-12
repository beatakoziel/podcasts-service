package com.purplecast.podcasts.controller;

import com.purplecast.podcasts.service.PodcastService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/podcasts")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081",
        "http://localhost:8082", "http://localhost:8083"})
public class PodcastController {

    private final PodcastService videoService;

    @GetMapping("/play/{name}")
    public ResponseEntity<ResourceRegion> getVideo(@PathVariable String name, @RequestHeader HttpHeaders headers) throws IOException {
        val video = new UrlResource("file:src/main/resources/podcasts/" + name + ".mp3");
        val region = videoService.resourceRegion(video, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }
}