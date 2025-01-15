package com.example.project.controllers;

import com.example.project.models.Project;
import com.example.project.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProjectController {

    @ModelAttribute("role")
    public String addUserRole(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Получение роли пользователя
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER"); // Значение по умолчанию
        }
        return "USER"; // Если пользователь не аутентифицирован
    }

    @Autowired
    private ProjectRepository projectRepository;
    //общая информация
    @GetMapping("/projects")
    public String allProjectsInf(Model model){
        Iterable<Project> projectsData= projectRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("projectData",projectsData);
        return "projects";
    }

//    //добавление информации
//    @GetMapping("/project/create")
//    public String newProjectsCreate(Model model){
//        return "project-create";
//    }
//
//
//    @PostMapping("/project/create")
//    public String newProjects(@RequestParam String name,@RequestParam String inf,@RequestParam String customerName,@RequestParam String customerEmail, Model model){
//      Project project;
//        project = new Project(name, inf, customerName,customerEmail);
//        projectRepository.save(project);//сохранение объекта в бд
//        return "redirect:/projects";
//    }

    //вывод по опрделенному Id
    @GetMapping("/project/inf/{id}")
    public String ProjectsInf(@PathVariable(value = "id") long id, Model model){
        Optional<Project> projectDataByObject= projectRepository.findById(id);//вытягивание всех строк из таблицы
        ArrayList<Project> projectData= new ArrayList<>();
        projectDataByObject.ifPresent(projectData::add);
        model.addAttribute("projectDataByObject",projectData);
        return "project-inf";
    }




}
