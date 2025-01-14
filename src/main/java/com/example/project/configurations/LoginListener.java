package com.example.project.configurations;

import com.example.project.models.UserActivity;
import com.example.project.repo.UserActivityRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoginListener {

    private final UserActivityRepository userActivityRepository;

    public LoginListener(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName(); // Assuming username is the unique identifier

        // Retrieve user ID (adapt based on your UserDetails implementation)
        Long userId = getUserIdByUsername(username);

        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setLoginTimestamp(LocalDateTime.now());

        userActivityRepository.save(activity);
    }

    private Long getUserIdByUsername(String username) {
        // Logic to fetch user ID based on username from the database
        return 1L; // Example: replace with actual lookup
    }

}