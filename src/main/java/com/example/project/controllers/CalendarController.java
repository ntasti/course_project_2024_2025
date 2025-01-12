package com.example.project.controllers;

import com.example.project.models.Project;
import com.example.project.repo.TasksUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarController {

//    public TasksUserRepository tasksUserRepository;
//    @PostMapping("/tasks/usercreate")
//    public String newProjects(@RequestParam String name, @RequestParam String inf, @RequestParam String customerName, @RequestParam String customerEmail, Model model){
//        Project project;
//        project = new Project(name, inf, customerName,customerEmail);
//        projectRepository.save(project);//сохранение объекта в бд
//        return "redirect:/projects";
//    }
}
