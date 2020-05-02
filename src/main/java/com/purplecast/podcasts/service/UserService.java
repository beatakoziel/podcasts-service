package com.purplecast.podcasts.service;

import com.purplecast.podcasts.db.entity.Podcast;
import com.purplecast.podcasts.db.entity.User;
import com.purplecast.podcasts.db.entity.UserPodcast;
import com.purplecast.podcasts.db.repository.PodcastRepository;
import com.purplecast.podcasts.db.repository.UserPodcastRepository;
import com.purplecast.podcasts.db.repository.UserRepository;
import com.purplecast.podcasts.dto.RegisterRequest;
import com.purplecast.podcasts.utility.UserMapper;
import com.purplecast.podcasts.utility.UserNotFoundException;
import com.purplecast.podcasts.utility.exception.PasswordsDontMatchException;
import com.purplecast.podcasts.utility.exception.PodcastIsAlreadyFreeException;
import com.purplecast.podcasts.utility.exception.PodcastIsNotInShoppingCartException;
import com.purplecast.podcasts.utility.exception.PodcastNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserPodcastRepository userPodcastRepository;
    private final PodcastRepository podcastRepository;

    public void registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword()))
            throw new PasswordsDontMatchException();
        userRepository.save(userMapper.mapToEntity(registerRequest)).getId();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }


    @Override
    public UserDetails loadUserByUsername(String s) {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UserNotFoundException(s));
    }

    public List<UserPodcast> getUserPodcasts(String username) {
        return getUser(username).getUserPodcasts();
    }


    public void toggleFavouritePodcast(String username, Long podcastId) {
        UserPodcast userPodcast = getUser(username).getUserPodcasts().stream()
                .filter(usersPodcast -> usersPodcast.getPodcast().getId().equals(podcastId))
                .findFirst()
                .orElseThrow(PodcastNotFoundException::new);
        userPodcast.setFavourite(!userPodcast.isFavourite());
        userPodcastRepository.save(userPodcast);
    }

    public List<Podcast> getUsersFavouritePodcasts(String username) {
        return getUser(username).getUserPodcasts().stream()
                .filter(UserPodcast::isFavourite)
                .map(UserPodcast::getPodcast)
                .collect(Collectors.toList());
    }

    public void toggleShoppingCartPodcast(String username, Long podcastId) {
        Podcast podcast = podcastRepository.findById(podcastId)
                .orElseThrow(PodcastNotFoundException::new);
        User user = getUser(username);
        Long count = user.getUserPodcasts().stream()
                .filter(userPodcast -> userPodcast.getId().equals(podcastId))
                .filter(userPodcast -> !userPodcast.isBlocked())
                .count();
        if (count != 0)
            throw new PodcastIsAlreadyFreeException();
        if (user.getShoppingCart().contains(podcast))
            user.getShoppingCart().remove(podcast);
        else
            user.getShoppingCart().add(podcast);
        userRepository.save(user);
    }

    public void deletePodastFromShoppingCart(String username, Long podcastId) {
        Podcast podcast = podcastRepository.findById(podcastId)
                .orElseThrow(PodcastNotFoundException::new);
        User user = getUser(username);
        if (user.getShoppingCart().contains(podcast))
            user.getShoppingCart().remove(podcast);
        else
            throw new PodcastIsNotInShoppingCartException();
        userRepository.save(user);
    }

    public List<Podcast> getShoppingCart(String username) {
        return getUser(username).getShoppingCart();
    }

    public void approvePayment(String username) {
        User user = getUser(username);
        unblockPurchasedPodcasts(user);
        user.getShoppingCart().clear();
    }

    private void unblockPurchasedPodcasts(User user) {
        List<Long> purchasedPodcastsIds = user.getShoppingCart().stream()
                .map(Podcast::getId)
                .collect(Collectors.toList());
        List<UserPodcast> userPodcastsToUnlock = user.getUserPodcasts().stream()
                .filter(userPodcast -> purchasedPodcastsIds.contains(userPodcast.getId()))
                .collect(Collectors.toList());
        userPodcastsToUnlock.forEach(userPodcast -> {
            userPodcast.setBlocked(false);
            userPodcastRepository.save(userPodcast);
        });
    }
}
