package org.example.view;

import org.example.model.TaskManager;
import org.example.io.XMLStorage; 
import org.example.io.AppConfig; 

import javax.swing.*; 
import java.awt.*; 
import java.io.File;

public class MainFrame extends JFrame {
    private TaskManager taskManager;
    private final TimelineView timelineView;

    public MainFrame() {
        super("Personal Task Manager");
        taskManager = new TaskManager();

        String fileName = AppConfig.loadLastFilePath();
        if (fileName == null) {
            fileName = "tasks.xml";
        }

        // Load tasks from file
        try {
            File file = new File(fileName);
            XMLStorage.loadTasks(taskManager, file);
            AppConfig.saveLastFilePath(fileName);
            taskManager.changeFile(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed loading from file: \n" + ex.getMessage(), "Fault", JOptionPane.ERROR_MESSAGE);
        }
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
