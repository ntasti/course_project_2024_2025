package com.example.project.controllers;

import com.example.project.models.Project;
import com.example.project.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String allProjectsInf(Model model){
        Iterable<Project> projectsData= projectRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("projectData",projectsData);
        return "projects";
    }

    @GetMapping("/project/create")
    public String newProjectsCreate(Model model){
        return "project-create";
    }


    @PostMapping("/project/create")
    public String newProjects(@RequestParam String name,@RequestParam String inf,@RequestParam String customerName,@RequestParam String customerEmail, Model model){
      Project project;
        project = new Project(name, inf, customerName,customerEmail);
        projectRepository.save(project);//сохранение объекта в бд
        return "redirect:/projects";
    }
}
