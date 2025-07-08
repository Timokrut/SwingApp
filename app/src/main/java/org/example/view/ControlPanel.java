package org.example.view;

import org.example.model.TaskManager; 

import javax.swing.*; 
import java.awt.*; 

public class ControlPanel extends JPanel {
    public ControlPanel(TaskManager taskManager) {
        setLayout(new FlowLayout());
        JButton addButton = new JButton("+");
        add(addButton);

        // TODO: another buttons Today, next, back.
    }
}
