package com.purplecast.podcasts.utility.exception;

public class PodcastIsNotInShoppingCartException extends RuntimeException {
    public PodcastIsNotInShoppingCartException() {
        super("Podcast isn't in shopping cart");
    }
}
