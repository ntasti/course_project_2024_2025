package com.example.project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//
//@Entity
//@Setter
//@Getter
//@Table(name = "work_schedule")
//
//public class WorkSchedule {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @Column(name = "week_start", nullable = false)
//    private Date weekStart;
//
//    @Column(name = "week_end", nullable = false)
//    private Date weekEnd;
//
//    @ManyToOne
//    @JoinColumn(name = "week_id", referencedColumnName = "id")
//    private Week week;
//
//    @ManyToOne
//    @JoinColumn(name = "dayType_id", referencedColumnName = "id")
//    private DayType dayType;
//
//    // Конструктор по умолчанию
//    public WorkSchedule() {
//    }
//
//    // Конструктор с аргументами (если нужно)
//    public WorkSchedule(Long id, User user, Date weekStart, Date weekEnd, DayType dayType) {
//        this.id = id;
//        this.user = user;
//        this.weekStart = weekStart;
//        this.weekEnd = weekEnd;
//        this.dayType = dayType;
//    }
//
//    public WorkSchedule(Long userId, Date weekStart, Date weekEnd, DayType dayType) {
//    }
//
//
//}


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

    // Сотрудник
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Дата начала недели
    @Column(name = "week_start", nullable = false)
    private Date weekStart;

    // Дата окончания недели
    @Column(name = "week_end", nullable = false)
    private Date weekEnd;

    // Тип дня ("Выходной" или "Рабочий")
    @ManyToOne
    @JoinColumn(name = "dayType_id", referencedColumnName = "id")
    private DayType dayType;

    // Конкретный день недели (1=Понедельник, 2=Вторник, ...)
    @ManyToOne
    @JoinColumn(name = "week_id", referencedColumnName = "id")
    private Week week;

    // Транзиентные поля для вывода
    @Transient
    private String monday;

    @Transient
    private String tuesday;

    @Transient
    private String wednesday;

    @Transient
    private String thursday;

    @Transient
    private String friday;


    @Transient
    private String saturday;

    @Transient
    private String sunday;

    @Transient
    private int rowspan;

    // Конструкторы
    public WorkSchedule() {
    }

    public WorkSchedule(Long id, User user, Date weekStart, Date weekEnd, DayType dayType) {
        this.id = id;
        this.user = user;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.dayType = dayType;
    }

    public WorkSchedule(Long userId, Date weekStart, Date weekEnd, DayType dayType) {
        // При необходимости заполнить поля
    }
}