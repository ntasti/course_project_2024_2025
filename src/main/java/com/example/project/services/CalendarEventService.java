package com.example.project.services;

import com.example.project.models.CalendarEvent;
import com.example.project.repo.CalendarEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;

    // Получить все события
    public List<CalendarEvent> getAllEvents() {
        return calendarEventRepository.findAll();
    }

    // Найти событие по ID
    public Optional<CalendarEvent> getEventById(Long id) {
        return calendarEventRepository.findById(id);
    }

    // Создать новое событие
    public CalendarEvent save(CalendarEvent calendarEvent) {
        return calendarEventRepository.save(calendarEvent);
    }

    // Обновить существующее событие
    public CalendarEvent update(CalendarEvent calendarEvent) {
        Optional<CalendarEvent> existingEvent = calendarEventRepository.findById(calendarEvent.getId());
        if (existingEvent.isPresent()) {
            CalendarEvent updatedEvent = existingEvent.get();
            updatedEvent.setStart(calendarEvent.getStart());
            updatedEvent.setEnd(calendarEvent.getEnd());
            updatedEvent.setText(calendarEvent.getText());
            updatedEvent.setColor(calendarEvent.getColor());
            return calendarEventRepository.save(updatedEvent);
        } else {
            throw new RuntimeException("Event not found with id: " + calendarEvent.getId());
        }
    }

    // Удалить событие
    public void delete(Long id) {
        if (calendarEventRepository.existsById(id)) {
            calendarEventRepository.deleteById(id);
        } else {
            throw new RuntimeException("Event not found with id: " + id);
        }
    }
}
