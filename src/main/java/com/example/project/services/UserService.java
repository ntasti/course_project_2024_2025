package com.example.project.services;


import com.example.project.models.User;
import com.example.project.models.enums.Role;
import com.example.project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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



    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }

//    private final TicketsRepository ticketsRepository;
//    public void createUserTickets(Tickets tickets){
//        log.info("Билет сохранен в 'Мои билеты'");
//        ticketsRepository.save(tickets);
    }



