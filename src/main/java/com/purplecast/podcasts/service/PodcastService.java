package com.purplecast.podcasts.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Math.min;

@Service
@AllArgsConstructor
public class PodcastService {

    public ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
        val contentLength = video.contentLength();
        val range = headers.getRange().get(0);
        if (range != null) {
            val start = range.getRangeStart(contentLength);
            val end = range.getRangeEnd(contentLength);
            val rangeLength = min(1024 * 1024, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            val rangeLength = min(1024 * 1024, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
