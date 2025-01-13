package com.example.project.repo;

import com.example.project.models.User;
import com.example.project.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Long> {
//    @Query(ALTER TABLE `work_schedule`
//            CHANGE `week_end` `week_end` DATE NOT NULL,
//            CHANGE `week_start` `week_start` DATE NOT NULL;)

    WorkSchedule findByUser(User user);
}
