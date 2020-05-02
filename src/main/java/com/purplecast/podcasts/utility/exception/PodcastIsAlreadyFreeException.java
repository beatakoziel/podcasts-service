package com.purplecast.podcasts.utility.exception;

public class PodcastIsAlreadyFreeException extends RuntimeException {
    public PodcastIsAlreadyFreeException() {
        super("This podcast is already free, cannot put it into shopping cart");
    }
}
