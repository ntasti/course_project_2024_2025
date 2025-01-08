package com.example.project.repo;

import com.example.project.models.Main;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaimRepository extends JpaRepository<Main, Long> {
}
