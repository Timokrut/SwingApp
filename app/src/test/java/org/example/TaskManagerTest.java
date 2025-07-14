package org.example;

import org.example.model.TaskManager;
import org.example.model.Task;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class TaskManagerTest {
    
    @Test 
    public void testAddAndGetTasks() {
        TaskManager manager = new TaskManager();
        Task task = new Task("Task 1", now(9), now(10), "FF0000", "desc");
        manager.addTask(task);

        assertEquals(1, manager.getTasks().size());
        assertEquals(task, manager.getTasks().get(0));
    }

    @Test 
    public void testRemoveTask() {
        TaskManager manager = new TaskManager();
        Task task = new Task("Task 2", now(12), now(13), "#00FF00", "");
        manager.addTask(task);
        manager.removeTask(task);

        assertTrue(manager.getTasks().isEmpty());
    }

    @Test 
    public void testClearTasks() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("1", now(1), now(2), "#123456", ""));
        manager.clearTasks();

        assertEquals(0, manager.getTasks().size());
    }

    private LocalDateTime now(int hour) {
        return LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }
}
