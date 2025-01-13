package com.example.project.controllers;

import com.example.project.models.*;
import com.example.project.repo.DayTypeRepository;
import com.example.project.repo.UserRepository;
import com.example.project.repo.WeekRepository;
import com.example.project.repo.WorkScheduleRepository;

import com.example.project.services.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Date;
import java.util.List;


@Controller

public class WorkScheduleController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  DayTypeRepository dayTypeRepository;
    @Autowired
    private  WorkScheduleRepository workScheduleRepository;

    private final WorkScheduleService workScheduleService;

    @Autowired
    private WeekRepository weekRepository;


    @GetMapping("/schedule/create")
    public String createWorkSchedule(Model model) {

        Iterable<User> users = userRepository.findAll();
        Iterable<DayType> dayTypes = dayTypeRepository.findAll();
        Iterable<Week> week = weekRepository.findAll();
        Iterable<WorkSchedule> workSchedule = workScheduleRepository.findAll();
        model.addAttribute("weeks", week);
        model.addAttribute("users", users);
        model.addAttribute("dayTypes", dayTypes);
        model.addAttribute("workSchedule", workSchedule);

        return "schedule-create"; //
    }





    // Spring найдет WorkScheduleService и передаст в конструктор
    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService=workScheduleService;
    }

    @GetMapping("/schedule")
    public String showSchedule(Model model) {
        List<WorkSchedule> scheduleList = workScheduleService.getGroupedWorkSchedules();

        model.addAttribute("scheduleList", scheduleList);
        return "work-schedule";
    }

//    //вывод данных расписания по всем сотрудникам
//    @GetMapping("/schedule")
//    public String getWorkSchedule(Model model) {
//        List<User> users = userRepository.findAll();
//        List<DayType> dayTypes = dayTypeRepository.findAll();
//        List<Week> weeks = weekRepository.findAll();
//        List<WorkSchedule> workSchedules = workScheduleRepository.findAll();
//        // Добавление данных в модель
//        model.addAttribute("weeks", weeks); // Обратите внимание на множественное число
//        model.addAttribute("users", users);
//        model.addAttribute("dayTypes", dayTypes);
//        model.addAttribute("workSchedules", workSchedules);
//
//        return "work-schedule";
//
//    }




    @PostMapping("/schedule/create")
    public String saveWorkSchedule(
            @RequestParam Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekStart, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekEnd, @RequestParam Long weekId, @RequestParam Long dayTypeId, Model model) {
        // Выполняем запрос ALTER TABLE
        workScheduleService.updateTableStructure();

        // 1) Ищем user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Ищем нужную запись Week
        Week week = weekRepository.findById(weekId)
                .orElseThrow(() -> new RuntimeException("Week not found with ID = " + weekId));

        // 3) Ищем нужную запись DayType
        DayType dayType = dayTypeRepository.findById(dayTypeId)
                .orElseThrow(() -> new RuntimeException("DayType not found with ID = " + dayTypeId));

        // 4) Создаём WorkSchedule
        WorkSchedule newSchedule = new WorkSchedule();
        newSchedule.setUser(user);
        newSchedule.setWeekStart(weekStart);
        newSchedule.setWeekEnd(weekEnd);
        newSchedule.setWeek(week);
        newSchedule.setDayType(dayType);

        // 5) Сохраняем
        workScheduleRepository.save(newSchedule);

        return "redirect:/schedule"; // или куда вам нужно
    }





}


