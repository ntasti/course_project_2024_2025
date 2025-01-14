package com.example.project.repo;

import com.example.project.models.User;
import com.example.project.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Long> {
    List<WorkSchedule> findByWeekStart(@Param("weekStart") Date weekStart);


    WorkSchedule findByUser(User user);
}
