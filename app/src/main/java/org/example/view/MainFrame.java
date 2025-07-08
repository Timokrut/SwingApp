package org.example.view;

import org.example.model.TaskManager;
import org.example.io.XMLStorage; 

import javax.swing.*; 
import java.awt.*; 


public class MainFrame extends JFrame {
    private TaskManager taskManager;

    public MainFrame() {
        super("Personal Task Manager");
        taskManager = new TaskManager();
        XMLStorage.loadTasks(taskManager);

        setLayout(new BorderLayout());
        add(new TimelineView(taskManager), BorderLayout.CENTER);
        add(new ControlPanel(taskManager), BorderLayout.NORTH);

        // TODO: etc

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
