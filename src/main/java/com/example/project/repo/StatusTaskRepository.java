package com.example.project.repo;

import com.example.project.models.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTaskRepository extends JpaRepository<StatusTask,Long> {
}
