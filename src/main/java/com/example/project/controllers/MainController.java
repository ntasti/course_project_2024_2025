package com.example.project.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {
    @GetMapping("/main")
    public String mainData(Model model){

        return "main";
    }

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

}
