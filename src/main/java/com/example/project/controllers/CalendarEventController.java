package com.example.project.controllers;

import com.example.project.models.CalendarEvent;
import com.example.project.services.CalendarEventService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    // Получение всех событий
    @GetMapping
    public ResponseEntity<List<CalendarEvent>> getEvents() {
        List<CalendarEvent> events = calendarEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Создание события
    @PostMapping("/create")
    public ResponseEntity<CalendarEvent> createEvent(@RequestBody CalendarEvent calendarEvent) {
        CalendarEvent createdEvent = calendarEventService.save(calendarEvent);
        return ResponseEntity.ok(createdEvent);
    }

    // Перемещение (обновление) события
    @PostMapping("/move")
    public ResponseEntity<CalendarEvent> moveEvent(@RequestBody CalendarEvent calendarEvent) {
        CalendarEvent updatedEvent = calendarEventService.update(calendarEvent);
        return ResponseEntity.ok(updatedEvent);
    }

    // Удаление события
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteEvent(@RequestBody Map<String, Long> body) {
        Long eventId = body.get("id");
        calendarEventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }
}
