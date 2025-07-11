package org.example.view;

import org.example.model.TaskManager; 

import javax.swing.*; 
import java.awt.*; 

public class ControlPanel extends JPanel {
    public ControlPanel(TaskManager manager, MainFrame frame) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnToday = new JButton("Today");
        JButton btnBack = new JButton("<");
        JButton btnForward = new JButton(">");
        JButton btnZoom3Days = new JButton("3 days");
        JButton btnZoomWeek = new JButton("Week");
        JButton btnAdd = new JButton("+");

        add(btnToday);
        add(btnBack);
        add(btnForward);
        add(btnZoom3Days);
        add(btnZoomWeek);
        add(btnAdd);

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
    }
}
