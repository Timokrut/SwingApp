package org.example.view;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.swing.*; 
import java.awt.*; 
import java.time.LocalDateTime;

public class TimelineView extends JPanel {
    private TaskManager taskManager;

    public TimelineView(TaskManager taskManager) {
        this.taskManager = taskManager;
        setPreferredSize(new Dimension(1000, 400));
        setBackground(Color.WHITE);
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: Draw tasks
        g.setColor(Color.GRAY);
        g.drawString("Time diagram will be displayed here", 50, 50);
    }
}

