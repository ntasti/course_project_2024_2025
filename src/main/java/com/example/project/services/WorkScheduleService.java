package com.example.project.services;

import com.example.project.models.User;
import com.example.project.models.WorkSchedule;
import com.example.project.repo.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;

    public List<WorkSchedule> getAllSchedules() {
        return workScheduleRepository.findAll();
    }

    public WorkSchedule findByUser(User user) {
        return workScheduleRepository.findByUser(user);
    }

    public void saveAll(List<WorkSchedule> schedules) {
        workScheduleRepository.saveAll(schedules);
    }

}
