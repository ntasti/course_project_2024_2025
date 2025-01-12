package com.example.project.controllers;

import com.example.project.models.*;
import com.example.project.repo.DayTypeRepository;
import com.example.project.repo.UserRepository;
import com.example.project.repo.WeekRepository;
import com.example.project.repo.WorkScheduleRepository;
import com.example.project.services.DayTypeService;
import com.example.project.services.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WorkScheduleController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final DayTypeRepository dayTypeRepository;
    @Autowired
    private final WorkScheduleRepository workScheduleRepository;

    @Autowired
    private WeekRepository weekRepository;







//    @GetMapping("/schedule")
//    public String WorkSchedule(Model model) {
//        Iterable<User> users = userRepository.findAll();
//        Iterable<DayType> dayTypes = dayTypeRepository.findAll();
//        Iterable<Week> week = weekRepository.findAll();
//        Iterable<WorkSchedule> workSchedule = workScheduleRepository.findAll();
//        model.addAttribute("week", week);
//        model.addAttribute("weeks", weekRepository.findAll());
//        model.addAttribute("users", users);
//        model.addAttribute("dayTypes", dayTypes);
//        model.addAttribute("workSchedule", workSchedule);
//        return "work-schedule"; // Your HTML template name
//    }



    //вывод данных расписания по всем сотрудникам
    @GetMapping("/schedule")
    public String getWorkSchedule(Model model) {
        // Получение списка всех сотрудников
        List<User> users = userRepository.findAll();

        // Получение списка всех типов дней
        List<DayType> dayTypes = dayTypeRepository.findAll();

        // Получение списка всех недель
        List<Week> weeks = weekRepository.findAll();

        // Получение расписания работы
        List<WorkSchedule> workSchedules = workScheduleRepository.findAll();

        // Добавление данных в модель
        model.addAttribute("weeks", weeks); // Обратите внимание на множественное число
        model.addAttribute("users", users);
        model.addAttribute("dayTypes", dayTypes);
        model.addAttribute("workSchedules", workSchedules);

        return "work-schedule";








}


