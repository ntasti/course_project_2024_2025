package com.example.project.models;

import jakarta.persistence.*;

@Entity
@Table(name = "day_types")
public class DayType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DayType(){}

    public DayType(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }


}
