package com.example.project.configurations;

import com.example.project.models.UserActivity;
import com.example.project.repo.UserActivityRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogoutListener {

    private final UserActivityRepository userActivityRepository;

    public LogoutListener(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();

        // Retrieve user ID (adapt based on your UserDetails implementation)
        Long userId = getUserIdByUsername(username);

        // Find the latest login activity for the user
        UserActivity activity = userActivityRepository.findTopByUserIdOrderByLoginTimestampDesc(userId);

        if (activity != null) {
            activity.setLogoutTimestamp(LocalDateTime.now());
            userActivityRepository.save(activity);
        }
    }

    private Long getUserIdByUsername(String username) {
        // Logic to fetch user ID based on username from the database
        return 1L; // Example: replace with actual lookup
    }
}