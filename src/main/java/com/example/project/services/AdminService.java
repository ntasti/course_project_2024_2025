package com.example.project.services;

import com.example.project.models.Task;
import com.example.project.models.TasksUser;
import com.example.project.models.WorkSchedule;
import com.example.project.repo.TaskRepository;
import com.example.project.repo.TasksUserRepository;
import com.example.project.repo.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final TasksUserRepository tasksUserRepository;
    public void newUserTask(TasksUser tasksUser){
        log.info("Create new user tasks {} ",tasksUser);
        tasksUserRepository.save(tasksUser);
    }

    private final WorkScheduleRepository workScheduleRepository;

    public void saveSchedule(WorkSchedule workSchedule) {
        log.info("Saving schedule: {}", workSchedule);
        workScheduleRepository.save(workSchedule);
    }

    private final TaskRepository taskRepository;
    public void createSession(Task task){
        log.info("Create new Task {} ",task);

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {

        log.info("Delete Task");
        taskRepository.deleteById(id);
    }

}
