package com.example.project.repo;

import com.example.project.models.User;
import com.example.project.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    List<User> findAllByRoles(Role roles);

}
