package com.example.CalendarEvents.Repository;


import com.example.CalendarEvents.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
