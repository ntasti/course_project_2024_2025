package com.example.project.controllers;

import com.example.project.models.Task;
import com.example.project.models.TasksUser;
import com.example.project.models.User;
import com.example.project.repo.TasksUserRepository;
import com.example.project.repo.UserRepository;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
    @RequiredArgsConstructor
    public class UserController {
        private final UserService userService;
        @Autowired
//        private TicketsViewRepository ticketsViewRepository;

        @GetMapping("/login")
        public String login() {
            return "main-login";
        }

        @GetMapping("/registration")
        public String registration() {
            return "registration";
        }


    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/login";
    }
    @Autowired
    public TasksUserRepository tasksUserRepository;

    @GetMapping("/calendar") // вывод купленных билетов в личном кабинете
    public String calender(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = user.getId();
        List<TasksUser> tasksUser = tasksUserRepository.findAllTasksByUserId(id_user);
        model.addAttribute("tasksUser", tasksUser);

        return "calendar";
    }


    @Autowired
    public UserRepository userRepository;
    @GetMapping("/users")
    public String users( Model model){
        Iterable<User> users= userRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("users",users);
        return "users";
    }

    }




