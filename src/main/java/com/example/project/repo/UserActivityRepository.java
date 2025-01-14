package com.example.project.repo;

import com.example.project.models.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    UserActivity findTopByUserIdOrderByLoginTimestampDesc(Long userId);
}