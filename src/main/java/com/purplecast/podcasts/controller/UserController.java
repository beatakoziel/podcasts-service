package com.purplecast.podcasts.controller;

import com.purplecast.podcasts.db.entity.Podcast;
import com.purplecast.podcasts.db.entity.User;
import com.purplecast.podcasts.db.entity.UserPodcast;
import com.purplecast.podcasts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<User> getUser(Authentication authentication) {
        return ResponseEntity.ok(
                userService.getUser(getUsernameFromAuthentication(authentication)));
    }

    private String getUsernameFromAuthentication(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getUsername();
    }

    @PutMapping("/favourites/{podcastId}")
    public ResponseEntity<Void> toggleFavoritePodcast(Authentication authentication,
                                                      @PathVariable Long podcastId) {
        userService.toggleFavouritePodcast(getUsernameFromAuthentication(authentication), podcastId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/favourites")
    public ResponseEntity<List<Podcast>> getFavouritePodcasts(Authentication authentication) {
        return ResponseEntity.ok(userService.getUsersFavouritePodcasts(getUsernameFromAuthentication(authentication)));
    }

    @GetMapping("/podcasts")
    public ResponseEntity<List<UserPodcast>> getPodcasts(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserPodcasts(getUsernameFromAuthentication(authentication)));
    }

    @GetMapping("/shopping-cart")
    public ResponseEntity<List<Podcast>> getShoppingCart(Authentication authentication) {
        return ResponseEntity.ok(userService.getShoppingCart(getUsernameFromAuthentication(authentication)));
    }

    @PutMapping("/shopping-cart/{podcastId}")
    public ResponseEntity<Void> toggleShoppingCartPodcast(Authentication authentication,
                                                          @PathVariable Long podcastId) {
        userService.toggleShoppingCartPodcast(getUsernameFromAuthentication(authentication), podcastId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/shopping-cart/{podcastId}")
    public ResponseEntity<Void> deletePodcastFromShoppingCart(Authentication authentication,
                                                              @PathVariable Long podcastId) {
        userService.deletePodastFromShoppingCart(getUsernameFromAuthentication(authentication), podcastId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/shopping-cart/approve")
    public ResponseEntity<Void> approvePayment(Authentication authentication) {
        userService.approvePayment(getUsernameFromAuthentication(authentication));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
