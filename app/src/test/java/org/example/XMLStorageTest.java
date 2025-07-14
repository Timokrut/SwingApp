package org.example;

import org.example.io.XMLStorage;
import org.example.model.Task;
import org.example.model.TaskManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

public class XMLStorageTest {

    private static final File TEST_FILE = new File("test_tasks.xml");

    @After
    public void cleanup() {
        TEST_FILE.delete();
    }

    @Test
    public void testSaveAndLoadTasksToFile() {
        Task task = new Task("Meeting", now(14), now(15), "#111111", "desc");
        XMLStorage.saveTasks(List.of(task), TEST_FILE.getPath());

        TaskManager manager = new TaskManager();
        XMLStorage.loadTasks(manager, TEST_FILE);

        assertEquals(1, manager.getTasks().size());
        Task loaded = manager.getTasks().get(0);

        assertEquals(task.getTitle(), loaded.getTitle());
        assertEquals(task.getColor(), loaded.getColor());
        assertEquals(task.getStartDate(), loaded.getStartDate());
        assertEquals(task.getEndDate(), loaded.getEndDate());
        assertEquals(task.getDescription(), loaded.getDescription());
    }

    private LocalDateTime now(int hour) {
        return LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }
}
