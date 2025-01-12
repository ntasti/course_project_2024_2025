package com.example.project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Setter
@Getter
@Table(name = "work_schedule")

public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "week_start", nullable = false)
    private Date weekStart;

    @Column(name = "week_end", nullable = false)
    private Date weekEnd;

    @ManyToOne
    @JoinColumn(name = "week_id", referencedColumnName = "id")
    private Week week;

    @ManyToOne
    @JoinColumn(name = "dayType_id", referencedColumnName = "id")
    private DayType dayType;

    // Конструктор по умолчанию
    public WorkSchedule() {
    }

    // Конструктор с аргументами (если нужно)
    public WorkSchedule(Long id, User user, Date weekStart, Date weekEnd, DayType dayType) {
        this.id = id;
        this.user = user;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.dayType = dayType;
    }
}
