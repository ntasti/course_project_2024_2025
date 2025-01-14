package com.example.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name ="status_id" ,referencedColumnName = "id")
    private  StatusTask status;
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "task")
//     private List<TasksUser> tasksUsers=new ArrayList<>();
    private LocalDateTime dateOfCreated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date dateOfEnd;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id") // Убедитесь, что имя колонки соответствует БД
    private Project project;


    public Task( String name, String inf, String priority, LocalDateTime dateOfCreated, Date dateOfEnd, Project project,StatusTask status) {

        this.name = name;
        this.inf = inf;
        this.priority = priority;
        this.dateOfCreated = dateOfCreated;
        this.dateOfEnd = dateOfEnd;
        this.project = project;
        this.status=status;

    }
    public Task(){}
}
