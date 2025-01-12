package com.example.project.repo;

import com.example.project.models.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week,Long> {
}
