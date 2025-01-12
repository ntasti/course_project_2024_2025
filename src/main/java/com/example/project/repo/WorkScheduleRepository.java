package com.example.project.repo;

import com.example.project.models.User;
import com.example.project.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Long> {
    WorkSchedule findByUser(User user);
}
