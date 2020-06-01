package com.purplecast.podcasts.service;

import com.purplecast.podcasts.db.entity.Podcast;
import com.purplecast.podcasts.db.entity.User;
import com.purplecast.podcasts.db.entity.UserPodcast;
import com.purplecast.podcasts.db.repository.PodcastRepository;
import com.purplecast.podcasts.db.repository.UserPodcastRepository;
import com.purplecast.podcasts.db.repository.UserRepository;
import com.purplecast.podcasts.dto.PodcastRequest;
import com.purplecast.podcasts.utility.exception.PodcastNotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
@AllArgsConstructor
public class PodcastService {

    private final PodcastRepository podcastRepository;
    private final UserRepository userRepository;
    private final UserPodcastRepository userPodcastRepository;

    public List<Podcast> getPodcasts() {
        return podcastRepository.findAll();
    }

    public List<String> getPodcastsFileNames() {
        File folder = new File("src/main/resources/podcasts");
        File[] listOfFiles = folder.listFiles();
        return Arrays.stream(listOfFiles)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public void addPodcast(Podcast podcast) {
        podcastRepository.save(podcast);
        UserPodcast userPodcast = UserPodcast.builder()
                .podcast(podcast)
                .blocked(podcast.isBlocked())
                .favourite(false)
                .inCart(false)
                .build();
        userPodcastRepository.save(userPodcast);
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> user.getUserPodcasts().add(userPodcast));
        userList.forEach(userRepository::save);
    }

    public Podcast getPodcastById(Long podcastId) {
        return podcastRepository.findById(podcastId)
                .orElseThrow(PodcastNotFoundException::new);
    }

    public void changeVisibility(Long podcastId) {
        Podcast podcast = getPodcastById(podcastId);
        podcast.setVisible(!podcast.isVisible());
        podcastRepository.save(podcast);
    }

    public void updatePodcast(Long podcastId, PodcastRequest podcastRequest) {
        Podcast podcast = podcastRepository.findById(podcastId)
                .orElseThrow(PodcastNotFoundException::new);
        podcast.setTitle(podcastRequest.getTitle());
        podcast.setDescription(podcastRequest.getDescription());
        podcast.setCategory(podcastRequest.getCategory());
        podcast.setPrice(podcastRequest.getPrice());
        podcast.setImageUrl(podcastRequest.getImageUrl());
        podcast.setLength(podcastRequest.getLength().toString());
        podcast.setAudioUrl(podcastRequest.getFileName().replace(".mp3", ""));
        podcast.setBlocked(!podcastRequest.getPrice().equals(new BigDecimal(0)));
        podcastRepository.save(podcast);
    }

    public void uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        String fileName = "";
        try {
            fileName = file.getOriginalFilename();
            if (!fileName.contains(".mp3"))
                throw new RuntimeException("Wrong file extension");
            System.out.println(fileName);
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get("src/main/resources/podcasts/" + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            String msg = String.format("Failed to store file %f", file.getName());
            throw new RuntimeException(msg, e);
        }
    }

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
