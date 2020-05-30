package com.purplecast.podcasts.utility;

import com.purplecast.podcasts.db.entity.User;
import com.purplecast.podcasts.db.entity.UserPodcast;
import com.purplecast.podcasts.db.enums.UserRole;
import com.purplecast.podcasts.db.repository.PodcastRepository;
import com.purplecast.podcasts.db.repository.UserPodcastRepository;
import com.purplecast.podcasts.db.repository.UserRepository;
import com.purplecast.podcasts.dto.RegisterRequest;
import com.purplecast.podcasts.utility.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserMapper {

    private final PodcastRepository podcastRepository;
    private final UserPodcastRepository userPodcastRepository;
    private final UserRepository userRepository;

    public User mapToEntity(RegisterRequest in) {
        userRepository.findByUsername(in.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(in.getUsername());
                });

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<UserPodcast> userPodcastList = new ArrayList<>();
        podcastRepository.findAll().forEach(podcast -> {
                    UserPodcast userPodcast = userPodcastRepository.save(
                            UserPodcast.builder()
                                    .podcast(podcast)
                                    .blocked(podcast.isBlocked())
                                    .favourite(false)
                                    .inCart(false)
                                    .build()
                    );
                    userPodcastList.add(userPodcast);
                }
        );
        return User.builder()
                .username(in.getUsername())
                .password(passwordEncoder.encode(in.getPassword()))
                .userRole(UserRole.USER)
                .shoppingCart(new ArrayList<>())
                .userPodcasts(userPodcastList)
                .build();
    }
}
