package org.example;

import org.example.model.Task;
import java.time.LocalDateTime; 

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {
    
    @Test 
    public void testTaskFields() {
        Task task = new Task("T", now(8), now(9), "#123123", "desc");
        assertEquals("T", task.getTitle());
        assertEquals("#123123", task.getColor());
        assertEquals("desc", task.getDescription());
    }

    @Test 
    public void testEquality() {
        Task t1 = new Task("A", now(10), now(11), "#222222", "x");
        Task t2 = new Task("A", now(10), now(11), "#222222", "x");

        assertNotSame(t1, t2);
    }

    private LocalDateTime now(int hour) {
        return LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }
}
