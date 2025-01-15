package com.example.project.controllers;

import com.example.project.models.*;
import com.example.project.repo.*;
import com.example.project.services.AdminService;
import com.example.project.services.UserService;
import com.example.project.services.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


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
        adminService.createTask(task);
        return "redirect:/task";
    }


    @Autowired
    public TasksUserRepository tasksUserRepository;

    @GetMapping("/data/reports")
    public String ReportCreate(Model model) {
        Iterable<TasksUser> tasksUsers = tasksUserRepository.findAll();
        model.addAttribute("tasksUser", tasksUsers);
        return "data-reports";
    }


    @Autowired
    UserActivityRepository userActivityRepository;

    @GetMapping("/session")
    public String Session(Model model) {

        Iterable<UserActivity> usersActivity = userActivityRepository.findAll();
        List<Map<String, Object>> userActivityWithDuration = new ArrayList<>();

        for (UserActivity activity : usersActivity) {
            Map<String, Object> activityData = new HashMap<>();
            activityData.put("id", activity.getId());
            activityData.put("name", activity.getName());
            activityData.put("loginTimestamp", activity.getLoginTimestamp());
            activityData.put("logoutTimestamp", activity.getLogoutTimestamp());

            if (activity.getLoginTimestamp() != null && activity.getLogoutTimestamp() != null) {
                long duration = java.time.Duration
                        .between(activity.getLoginTimestamp(), activity.getLogoutTimestamp())
                        .toMinutes();
                activityData.put("duration", (duration / 60)); // Время в минутах
            } else {
                activityData.put("duration", "В системе"); // Если время выхода отсутствует
            }

            userActivityWithDuration.add(activityData);
        }
        model.addAttribute("userActivity", userActivityWithDuration); // Передача данных в шаблон

        return "user-session";
    }

    @GetMapping("/delete")
    public String deleteInf(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "delete-task";
    }


    @GetMapping("/tasks/by-user/{userId}")
    @ResponseBody
    public List<TasksUser> getTasksByUser(@PathVariable Long userId) {
        return tasksUserRepository.findAllTasksByUserId(userId); // Метод в репозитории
    }

    @PostMapping("/delete")
    public String deleteData(@RequestParam Long id) {
        adminService.deleteUserTask(id);
        return "redirect:/task";
    }


    //добавление информации
    @GetMapping("/project/create")
    public String newProjectsCreate(Model model) {
        return "project-create";
    }


    @PostMapping("/project/create")
    public String newProjects(@RequestParam String name, @RequestParam String inf, @RequestParam String customerName, @RequestParam String customerEmail, Model model) {
        Project project;
        project = new Project(name, inf, customerName, customerEmail);
        projectRepository.save(project);//сохранение объекта в бд
        return "redirect:/projects";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    private final UserService userService;

    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/login";
    }

    @Autowired
    private final WeekRepository weekRepository;

    @GetMapping("/schedule/create")
    public String createWorkSchedule(Model model) {

        Iterable<User> users = userRepository.findAll();
        Iterable<DayType> dayTypes = dayTypeRepository.findAll();
        Iterable<Week> week = weekRepository.findAll();
        Iterable<WorkSchedule> workSchedule = workScheduleRepository.findAll();
        model.addAttribute("weeks", week);
        model.addAttribute("users", users);
        model.addAttribute("dayTypes", dayTypes);
        model.addAttribute("workSchedule", workSchedule);

        return "schedule-create"; //
    }

private final WorkScheduleService workScheduleService;
    @PostMapping("/schedule/create")
    public String saveWorkSchedule(@RequestParam Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekStart, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekEnd, @RequestParam Long weekId, @RequestParam Long dayTypeId, Model model) {
        // Выполняем запрос ALTER TABLE
        workScheduleService.updateTableStructure();

        // 1) Ищем user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Ищем нужную запись Week
        Week week = weekRepository.findById(weekId)
                .orElseThrow(() -> new RuntimeException("Week not found with ID = " + weekId));

        // 3) Ищем нужную запись DayType
        DayType dayType = dayTypeRepository.findById(dayTypeId)
                .orElseThrow(() -> new RuntimeException("DayType not found with ID = " + dayTypeId));

        // 4) Создаём WorkSchedule
        WorkSchedule newSchedule = new WorkSchedule();
        newSchedule.setUser(user);
        newSchedule.setWeekStart(weekStart);
        newSchedule.setWeekEnd(weekEnd);
        newSchedule.setWeek(week);
        newSchedule.setDayType(dayType);

        // 5) Сохраняем
        workScheduleRepository.save(newSchedule);

        return "redirect:/schedule"; // или куда вам нужно
    }


}
