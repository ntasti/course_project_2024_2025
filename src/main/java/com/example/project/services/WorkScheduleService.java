package com.example.project.services;

import com.example.project.models.User;
import com.example.project.models.WorkSchedule;
import com.example.project.repo.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;



    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateTableStructure() {
        String alterTableQuery = """
            ALTER TABLE work_schedule
            CHANGE week_end week_end DATE NOT NULL,
            CHANGE week_start week_start DATE NOT NULL;
        """;
        jdbcTemplate.execute(alterTableQuery);
        System.out.println("Table structure updated successfully!");
    }


    public List<WorkSchedule> getGroupedWorkSchedules() {
        List<WorkSchedule> all = workScheduleRepository.findAll();

        // Map для группировки записей
        Map<String, WorkSchedule> groupedMap = new LinkedHashMap<>();

        for (WorkSchedule ws : all) {
            // Формируем ключ для группировки
            String key = ws.getUser().getId() + "_" + ws.getWeekStart().toString() + "_" + ws.getWeekEnd().toString();

            if (!groupedMap.containsKey(key)) {
                // Создаем новый объект для группировки
                WorkSchedule grouped = new WorkSchedule();
                grouped.setUser(ws.getUser());
                grouped.setWeekStart(ws.getWeekStart());
                grouped.setWeekEnd(ws.getWeekEnd());
                grouped.setMonday("-");
                grouped.setTuesday("-");
                grouped.setWednesday("-");
                grouped.setThursday("-");
                grouped.setFriday("-");
                grouped.setSaturday("-");
                grouped.setSunday("-");
                groupedMap.put(key, grouped);
            }

            // Объединяем данные о днях недели
            WorkSchedule groupWS = groupedMap.get(key);
            int dayOfWeek = ws.getWeek().getId().intValue(); // Преобразуем Long в int
            String typeOfDay = ws.getDayType().getDescription(); // Тип дня (Рабочий/Выходной)

            switch (dayOfWeek) {
                case 1 -> groupWS.setMonday(typeOfDay);
                case 2 -> groupWS.setTuesday(typeOfDay);
                case 3 -> groupWS.setWednesday(typeOfDay);
                case 4 -> groupWS.setThursday(typeOfDay);
                case 5 -> groupWS.setFriday(typeOfDay);
                case 6 -> groupWS.setSaturday(typeOfDay);
                case 7 -> groupWS.setSunday(typeOfDay);
                default -> {}
            }
        }

        return new ArrayList<>(groupedMap.values());
    }

    public List<WorkSchedule> getGroupedWorkSchedulesSearch(  @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekStart) {
        List<WorkSchedule> all = workScheduleRepository.findByWeekStart(weekStart);

        // Map для группировки записей
        Map<String, WorkSchedule> groupedMap = new LinkedHashMap<>();

        for (WorkSchedule ws : all) {
            // Формируем ключ для группировки
            String key = ws.getUser().getId() + "_" + ws.getWeekStart().toString() + "_" + ws.getWeekEnd().toString();

            if (!groupedMap.containsKey(key)) {
                // Создаем новый объект для группировки
                WorkSchedule grouped = new WorkSchedule();
                grouped.setUser(ws.getUser());
                grouped.setWeekStart(ws.getWeekStart());
                grouped.setWeekEnd(ws.getWeekEnd());
                grouped.setMonday("-");
                grouped.setTuesday("-");
                grouped.setWednesday("-");
                grouped.setThursday("-");
                grouped.setFriday("-");
                grouped.setSaturday("-");
                grouped.setSunday("-");
                groupedMap.put(key, grouped);
            }

            // Объединяем данные о днях недели
            WorkSchedule groupWS = groupedMap.get(key);
            int dayOfWeek = ws.getWeek().getId().intValue(); // Преобразуем Long в int
            String typeOfDay = ws.getDayType().getDescription(); // Тип дня (Рабочий/Выходной)

            switch (dayOfWeek) {
                case 1 -> groupWS.setMonday(typeOfDay);
                case 2 -> groupWS.setTuesday(typeOfDay);
                case 3 -> groupWS.setWednesday(typeOfDay);
                case 4 -> groupWS.setThursday(typeOfDay);
                case 5 -> groupWS.setFriday(typeOfDay);
                case 6 -> groupWS.setSaturday(typeOfDay);
                case 7 -> groupWS.setSunday(typeOfDay);
                default -> {}
            }
        }

        return new ArrayList<>(groupedMap.values());
    }

}
