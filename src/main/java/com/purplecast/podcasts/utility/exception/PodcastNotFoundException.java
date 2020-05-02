package com.purplecast.podcasts.utility.exception;

public class PodcastNotFoundException extends RuntimeException {
    public PodcastNotFoundException() {
        super("Podcast not found");
    }
}
