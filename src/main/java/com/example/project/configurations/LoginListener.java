package com.example.project.configurations;

import com.example.project.models.User;
import com.example.project.models.UserActivity;
import com.example.project.repo.UserActivityRepository;
import com.example.project.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoginListener {

    private static final Logger log = LoggerFactory.getLogger(LoginListener.class);
    private final UserActivityRepository userActivityRepository;

    public LoginListener(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }


    private UserRepository userRepository;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();

        log.info(username);
        Long userId = getUserIdByUsername(username);
        User user = (User) authentication.getPrincipal();

        UserActivity activity = new UserActivity();
        activity.setUserId(user.getId());

        activity.setLoginTimestamp(LocalDateTime.now());

        userActivityRepository.save(activity);
    }



    private Long getUserIdByUsername(String username) {
        return 1L; // Example: replace with actual lookup
    }

}