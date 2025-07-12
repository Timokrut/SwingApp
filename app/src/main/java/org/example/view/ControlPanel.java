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
        JButton btnSaveto = new JButton("Save to");
        JButton btnLoad = new JButton("Load from");

        add(btnToday);
        add(btnBack);
        add(btnForward);
        add(btnZoom3Days);
        add(btnZoomWeek);
        add(btnAdd);
        add(btnSaveto);
        add(btnLoad);

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

        btnSaveto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(ControlPanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                manager.changeFile(file.getPath());
                XMLStorage.saveTasks(manager.getTasks(), manager.getFilename());
                AppConfig.saveLastFilePath(file.getPath());
            }
        });

        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(ControlPanel.this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                manager.changeFile(file.getPath());

                manager.clearTasks();
                XMLStorage.loadTasks(manager, file);
                AppConfig.saveLastFilePath(file.getPath());

                frame.refreshView();
            }
        });
    }
}
