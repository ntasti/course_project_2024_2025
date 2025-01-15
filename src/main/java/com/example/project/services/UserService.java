package com.example.project.services;


import com.example.project.models.TasksUser;
import com.example.project.models.User;
import com.example.project.models.enums.Role;
import com.example.project.repo.TasksUserRepository;
import com.example.project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.example.project.models.enums.Role.ADMIN;
import static com.example.project.models.enums.Role.USER;


@Service
@Slf4j
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(user.getEmail()) != null){
            log.info("Saving new User with exists", email);
            return  false;
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(USER);
        log.info("Saving new User with email", email);


        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        return userRepository.findAllByRoles(USER);
    }

    public List<User> listADMIN() {
        return userRepository.findAllByRoles(ADMIN);
    }


    }



