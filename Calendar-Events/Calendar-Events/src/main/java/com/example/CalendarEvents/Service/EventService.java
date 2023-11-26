package com.example.CalendarEvents.Service;

import com.example.CalendarEvents.Entity.Event;
import com.example.CalendarEvents.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Duration calculateDuration(Event event) {
        if (event.isAllDay()) {
            return Duration.between(event.getStartDateTime(), event.getEndDateTime().plusDays(1));
        } else {
            return Duration.between(event.getStartDateTime(), event.getEndDateTime());
        }
    }

    public List<Event> getEventsForDay(LocalDateTime day) {
        return eventRepository.findByStartDateTimeBetween(
                day.withHour(0).withMinute(0).withSecond(0),
                day.withHour(23).withMinute(59).withSecond(59)
        );
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public Event addEvent(Event newEvent) {
        return eventRepository.save(newEvent);
    }

    public Event updateEvent(Long eventId, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(eventId).orElse(null);

        if (existingEvent != null) {
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setStartDateTime(updatedEvent.getStartDateTime());
            existingEvent.setEndDateTime(updatedEvent.getEndDateTime());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setAllDay(updatedEvent.isAllDay());

            return eventRepository.save(existingEvent);
        } else {
            return null;
        }
    }

    public boolean removeEvent(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        } else {
            return false;
        }
    }
}
