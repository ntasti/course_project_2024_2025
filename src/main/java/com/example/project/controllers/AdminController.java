package com.example.project.controllers;

import com.example.project.models.*;
import com.example.project.repo.*;
import com.example.project.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public ProjectRepository projectRepository;
    @Autowired
    public UserRepository userRepository;

    @PostMapping("/usertasks/create")
    public String newUserTask(@RequestParam Long projectId, @RequestParam Long taskId,
                              @RequestParam Long userId, @RequestParam String assignmentDate, @RequestParam String dateOfTheEnd) throws ParseException {

        // Получаем сущности по идентификаторам
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        // Парсинг дат
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedAssignmentDate = dateFormat.parse(assignmentDate);
        Date parsedDateOfTheEnd = dateFormat.parse(dateOfTheEnd);

        // Создаем объект TasksUser
        TasksUser tasksUser = new TasksUser();
        tasksUser.setTask(task);
        tasksUser.setUser(user);
        tasksUser.setAssignmentDate(parsedAssignmentDate);
        tasksUser.setDateOfTheEnd(parsedDateOfTheEnd);

        // Сохраняем в базу данных
        adminService.newUserTask(tasksUser);

        return "redirect:/calendar";
    }


    @GetMapping("/usertasks/create")
    public String newUserTask(Model model) {
        // Добавляем данные в модель для формы
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        Iterable<Project> projects = projectRepository.findAll();
        model.addAttribute("project", projects);

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        // Возвращаем имя шаблона
        return "usertasks-create";
    }
    @GetMapping("/tasks/by-project")
    @ResponseBody
    public List<Task> getTasksByProject(@RequestParam Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Autowired
    private DayTypeRepository dayTypeRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @GetMapping("/task/create")
    public String newTask(Model model) {

        Iterable<Project> projectsData = projectRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("project", projectsData);

        return "task-create";

    }

    @PostMapping("/task/create")
    public String TaskCreate(Task task) {
        adminService.createSession(task);
        return "redirect:/task";
    }


}
