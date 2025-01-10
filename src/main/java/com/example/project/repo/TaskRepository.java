package com.example.project.repo;

import com.example.project.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByProjectIdOrderByDateOfCreatedAsc(Long projectId);
}
