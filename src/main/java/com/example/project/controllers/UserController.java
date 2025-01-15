package com.example.project.controllers;

import org.springframework.security.core.GrantedAuthority;
import com.example.project.models.TasksUser;
import com.example.project.models.User;
import com.example.project.repo.TasksUserRepository;
import com.example.project.repo.UserRepository;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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


    @Autowired
    public TasksUserRepository tasksUserRepository;

    @GetMapping("/calendar") // вывод задач в календаре
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
    public String users(Model model) {
        Iterable<User> users = userRepository.findAll();//вытягивание всех строк из таблицы
        model.addAttribute("users", users);
        return "users";
    }


    @GetMapping("/users/search")
    public String searchUsers(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "sort", required = false, defaultValue = "name") String sort, Model model) {
        List<User> users;
        // Проверяем, если поисковый запрос не пустой
        if (query != null && !query.isEmpty()) {
            // Выполняем поиск по имени, фамилии или email
            users = userRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query);
        } else {
            // Если строка поиска пустая, возвращаем всех пользователей
            users = userRepository.findAll();
        }

        // Сортировка списка
        users.sort((u1, u2) -> {
            switch (sort.toLowerCase()) {
                case "lastname":
                    return u1.getLastName().compareToIgnoreCase(u2.getLastName());
                case "email":
                    return u1.getEmail().compareToIgnoreCase(u2.getEmail());
                case "name":
                default:
                    return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        // Добавляем результат поиска в модель
        model.addAttribute("users", users);

        // Возвращаем имя шаблона, который будет отображать таблицу
        return "users";
    }


    @GetMapping("/personal/inf")//вывод данных пользователя  в личном кабинете
    public String personal1(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getName();
        model.addAttribute("name", name);
        String lastName = user.getLastName();
        model.addAttribute("lastName", lastName);
        String email = user.getEmail();
        model.addAttribute("email", email);
        String position = user.getPosition();
        model.addAttribute("position", position);
        LocalDateTime dateOfCreated = user.getDateOfCreated();
        model.addAttribute("dateOfCreated", dateOfCreated);
        return "person-inf";
    }


    @GetMapping("/") // вывод фильтрованных задач по каждому юзеру
    public String tasks(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = user.getId();
        Optional<User> users = userRepository.findById(id_user);

        model.addAttribute("user", users);

        List<TasksUser> tasks = tasksUserRepository.findAllTasksByUserId(id_user);

        model.addAttribute("tasks", tasks);
        model.addAttribute("tasks", tasks);
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




