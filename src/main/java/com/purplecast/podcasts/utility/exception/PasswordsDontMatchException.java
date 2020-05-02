package com.purplecast.podcasts.utility.exception;

public class PasswordsDontMatchException extends RuntimeException {
    public PasswordsDontMatchException() {
        super("Password and repeat password don't match");
    }
}
