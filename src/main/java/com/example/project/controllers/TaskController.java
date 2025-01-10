package com.example.project.controllers;


import com.example.project.models.Project;
import com.example.project.models.Task;

import com.example.project.repo.ProjectRepository;
import com.example.project.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;


    //вывод по опрделенному Id
    @GetMapping("/task/{project}")
    public String getTasksByProjectId(@PathVariable(value = "project") Long project, Model model) {
        // Получаем задачи с сортировкой
        List<Task> tasks = taskRepository.findAllByProjectIdOrderByDateOfCreatedAsc(project);

        // Добавляем задачи в модель
        model.addAttribute("tasks", tasks);
        return "task-by-project";
    }


    @GetMapping("/task/inf/{id}")
    public String ProjectsInf(@PathVariable(value = "id") long id, Model model){
        Optional<Task> taskInf= taskRepository.findById(id);//вытягивание всех строк из таблицы
        ArrayList<Task> taskData= new ArrayList<>();
        taskInf.ifPresent(taskData::add);
        model.addAttribute("taskInf",taskData);
        return "task-inf";
    }

//    @GetMapping("/project/inf/{id}")
//    public String ProjectsInf(@PathVariable(value = "id") long id, Model model){
//        Optional<Project> projectDataByObject= projectRepository.findById(id);//вытягивание всех строк из таблицы
//        ArrayList<Project> projectData= new ArrayList<>();
//        projectDataByObject.ifPresent(projectData::add);
//        model.addAttribute("projectDataByObject",projectData);
//        return "project-inf";
//    }
//    @Autowired
//    private ProjectRepository projectRepository;
//    //общая информация
//    @GetMapping("/projects")
//    public String allProjectsInf(Model model){
//        Iterable<Project> projectsData= projectRepository.findAll();//вытягивание всех строк из таблицы
//        model.addAttribute("projectData",projectsData);
//        return "projects";
//    }
//
//    //добавление информации
//    @GetMapping("/project/create")
//    public String newProjectsCreate(Model model){
//        return "project-create";
//    }
//
//
//    @PostMapping("/project/create")
//    public String newProjects(@RequestParam String name, @RequestParam String inf, @RequestParam String customerName, @RequestParam String customerEmail, Model model){
//        Project project;
//        project = new Project(name, inf, customerName,customerEmail);
//        projectRepository.save(project);//сохранение объекта в бд
//        return "redirect:/projects";
//    }
//

}
