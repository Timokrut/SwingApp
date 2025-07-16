package org.example;

import org.example.model.Task;
import java.time.LocalDateTime; 

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {
    
    @Test
    public void testFieldsAndGetters() {
        LocalDateTime start = now(8);
        LocalDateTime end = now(9);

        Task task = new Task("T", start, end, "#123123", "desc");

        assertEquals("T", task.getTitle());
        assertEquals(start, task.getStartDate());
        assertEquals(end, task.getEndDate());
        assertEquals("#123123", task.getColor());
        assertEquals("desc", task.getDescription());
    }

    private LocalDateTime now(int hour) {
        return LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }
}
