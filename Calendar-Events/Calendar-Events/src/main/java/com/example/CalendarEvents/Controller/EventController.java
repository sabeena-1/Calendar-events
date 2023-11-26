package com.example.CalendarEvents.Controller;

import com.example.CalendarEvents.Entity.Event;
import com.example.CalendarEvents.Service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);

        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/day")
    public ResponseEntity<List<Event>> getEventsForDay(@RequestParam LocalDateTime day) {
        List<Event> events = eventService.getEventsForDay(day);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventService.addEvent(newEvent);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        Event editedEvent = eventService.updateEvent(eventId, updatedEvent);

        if (editedEvent != null) {
            return new ResponseEntity<>(editedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeEvent(@PathVariable Long eventId) {
        boolean deleted = eventService.removeEvent(eventId);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/duration/{eventId}")
    public ResponseEntity<String> getEventDuration(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        Duration duration = eventService.calculateDuration(event);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return new ResponseEntity<>(hours + " hours and " + minutes + " minutes", HttpStatus.OK);
    }
}
