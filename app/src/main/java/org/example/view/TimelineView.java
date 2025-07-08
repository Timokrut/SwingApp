package org.example.view;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.swing.*; 
import java.awt.*; 
import java.time.LocalDate;

public class TimelineView extends JPanel {
    private TaskManager taskManager;

    public TimelineView(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: Draw tasks
    }
}

