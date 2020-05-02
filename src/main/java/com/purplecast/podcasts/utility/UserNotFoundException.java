package com.purplecast.podcasts.utility;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id %s not found", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with username %s not found", email));
    }
}
