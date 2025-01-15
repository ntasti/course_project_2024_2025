package com.example.project.controllers;


import com.example.project.models.Project;
import com.example.project.models.StatusTask;
import com.example.project.models.Task;

import com.example.project.repo.ProjectRepository;
import com.example.project.repo.StatusTaskRepository;
import com.example.project.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;


    //вывод по опрделенному Id
    @GetMapping("/task/{projectId}")
    public String getTasksByProjectId(@PathVariable(value = "projectId") Long projectId, Model model) {
        List<Task> tasks = taskRepository.findAllByProject_IdOrderByDateOfCreatedAsc(projectId);
        model.addAttribute("tasks", tasks);
        return "task-by-project";
    }


    @GetMapping("/task/inf/{id}")
    public String TaskInf(@PathVariable(value = "id") long id, Model model) {
        Optional<Task> taskInf = taskRepository.findById(id);//вытягивание всех строк из таблицы
        ArrayList<Task> taskData = new ArrayList<>();
        taskInf.ifPresent(taskData::add);
        model.addAttribute("taskInf", taskData);
        return "task-inf";
    }

    @Autowired
    private StatusTaskRepository statusTaskRepository;
    @GetMapping("/task/inf/{id}/edit")
    public String TaskStatusEdit(@PathVariable(value = "id") long id, Model model) {
        // Проверка существования задачи
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
        model.addAttribute("task", task);

        // Проверка существования статусов
        Iterable<StatusTask> statuses = statusTaskRepository.findAll();
        if (!statuses.iterator().hasNext()) {
            throw new IllegalArgumentException("No statuses found in the database.");
        }
        model.addAttribute("statuses", statuses);

        return "task-edit";
    }


    @PostMapping("/task/inf/{id}/edit")
    public String TaskStatusEdit(@PathVariable(value = "id") long id, @RequestParam Long statusId) {
        // Находим задачу
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        // Находим статус по ID
        StatusTask status = statusTaskRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found with id: " + statusId));

        // Обновляем статус задачи
        task.setStatus(status);
        taskRepository.save(task);

        return "redirect:/task/inf/" + id;
    }


    @GetMapping("/task")
    public String task(Model model) {
        Iterable<Task> task = taskRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("taskData", task);
        return "task";
    }

    @GetMapping("/task/search")
    public String searchTasks(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Task> tasks;
        // Проверяем, если поисковый запрос не пустой
        if (query != null && !query.isEmpty()) {
            // Выполняем поиск по имени задачи или имени проекта
            tasks = taskRepository.findByNameContainingIgnoreCaseOrProject_NameContainingIgnoreCase(query, query);
        } else {
            // Если строка поиска пустая, возвращаем все задачи
            tasks = taskRepository.findAll();
        }
        // Добавляем результат поиска в модель
        model.addAttribute("taskData", tasks);
        // Возвращаем имя шаблона, который будет отображать таблицу
        return "task";
    }


    @Autowired
    private ProjectRepository projectRepository;




}
