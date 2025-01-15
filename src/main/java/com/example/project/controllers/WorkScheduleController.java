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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
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


    @ModelAttribute("role")
    public String addUserRole(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Получение роли пользователя
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER"); // Значение по умолчанию
        }
        return "USER"; // Если пользователь не аутентифицирован
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


//
//    @PostMapping("/schedule/create")
//    public String saveWorkSchedule(@RequestParam Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekStart, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date weekEnd, @RequestParam Long weekId, @RequestParam Long dayTypeId, Model model) {
//        // Выполняем запрос ALTER TABLE
//        workScheduleService.updateTableStructure();
//
//        // 1) Ищем user
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 2) Ищем нужную запись Week
//        Week week = weekRepository.findById(weekId)
//                .orElseThrow(() -> new RuntimeException("Week not found with ID = " + weekId));
//
//        // 3) Ищем нужную запись DayType
//        DayType dayType = dayTypeRepository.findById(dayTypeId)
//                .orElseThrow(() -> new RuntimeException("DayType not found with ID = " + dayTypeId));
//
//        // 4) Создаём WorkSchedule
//        WorkSchedule newSchedule = new WorkSchedule();
//        newSchedule.setUser(user);
//        newSchedule.setWeekStart(weekStart);
//        newSchedule.setWeekEnd(weekEnd);
//        newSchedule.setWeek(week);
//        newSchedule.setDayType(dayType);
//
//        // 5) Сохраняем
//        workScheduleRepository.save(newSchedule);
//
//        return "redirect:/schedule"; // или куда вам нужно
//    }


    @GetMapping("/schedule/search")
    public String searchSchedulesByDate(@RequestParam(value = "weekStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekStart,
            Model model) {
        List<WorkSchedule> scheduleList = workScheduleService.getGroupedWorkSchedulesSearch(weekStart);

        model.addAttribute("scheduleList", scheduleList);
        return "work-schedule";
    }


}


