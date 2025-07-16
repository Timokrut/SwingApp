package org.example.view;

import org.example.model.TaskManager; 
import org.example.io.XMLStorage;
import org.example.io.AppConfig;

import javax.swing.*; 
import java.awt.*; 
import java.io.File;

public class ControlPanel extends JPanel {
    public ControlPanel(TaskManager manager, MainFrame frame) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnToday = new JButton("Today");
        JButton btnBack = new JButton("<");
        JButton btnForward = new JButton(">");
        JButton btnZoom3Days = new JButton("3 days");
        JButton btnZoomWeek = new JButton("Week");
        JButton btnAdd = new JButton("+");
        JButton btnSaveTo = new JButton("Save to");
        JButton btnLoadFrom = new JButton("Load from");

        add(btnToday);
        add(btnBack);
        add(btnForward);
        add(btnZoom3Days);
        add(btnZoomWeek);
        add(btnAdd);
        add(btnSaveTo);
        add(btnLoadFrom);

        btnAdd.addActionListener(e -> {
            TaskDialog dialog = new TaskDialog(frame, manager, null);
            dialog.setVisible(true);
            frame.refreshView();
        });

        btnZoom3Days.addActionListener(e -> {
            frame.getTimelineView().setDaySpan(3);
            frame.getTimelineView().resetToToday();
        });

        btnZoomWeek.addActionListener(e -> {
            frame.getTimelineView().setDaySpan(7);
            frame.getTimelineView().resetToToday();
        });

        btnToday.addActionListener(e -> {
            frame.getTimelineView().resetToToday();
        });

        btnBack.addActionListener(e -> {
            frame.getTimelineView().shiftDays(-frame.getTimelineView().getDaySpan());
        });

        btnForward.addActionListener(e -> {
            frame.getTimelineView().shiftDays(frame.getTimelineView().getDaySpan());
        });

        btnSaveTo.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(ControlPanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    manager.changeFile(file.getPath());
                    XMLStorage.saveTasks(manager.getTasks(), manager.getFilename());
                    AppConfig.saveLastFilePath(file.getPath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed saving to file: \n" + ex.getMessage(), "Fault", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLoadFrom.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(ControlPanel.this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    manager.changeFile(file.getPath());

                    manager.clearTasks();
                    XMLStorage.loadTasks(manager, file);
                    AppConfig.saveLastFilePath(file.getPath());

                    frame.refreshView();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed loading from file: \n" + ex.getMessage(), "Fault", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
