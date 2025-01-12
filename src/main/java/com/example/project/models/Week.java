package com.example.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;


    public Week(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Week(){}


}
