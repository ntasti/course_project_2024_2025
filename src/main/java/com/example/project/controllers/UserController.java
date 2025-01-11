package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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
    }




