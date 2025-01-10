package com.example.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
     private String inf;
     private String priority;
     private LocalDateTime dateOfCreated;
     private Date dateOfEnd;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id") // Убедитесь, что имя колонки соответствует БД
    private Project project;


    public Task( String name, String inf, String priority, LocalDateTime dateOfCreated, Date dateOfEnd, Project project) {

        this.name = name;
        this.inf = inf;
        this.priority = priority;
        this.dateOfCreated = dateOfCreated;
        this.dateOfEnd = dateOfEnd;
        this.project = project;
    }
    public Task(){}
}
