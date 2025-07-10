package org.example.view;

import org.example.model.TaskManager;
import org.example.io.XMLStorage; 

import javax.swing.*; 
import java.awt.*; 

public class MainFrame extends JFrame {
    private TaskManager taskManager;
    private final TimelineView timelineView;

    public MainFrame() {
        super("Personal Task Manager");
        taskManager = new TaskManager();
        
        // Load tasks from file
        XMLStorage.loadTasks(taskManager);

        setLayout(new BorderLayout());

        // UPPER PANEL
        ControlPanel controlPanel = new ControlPanel(taskManager, this);
        add(controlPanel, BorderLayout.NORTH);

        timelineView = new TimelineView(taskManager);
        add(new JScrollPane(timelineView), BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1900, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refreshView() {
        timelineView.repaint();
    }

    public TimelineView getTimelineView() {
        return timelineView;
    }
}
