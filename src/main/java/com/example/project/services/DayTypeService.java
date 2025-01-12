package com.example.project.services;



import com.example.project.models.DayType;
import com.example.project.repo.DayTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayTypeService {

    private final DayTypeRepository dayTypeRepository;

    public List<DayType> getAllDayTypes() {
        return dayTypeRepository.findAll();
    }
}
