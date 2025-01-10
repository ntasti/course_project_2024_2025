package com.example.project.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String inf;
    private LocalDateTime dateOfCreated;
    private String customerName;
    private String customerEmail;


    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public Project(){
    }

    public Project(String name, String inf,String customerName,String customerEmail) {
        this.name = name;
        this.inf = inf;
        this.customerName=customerName;
        this.customerEmail=customerEmail;
    }

}
