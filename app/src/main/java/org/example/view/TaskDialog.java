package org.example.view;

import org.example.model.Task;
import org.example.model.TaskManager; 

import javax.swing.*; 
import java.awt.*; 
import java.time.LocalDateTime; 
import java.time.LocalTime; 
import java.awt.event.ActionEvent;

public class TaskDialog extends JDialog {
    private final JTextField titleField = new JTextField(20);
    private final JTextField startField = new JTextField(LocalDateTime.now().with(LocalTime.MIN).toString(), 16);
    private final JTextField endField = new JTextField(LocalDateTime.now().with(LocalTime.MIN).toString(), 16);
    private final JTextField colorField = new JTextField("#00FFFF", 10);
    private final JTextArea descriptionArea = new JTextArea(5, 20);

    public TaskDialog(Window parent, TaskManager manager, Task existingTask) {
        super(parent, existingTask == null ? "New task" : "Edit", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        
        // Form for task 
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Name:")); form.add((titleField));
        form.add(new JLabel("Start at (YYYY-MM-DDTHH:MM):")); form.add(startField);
        form.add(new JLabel("End at (YYYY-MM-DDTHH:MM):")); form.add(endField);
        form.add(new JLabel("Color in hex (e.g. #FFFFFF):")); form.add(colorField);
        form.add(new JLabel("Description:")); form.add(new JScrollPane(descriptionArea));
        add(form, BorderLayout.CENTER);

        // Buttons 
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        buttons.add(saveButton);

        saveButton.addActionListener(e -> {
            Task task = new Task(
                titleField.getText(),
                LocalDateTime.parse(startField.getText()),
                LocalDateTime.parse(endField.getText()),
                colorField.getText(),
                descriptionArea.getText()
            );

            if (existingTask == null) {
                manager.addTask(task);
            } else {
                manager.removeTask(existingTask);
                manager.addTask(task);
            }

            dispose();
        });

        if (existingTask != null) {
            JButton deleteButton = new JButton("Delete");
            deleteButton.setForeground(Color.RED);
            buttons.add(deleteButton);
            
            deleteButton.addActionListener((ActionEvent e) -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Permanently delete this task?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeTask(existingTask);
                    dispose();
                }
            });
        }

        add(buttons, BorderLayout.SOUTH);

        // Filling fields  
        if (existingTask != null) {
            titleField.setText(existingTask.getTitle());
            startField.setText(existingTask.getStartDate().toString());
            endField.setText(existingTask.getEndDate().toString());
            colorField.setText(existingTask.getColor());
            descriptionArea.setText(existingTask.getDescription());
        }

        pack();
        setLocationRelativeTo(parent);
    } 
}
