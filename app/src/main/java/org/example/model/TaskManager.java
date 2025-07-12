package org.example.model; 

import java.util.ArrayList; 
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private String fileName = "tasks.xml";

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return this.tasks;
    }
    
    public void clearTasks() {
        tasks.clear();
    }

    public String getFilename() {
        return fileName;
    }

    public void changeFile(String fileName) {
        this.fileName = fileName;
    }
}
