package com.example.project.repo;

import com.example.project.models.TasksUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasksUserRepository extends JpaRepository<TasksUser,Long> {
    List<TasksUser> findAllTasksByUserId(@Param("user_id") Long user);


}

